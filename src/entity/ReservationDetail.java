package entity;

import java.math.BigDecimal;

public class ReservationDetail implements SuperEntity{

  private ReservationDetailPK reservationDetailPK;
  private BigDecimal roomPrice;

  public ReservationDetail() {
  }

  public ReservationDetail(ReservationDetailPK reservationDetailPK, BigDecimal roomPrice) {
    this.reservationDetailPK=reservationDetailPK;
    this.roomPrice=roomPrice;
  }

  public ReservationDetail(String resvId,String roomNumber, BigDecimal roomPrice) {
    this.reservationDetailPK = new ReservationDetailPK(resvId,roomNumber);
    this.roomPrice = roomPrice;
  }


  public ReservationDetailPK getReservationDetailPK() {
    return reservationDetailPK;
  }

  public void setReservationDetailPK(ReservationDetailPK reservationDetailPK) {
    this.reservationDetailPK = reservationDetailPK;
  }

  public BigDecimal getRoomPrice() {
    return roomPrice;
  }

  public void setRoomPrice(BigDecimal roomPrice) {
    this.roomPrice = roomPrice;
  }

  @Override
  public String toString() {
    return "ReservationDetail{" +
        "reservationDetailPK=" + reservationDetailPK +
        ", roomPrice=" + roomPrice +
        '}';
  }
}
