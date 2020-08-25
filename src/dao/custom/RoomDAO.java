package dao.custom;

import java.util.List;

import dao.CrudDAO;
import entity.Room;

public interface RoomDAO extends CrudDAO<Room,String> {

  public String getLastRoomId() throws Exception;

  //public List<Room> findAvailableRooms(int typeId,String roomStatus) throws Exception;

//  boolean updateCheckoutRooms(Room room) throws Exception;
}
