package dao.custom.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.CrudUtil;
import dao.custom.RoomTypeDAO;
import entity.RoomType;

public class RoomTypeDAOImpl implements RoomTypeDAO {

  @Override
  public List<RoomType> findAll() throws Exception {
    ResultSet rst = CrudUtil.execute("SELECT * FROM RoomType");
    List<RoomType> roomTypes = new ArrayList<>();
    while (rst.next()) {
      roomTypes.add(new RoomType(rst.getInt(1), rst.getString(2), rst.getBigDecimal(3)));
    }
    return roomTypes;
  }

  @Override
  public RoomType find(String key) throws Exception {
    return null;
  }

  @Override
  public boolean save(RoomType roomType) throws Exception {
    return false;
  }

  @Override
  public boolean update(RoomType roomType) throws Exception {
    return CrudUtil.execute("UPDATE RoomType SET typeName=?,typePrice=? WHERE typeId=?",
        roomType.getTypeName(), roomType.getTypePrice(), roomType.getTypeId());
  }

  @Override
  public boolean delete(String key) throws Exception {
    return CrudUtil.execute("DELETE FROM RoomType WHERE typeId=?", key);
  }

  @Override
  public boolean saveRoomType(String name, double price) throws Exception {
    return CrudUtil.execute("INSERT INTO RoomType (typeName,typePrice) VALUES (?,?)",
        name, price);
  }
}
