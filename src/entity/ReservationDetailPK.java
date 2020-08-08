package entity;

import java.io.Serializable;

public class ReservationDetailPK implements Serializable {

  private String resvId;
  private String roomNumber;

  public ReservationDetailPK() {
  }

  public ReservationDetailPK(String resvId, String roomNumber) {
    this.setResvId(resvId);
    this.setRoomNumber(roomNumber);
  }


  public String getResvId() {
    return resvId;
  }

  public void setResvId(String resvId) {
    this.resvId = resvId;
  }

  public String getRoomNumber() {
    return roomNumber;
  }

  public void setRoomNumber(String roomNumber) {
    this.roomNumber = roomNumber;
  }

  @Override
  public String toString() {
    return "ReservationDetailPK{" +
        "resvId='" + resvId + '\'' +
        ", roomNumber='" + roomNumber + '\'' +
        '}';
  }
}
