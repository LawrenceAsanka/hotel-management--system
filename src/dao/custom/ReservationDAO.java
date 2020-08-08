package dao.custom;

import dao.CrudDAO;
import entity.Reservation;

public interface ReservationDAO extends CrudDAO<Reservation,String> {

  String getLastReservationId() throws Exception;
}
