package bo.custom;

import java.util.List;

import bo.SuperBO;
import util.ReservationDetailTM;
import util.RoomTM;

public interface RoomBO extends SuperBO {

  public String getNewRoomId() throws Exception;

  public List<RoomTM> getAllRooms() throws Exception;

  public boolean saveRoom(String roomNumber,int typeId,String roomStatus)throws Exception;

  public boolean deleteRoom(String roomNumber)throws Exception;

  public boolean updateRoom(String roomNumber,int typeId,String roomStatus)throws Exception;

  List<RoomTM> getAvailableRooms(int typeId,String roomStatus) throws Exception;

  ReservationDetailTM findRoom(String roomNumber) throws Exception;

}
