package util;

import java.math.BigDecimal;

public class RoomTM {

  private String roomNumber;
  private String roomType;
  private BigDecimal roomPrice;
  private String roomStatus;


  public RoomTM() {
  }

  public RoomTM(String roomNumber, String roomType, BigDecimal roomPrice, String roomStatus) {
    this.setRoomNumber(roomNumber);
    this.setRoomType(roomType);
    this.setRoomPrice(roomPrice);
    this.setRoomStatus(roomStatus);
  }

  public RoomTM(String roomNumber, String roomType, String roomStatus) {
    this.roomNumber = roomNumber;
    this.roomType = roomType;
    this.roomStatus = roomStatus;
  }

  public String getRoomNumber() {
    return roomNumber;
  }

  public void setRoomNumber(String roomNumber) {
    this.roomNumber = roomNumber;
  }

  public String getRoomType() {
    return roomType;
  }

  public void setRoomType(String roomType) {
    this.roomType = roomType;
  }

  public BigDecimal getRoomPrice() {
    return roomPrice;
  }

  public void setRoomPrice(BigDecimal roomPrice) {
    this.roomPrice = roomPrice;
  }

  public String getRoomStatus() {
    return roomStatus;
  }

  public void setRoomStatus(String roomStatus) {
    this.roomStatus = roomStatus;
  }

  @Override
  public String toString() {
    return roomNumber;
  }
}
