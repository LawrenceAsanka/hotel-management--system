package dao.custom.impl;

import java.util.List;

import dao.custom.CrudDAO;
import entity.Room;

public interface RoomDAO extends CrudDAO<Room,String> {

  public String getLastRoomId() throws Exception;

  public List<Room> findAvailableRooms(int typeId,String roomStatus) throws Exception;

  boolean updateCheckoutRooms(Room room) throws Exception;
}
