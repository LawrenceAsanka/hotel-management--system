package bo.custom.impl;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import business.custom.CheckoutBO;
import dao.DAOFactory;
import dao.DAOType;
import dao.custom.CheckOutDAO;
import dao.custom.GuestDAO;
import dao.custom.ReservationDetailDAO;
import dao.custom.RoomDAO;
import dbConnection.DBConnection;
import entity.CheckOut;
import entity.ReservationDetail;
import entity.Room;
import util.ReservationTM;
import util.RoomTM;

public class CheckoutBOImpl implements CheckoutBO {

  private final CheckOutDAO checkOutDAO = DAOFactory.getInstance().getDAO(DAOType.CHECKOUT);
  private final GuestDAO guestDAO = DAOFactory.getInstance().getDAO(DAOType.GUEST);
  private final RoomDAO roomDAO = DAOFactory.getInstance().getDAO(DAOType.ROOM);
  private final ReservationDetailDAO reservationDetailDAO = DAOFactory.getInstance().getDAO(DAOType.RESERVATION_DETAIL);

  @Override
  public boolean checkout(ReservationTM reservation, String userId, Date date, int dateCount, List<RoomTM> room) {
    boolean result = false;
    Connection connection = DBConnection.getInstance().getConnection();
    try {
      connection.setAutoCommit(false);
      result = checkOutDAO.save(new CheckOut(0, reservation.getReservationId(), userId, date, dateCount,
          BigDecimal.valueOf(reservation.getNetAmount().doubleValue() * dateCount)));
      if (!result) {
        connection.rollback();
        return false;
      }

      result = guestDAO.updateGuestStatus(reservation.getGuestId(), "out");
      if (!result) {
        connection.rollback();
        return false;
      }

      for (RoomTM rooms : room) {
        result = roomDAO.updateCheckoutRooms(new Room(rooms.getRoomNumber(),0,"Not-Reserved"));
        if (!result) {
          connection.rollback();
          return false;
        }
      }


      connection.commit();
      return true;
    } catch (Throwable throwable) {
      throwable.printStackTrace();
      try {
        connection.rollback();
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
      return false;
    } finally {
      try {
        connection.setAutoCommit(true);
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
    }
  }

  @Override
  public List<RoomTM> findAllCheckoutRooms(String id) throws Exception {
    List<ReservationDetail> reservedRooms = reservationDetailDAO.findReservedRooms(id);
    List<RoomTM> rooms = new ArrayList<>();
    for (ReservationDetail reservedRoom : reservedRooms) {
      rooms.add(new RoomTM(reservedRoom.getReservationDetailPK().getRoomNumber(),"", BigDecimal.valueOf(00.00),
          ""));
    }
    return rooms;
  }
}
