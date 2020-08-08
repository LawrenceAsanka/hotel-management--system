package bo.custom.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import bo.custom.RoomBO;
import dao.DAOFactory;
import dao.DAOType;
import dao.custom.QueryDAO;
import dao.custom.RoomDAO;
import entity.CustomEntity;
import entity.Room;
import javafx.scene.control.Button;
import util.ReservationDetailTM;
import util.RoomTM;

public class RoomBOImpl implements RoomBO {

  private final RoomDAO roomDAO = DAOFactory.getInstance().getDAO(DAOType.ROOM);
  private QueryDAO queryDAO = DAOFactory.getInstance().getDAO(DAOType.QUERY);

  @Override
  public String getNewRoomId() throws Exception {
    String lastRoomId = roomDAO.getLastRoomId();
    if (lastRoomId == null) {
      return "R1";
    } else {
      int maxId = Integer.parseInt(lastRoomId.replace("R", ""));
      maxId=maxId+1;
      String id="R"+maxId;
      return id;
    }
  }

  @Override
  public List<RoomTM> getAllRooms() throws Exception {
    List<CustomEntity> allRoomDetails = queryDAO.getAllRoomDetails();
    List<RoomTM> rooms = new ArrayList<>();
    for (CustomEntity allRoomDetail : allRoomDetails) {
      rooms.add(new RoomTM(allRoomDetail.getRoomNumber(),allRoomDetail.getTypeName(),
          allRoomDetail.getTypePrice(),allRoomDetail.getRoomStatus()));
    }
    return rooms;
  }

  @Override
  public boolean saveRoom(String roomNumber,int typeId,String roomStatus) throws Exception {
    return roomDAO.save(new Room(roomNumber,typeId,roomStatus));
  }

  @Override
  public boolean deleteRoom(String roomNumber) throws Exception {
    return roomDAO.delete(roomNumber);
  }

  @Override
  public boolean updateRoom(String roomNumber, int typeId, String roomStatus) throws Exception {
    return roomDAO.update(new Room(roomNumber,typeId,roomStatus));
  }

  @Override
  public List<RoomTM> getAvailableRooms(int typeId, String roomStatus) throws Exception {
    List<Room> availableRooms = roomDAO.findAvailableRooms(typeId, roomStatus);
    List<RoomTM> rooms = new ArrayList<>();
    for (Room availableRoom : availableRooms) {
      rooms.add(new RoomTM(availableRoom.getRoomNumber(), availableRoom.getTypeId() + "", BigDecimal.valueOf(000.00),
          availableRoom.getRoomStatus()));
    }
    return rooms;
  }

  @Override
  public ReservationDetailTM findRoom(String roomNumber) throws Exception {
    CustomEntity room = queryDAO.findRoom(roomNumber);
    return new ReservationDetailTM(room.getRoomNumber(),room.getTypeName(),room.getTypePrice(),new Button("Delete"));

  }
}
