package util;

import java.math.BigDecimal;

import javafx.scene.control.Button;

public class ReservationDetailTM {

  private String roomNumber;
  private String roomType;
  private BigDecimal roomPrice;
  private Button button;

  public ReservationDetailTM() {
  }

  public ReservationDetailTM(String roomNumber, String roomType, BigDecimal roomPrice, Button button) {
    this.roomNumber = roomNumber;
    this.roomType = roomType;
    this.roomPrice = roomPrice;
    this.button = button;
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

  public Button getButton() {
    return button;
  }

  public void setButton(Button button) {
    this.button = button;
  }

  @Override
  public String toString() {
    return "ReservationDetailTM{" +
        "roomNumber='" + roomNumber + '\'' +
        ", roomType='" + roomType + '\'' +
        ", roomPrice=" + roomPrice +
        ", button=" + button +
        '}';
  }
}
