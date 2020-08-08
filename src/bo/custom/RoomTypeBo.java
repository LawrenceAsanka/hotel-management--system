package bo.custom;

import java.util.List;

import bo.SuperBO;
import util.RoomTypeTM;

public interface RoomTypeBo extends SuperBO {

  public List<RoomTypeTM> getAllRoomTypes() throws Exception;

  public boolean saveRoomType(String name,double price)throws Exception;

  public boolean deleteRoomType(String roomId)throws Exception;

  public boolean updateRoomType(int id, String typeName, double typePrice)throws Exception;

}
