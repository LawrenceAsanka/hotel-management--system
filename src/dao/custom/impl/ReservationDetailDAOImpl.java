package dao.custom.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.CrudUtil;
import dao.custom.ReservationDetailDAO;
import entity.ReservationDetail;
import entity.ReservationDetailPK;

public class ReservationDetailDAOImpl implements ReservationDetailDAO {

  @Override
  public List<ReservationDetail> findAll() throws Exception {
    return null;
  }

  @Override
  public ReservationDetail find(ReservationDetailPK key) throws Exception {
    return null;

  }

  @Override
  public boolean save(ReservationDetail reservationDetail) throws Exception {
    return CrudUtil.execute("INSERT INTO ReservationDetail VALUES (?,?,?)",
        reservationDetail.getReservationDetailPK().getResvId(),
        reservationDetail.getReservationDetailPK().getRoomNumber(),
        reservationDetail.getRoomPrice());
  }

  @Override
  public boolean update(ReservationDetail entity) throws Exception {
    return false;
  }

  @Override
  public boolean delete(ReservationDetailPK key) throws Exception {
    return false;
  }

  @Override
  public List<ReservationDetail> findReservedRooms(String id) throws Exception {
    ResultSet result = CrudUtil.execute("SELECT * FROM ReservationDetail WHERE resvId=?", id);
    List<ReservationDetail> reservationDetails = new ArrayList<>();
    while (result.next()) {
      reservationDetails.add(new ReservationDetail(result.getString(1), result.getString(2),
          result.getBigDecimal(3)));
    }
    return reservationDetails;
  }
}
