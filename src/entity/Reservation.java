package entity;

import java.sql.Date;

public class Reservation implements SuperEntity{

  private String resvId;
  private Date resvDate;
  private String guestId;
  private Date checkInDate;
  private Date checkOutDate;
  private String userId;

  public Reservation() {
  }

  public Reservation(String resvId, Date resvDate, String guestId, Date checkInDate, Date checkOutDate,
       String userId) {
    this.setResvId(resvId);
    this.setResvDate(resvDate);
    this.setGuestId(guestId);
    this.setCheckInDate(checkInDate);
    this.setCheckOutDate(checkOutDate);
    this.setUserId(userId);
  }


  public String getResvId() {
    return resvId;
  }

  public void setResvId(String resvId) {
    this.resvId = resvId;
  }

  public Date getResvDate() {
    return resvDate;
  }

  public void setResvDate(Date resvDate) {
    this.resvDate = resvDate;
  }

  public String getGuestId() {
    return guestId;
  }

  public void setGuestId(String guestId) {
    this.guestId = guestId;
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

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  @Override
  public String toString() {
    return "Reservation{" +
        "resvId='" + resvId + '\'' +
        ", resvDate=" + resvDate +
        ", guestId='" + guestId + '\'' +
        ", checkInDate=" + checkInDate +
        ", checkOutDate=" + checkOutDate +
        ", userId='" + userId + '\'' +
        '}';
  }
}
