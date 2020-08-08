package dao.custom;

import java.util.List;

import dao.CrudDAO;
import entity.ReservationDetail;
import entity.ReservationDetailPK;

public interface ReservationDetailDAO extends CrudDAO<ReservationDetail, ReservationDetailPK> {

  List<ReservationDetail> findReservedRooms(String id) throws  Exception;
}
