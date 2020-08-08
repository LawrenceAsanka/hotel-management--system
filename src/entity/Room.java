package entity;

public class Room implements SuperEntity{

  private String roomNumber;
  private int typeId;
  private String roomStatus;

  public Room() {
  }

  public Room(String roomNumber, int typeId, String roomStatus) {
    this.setRoomNumber(roomNumber);
    this.setTypeId(typeId);
    this.setRoomStatus(roomStatus);
  }

  public String getRoomNumber() {
    return roomNumber;
  }

  public void setRoomNumber(String roomNumber) {
    this.roomNumber = roomNumber;
  }

  public int getTypeId() {
    return typeId;
  }

  public void setTypeId(int typeId) {
    this.typeId = typeId;
  }

  public String getRoomStatus() {
    return roomStatus;
  }

  public void setRoomStatus(String roomStatus) {
    this.roomStatus = roomStatus;
  }

  @Override
  public String toString() {
    return "Room{" +
        "roomNumber='" + roomNumber + '\'' +
        ", typeId=" + typeId +
        ", roomStatus='" + roomStatus + '\'' +
        '}';
  }
}
