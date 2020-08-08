package dao.custom;

import dao.CrudDAO;
import entity.RoomType;

public interface RoomTypeDAO extends CrudDAO<RoomType,String> {

  public boolean saveRoomType(String name,double price) throws Exception;
}
