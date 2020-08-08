package entity;

import java.math.BigDecimal;
import java.sql.Date;

public class CustomEntity implements SuperEntity{

  private String roomNumber;
  private String typeName;
  private BigDecimal typePrice;
  private String roomStatus;
  private String reservationId;
  private String guestId;
  private String guestFirstName;
  private Date checkoutDate;
  private Date checkinDate;

  public CustomEntity() {
  }

  public CustomEntity(String roomNumber, String typeName, BigDecimal typePrice, String roomStatus) {
    this.setRoomNumber(roomNumber);
    this.setTypeName(typeName);
    this.setTypePrice(typePrice);
    this.setRoomStatus(roomStatus);
  }

  public CustomEntity(String roomNumber, String typeName, BigDecimal typePrice) {
    this.roomNumber = roomNumber;
    this.typeName = typeName;
    this.typePrice = typePrice;
  }

  public CustomEntity(String reservationId,String guestId,String guestFirstName,Date checkinDate,
      Date checkoutDate,BigDecimal typePrice) {
    this.reservationId = reservationId;
    this.guestId = guestId;
    this.guestFirstName = guestFirstName;
    this.checkoutDate = checkoutDate;
    this.checkinDate = checkinDate;
    this.typePrice = typePrice;
  }

  public String getRoomNumber() {
    return roomNumber;
  }

  public void setRoomNumber(String roomNumber) {
    this.roomNumber = roomNumber;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public BigDecimal getTypePrice() {
    return typePrice;
  }

  public void setTypePrice(BigDecimal typePrice) {
    this.typePrice = typePrice;
  }

  public String getRoomStatus() {
    return roomStatus;
  }

  public void setRoomStatus(String roomStatus) {
    this.roomStatus = roomStatus;
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

  public String getGuestFirstName() {
    return guestFirstName;
  }

  public void setGuestFirstName(String guestFirstName) {
    this.guestFirstName = guestFirstName;
  }

  public Date getCheckoutDate() {
    return checkoutDate;
  }

  public void setCheckoutDate(Date checkoutDate) {
    this.checkoutDate = checkoutDate;
  }

  public Date getCheckinDate() {
    return checkinDate;
  }

  public void setCheckinDate(Date checkinDate) {
    this.checkinDate = checkinDate;
  }
}
