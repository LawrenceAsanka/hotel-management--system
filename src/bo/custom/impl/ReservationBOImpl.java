package bo.custom.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bo.custom.ReservationBO;
import dao.DAOFactory;
import dao.DAOType;
import dao.custom.GuestDAO;
import dao.custom.QueryDAO;
import dao.custom.ReservationDAO;
import dao.custom.ReservationDetailDAO;
import dao.custom.RoomDAO;
import dao.custom.impl.RoomDAOImpl;
import db.DBConnection;
import entity.CustomEntity;
import entity.Guest;
import entity.Reservation;
import entity.ReservationDetail;
import entity.Room;
import util.GuestTM;
import util.ReservationDetailTM;
import util.ReservationTM;

public class ReservationBOImpl implements ReservationBO {

  private final ReservationDAO reservationDAO = DAOFactory.getInstance().getDAO(DAOType.RESERVATION);
  private final GuestDAO guestDAO = DAOFactory.getInstance().getDAO(DAOType.GUEST);
  private final ReservationDetailDAO reservationDetailDAO = DAOFactory.getInstance().getDAO(DAOType.RESERVATION_DETAIL);
  private final RoomDAO roomDAO = DAOFactory.getInstance().getDAO(DAOType.ROOM);
  private final QueryDAO queryDAO = DAOFactory.getInstance().getDAO(DAOType.QUERY);

  @Override
  public boolean reserved(GuestTM guest, String guestId, String userId, ReservationTM reservation,
      List<ReservationDetailTM> reservationDetails) {
    boolean result = false;
    //Beginning transaction
    Connection connection = DBConnection.getInstance().getConnection();

    try {
      connection.setAutoCommit(false);

      Guest existGuest = guestDAO.find(guestId);
      //if (existGuest.getGuestId().equals(guest.getGuestId()))
      if (existGuest != null) {

        result = guestDAO.update(new Guest(guest.getGuestId(), guest.getGuestFirstName(), guest.getGuestLastName(),
            guest.getGuestAddress(), guest.getPassportNumber(), guest.getGuestCountry(),
            guest.getGuestContact(), guest.getGuestStatus()));

      } else {

        result = guestDAO.save(new Guest(guest.getGuestId(), guest.getGuestFirstName(), guest.getGuestLastName(),
            guest.getGuestAddress(), guest.getPassportNumber(), guest.getGuestCountry(),
            guest.getGuestContact(), guest.getGuestStatus()));
      }
      if (!result) {
        connection.rollback();
        return false;
      }

      result = reservationDAO.save(new Reservation(reservation.getReservationId(), reservation.getCheckInDate(),
          reservation.getGuestId(), reservation.getCheckInDate(), reservation.getCheckOutDate(), userId));
      if (!result) {
        connection.rollback();
        return false;
      }

      for (ReservationDetailTM reservationDetail : reservationDetails) {
        result = reservationDetailDAO
            .save(new ReservationDetail(reservation.getReservationId(), reservationDetail.getRoomNumber(),
                reservationDetail.getRoomPrice()));

        if (!result) {
          connection.rollback();
          return false;
        }

        Room room = roomDAO.find(reservationDetail.getRoomNumber());
        room.setRoomStatus("Reserved");
        result = new RoomDAOImpl().update(room);
        if (!result) {
          connection.rollback();
          return false;
        }
      }
      connection.commit();
      return true;
    } catch (Throwable e) {
      e.printStackTrace();
      try {
        connection.rollback();
      } catch (SQLException throwable) {
        throwable.printStackTrace();
      }
      return false;
    } finally {
      try {
        connection.setAutoCommit(true);
      } catch (SQLException throwable) {
        throwable.printStackTrace();
      }
    }


  }

  @Override
  public String getNewReservationId() throws Exception {
    String lastReservationId = reservationDAO.getLastReservationId();
    if (lastReservationId == null) {
      return "R001";
    } else {
      int maxId = Integer.parseInt(lastReservationId.replace("R", ""));
      maxId = maxId + 1;
      String id = "";
      if (maxId < 10) {
        id = "R00" + maxId;
      } else if (maxId < 100) {
        id = "R0" + maxId;
      } else {
        id = "R" + maxId;
      }
      return id;
    }
  }

  @Override
  public List<ReservationTM> reservationDetails(String status) throws Exception {
    List<CustomEntity> reservationsDetails = queryDAO.getReservationsDetails(status);
    List<ReservationTM> reservationTMList = new ArrayList<>();
    for (CustomEntity reservationsDetail : reservationsDetails) {
      reservationTMList.add(new ReservationTM(reservationsDetail.getReservationId(), reservationsDetail.getGuestId(),
          reservationsDetail.getGuestFirstName(), reservationsDetail.getCheckinDate(),
          reservationsDetail.getCheckoutDate(),
          reservationsDetail.getTypePrice()));
    }
    return reservationTMList;
  }




}