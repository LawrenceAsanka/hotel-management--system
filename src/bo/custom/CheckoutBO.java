package bo.custom;

import java.sql.Date;
import java.util.List;

import bo.SuperBO;
import util.ReservationTM;
import util.RoomTM;

public interface CheckoutBO extends SuperBO {

  boolean checkout(ReservationTM reservation,String userId, Date date,int dateCount,List<RoomTM> room,String status) throws Exception;

  List<RoomTM> findAllCheckoutRooms(String id) throws Exception;
}
