package util;

import java.math.BigDecimal;

public class RoomTypeTM {

  private int roomTypeId;
  private String roomType;
  private BigDecimal roomTypePrice;

  public RoomTypeTM() {
  }

  public RoomTypeTM(int roomTypeId, String roomType, BigDecimal roomTypePrice) {
    this.setRoomTypeId(roomTypeId);
    this.setRoomType(roomType);
    this.setRoomTypePrice(roomTypePrice);
  }



  public int getRoomTypeId() {
    return roomTypeId;
  }

  public void setRoomTypeId(int roomTypeId) {
    this.roomTypeId = roomTypeId;
  }

  public String getRoomType() {
    return roomType;
  }

  public void setRoomType(String roomType) {
    this.roomType = roomType;
  }

  public BigDecimal getRoomTypePrice() {
    return roomTypePrice;
  }

  public void setRoomTypePrice(BigDecimal roomTypePrice) {
    this.roomTypePrice = roomTypePrice;
  }

  @Override
  public String toString() {
    return roomType;
  }
}
