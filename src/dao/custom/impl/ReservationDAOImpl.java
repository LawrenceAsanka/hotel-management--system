package dao.custom.impl;

import java.sql.ResultSet;
import java.util.List;

import dao.CrudUtil;
import dao.custom.ReservationDAO;
import entity.Reservation;

public class ReservationDAOImpl implements ReservationDAO {

  @Override
  public List<Reservation> findAll() throws Exception {
    return null;
  }

  @Override
  public Reservation find(String key) throws Exception {
    ResultSet resultSet = CrudUtil.execute("SELECT * FROM Reservation WHERE resvId=?",key);
    if (resultSet.next()) {
      return new Reservation(resultSet.getString(1),resultSet.getDate(2),resultSet.getString(3),
          resultSet.getDate(4),resultSet.getDate(5),resultSet.getString(6),resultSet.getString(7));
    }
    return null;
  }

  @Override
  public boolean save(Reservation reservation) throws Exception {
   return CrudUtil.execute("INSERT INTO Reservation VALUES (?,?,?,?,?,?,?)",
       reservation.getResvId(),reservation.getResvDate(),reservation.getGuestId(),reservation.getCheckInDate(),
       reservation.getCheckOutDate(),reservation.getUserId(),reservation.getStatus());
  }

  @Override
  public boolean update(Reservation reservation) throws Exception {
    return CrudUtil.execute("UPDATE Reservation SET userId=?,status=? WHERE resvId=?",reservation.getUserId(),
        reservation.getStatus(),reservation.getResvId());
  }

  @Override
  public boolean delete(String key) throws Exception {
    return false;
  }

  @Override
  public String getLastReservationId() throws Exception {
    ResultSet result =  CrudUtil.execute("SELECT resvId FROM Reservation ORDER BY resvId DESC LIMIT 1 ");
    if (!result.next()) {
      return null;
    } else {
      return result.getString(1);
    }
  }
}
