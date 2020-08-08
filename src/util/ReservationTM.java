package util;

import java.math.BigDecimal;
import java.sql.Date;

public class ReservationTM {

  private String reservationId;
  private String guestId;
  private String guestName;
  private Date checkInDate;
  private Date checkOutDate;
  private BigDecimal netAmount;


  public ReservationTM() {
  }


  public ReservationTM(String reservationId, String guestId, String guestName, Date checkInDate,
      Date checkOutDate, BigDecimal netAmount) {
    this.reservationId = reservationId;
    this.guestId = guestId;
    this.guestName = guestName;
    this.checkInDate = checkInDate;
    this.checkOutDate = checkOutDate;
    this.netAmount = netAmount;
  }

  public String getReservationId() {
    return reservationId;
  }

  public void setReservationId(String reservationId) {
    this.reservationId = reservationId;
  }

  public String getGuestId() {
    return guestId;
  }

  public void setGuestId(String guestId) {
    this.guestId = guestId;
  }

  public String getGuestName() {
    return guestName;
  }

  public void setGuestName(String guestName) {
    this.guestName = guestName;
  }

  public Date getCheckInDate() {
    return checkInDate;
  }

  public void setCheckInDate(Date checkInDate) {
    this.checkInDate = checkInDate;
  }

  public Date getCheckOutDate() {
    return checkOutDate;
  }

  public void setCheckOutDate(Date checkOutDate) {
    this.checkOutDate = checkOutDate;
  }

  public BigDecimal getNetAmount() {
    return netAmount;
  }

  public void setNetAmount(BigDecimal netAmount) {
    this.netAmount = netAmount;
  }

  @Override
  public String toString() {
    return "ReservationTM{" +
        "reservationId='" + reservationId + '\'' +
        ", guestId='" + guestId + '\'' +
        ", guestName='" + guestName + '\'' +
        ", checkInDate=" + checkInDate +
        ", checkOutDate=" + checkOutDate +
        ", netAmount=" + netAmount +
        '}';
  }

}
