package dao.custom.impl;

import dao.custom.CrudDAO;
import entity.Reservation;

public interface ReservationDAO extends CrudDAO<Reservation,String> {

  String getLastReservationId() throws Exception;
}
