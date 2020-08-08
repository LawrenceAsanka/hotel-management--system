package dao.custom.impl;

import java.util.List;

import dao.custom.CrudDAO;
import entity.ReservationDetail;
import entity.ReservationDetailPK;

public interface ReservationDetailDAO extends CrudDAO<ReservationDetail, ReservationDetailPK> {

  List<ReservationDetail> findReservedRooms(String id) throws  Exception;
}
