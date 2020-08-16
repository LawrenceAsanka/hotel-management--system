package bo.custom;

import java.util.List;

import bo.SuperBO;
import util.GuestTM;
import util.ReservationDetailTM;
import util.ReservationTM;

public interface ReservationBO extends SuperBO {

  public boolean reserved(GuestTM guestTM,String guestId, String userId,ReservationTM reservationTM, List<ReservationDetailTM> reservationDetail,String reservationStatus)throws Exception;

  String getNewReservationId() throws Exception;

  List<ReservationTM> reservationDetails(String status) throws Exception;


}
