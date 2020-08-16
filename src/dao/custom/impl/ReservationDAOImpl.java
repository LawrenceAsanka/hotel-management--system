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
