package dao.custom.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.CrudUtil;
import dao.custom.QueryDAO;
import entity.CustomEntity;

public class QueryDAOImpl implements QueryDAO {

  @Override
  public List<CustomEntity> getAllRoomDetails() throws Exception {
    ResultSet result = CrudUtil.execute("SELECT r.roomNumber,rt.typeName,rt.typePrice,r.roomStatus\n"
        + "FROM Room r\n"
        + "INNER JOIN RoomType rt on r.typeId = rt.typeId");
    List<CustomEntity> rooms = new ArrayList<>();
    while (result.next()) {
      rooms.add(new CustomEntity(result.getString(1), result.getString(2),
          result.getBigDecimal(3), result.getString(4)));
    }
    return rooms;
  }

  @Override
  public CustomEntity findRoom(String roomNumber) throws Exception {
    ResultSet result = CrudUtil.execute("SELECT r.roomNumber,rt.typeName,rt.typePrice\n"
        + "FROM Room r\n"
        + "INNER JOIN RoomType rt on r.typeId = rt.typeId\n"
        + "WHERE roomNumber=?",roomNumber);
    if (result.next()) {
      return new CustomEntity(result.getString(1),result.getString(2),result.getBigDecimal(3));
    }
    return null;
  }

  @Override
  public List<CustomEntity> getReservationsDetails(String status) throws Exception {
    ResultSet result = CrudUtil.execute("SELECT r.resvId,g.guestId,g.guestFirstName,r.checkInDate,r.checkOutDate,SUM(rd.roomPrice),g.guestStatus\n"
        + "FROM Reservation r\n"
        + "INNER JOIN Guest g ON r.guestId = g.guestId\n"
        + "INNER JOIN ReservationDetail rd on r.resvId = rd.resvId WHERE guestStatus=? GROUP BY r.resvId",status);
    List<CustomEntity> reservationDetails = new ArrayList<>();
    while (result.next()) {
      reservationDetails.add(new CustomEntity(result.getString(1), result.getString(2),result.getString(3),
          result.getDate(4),result.getDate(5),result.getBigDecimal(6)));
    }
    return reservationDetails;
  }

}

