package dao.custom.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.CrudUtil;
import entity.Room;

public class RoomDAOImpl implements RoomDAO {


  @Override
  public String getLastRoomId() throws Exception {
    ResultSet rst = CrudUtil.execute("SELECT * FROM Room ORDER BY roomNumber DESC LIMIT 1");
    if (!rst.next()) {
      return null;
    } else {
      return rst.getString(1);
    }
  }

  @Override
  public List<Room> findAvailableRooms(int typeId,String roomStatus) throws Exception {
    ResultSet rst = CrudUtil.execute("SELECT * FROM Room WHERE typeId=? AND roomStatus=?",typeId,roomStatus);
    List<Room> rooms = new ArrayList<>();
    while (rst.next()) {
      rooms.add(new Room(rst.getString(1),rst.getInt(2),rst.getString(3)));
    }
    return rooms;
  }

  @Override
  public boolean updateCheckoutRooms(Room room) throws Exception {
    return CrudUtil.execute("UPDATE Room SET roomStatus=? WHERE roomNumber=?",
        room.getRoomStatus(),room.getRoomNumber());
  }

  @Override
  public List<Room> findAll() throws Exception {
    return null;
  }

  @Override
  public Room find(String key) throws Exception {
    ResultSet resultSet = CrudUtil.execute("SELECT * FROM Room WHERE roomNumber=?",key);
    if (resultSet.next()) {
     return new Room(resultSet.getString(1), resultSet.getInt(2), resultSet.getString(3));
    }
    return null;
  }

  @Override
  public boolean save(Room room) throws Exception {
    return CrudUtil.execute("INSERT INTO Room VALUES (?,?,?)",
        room.getRoomNumber(),room.getTypeId(),room.getRoomStatus());
  }

  @Override
  public boolean update(Room room) throws Exception {
    return CrudUtil.execute("UPDATE Room SET typeId=?,roomStatus=? WHERE roomNumber=?",
        room.getTypeId(),room.getRoomStatus(),room.getRoomNumber());
  }

  @Override
  public boolean delete(String key) throws Exception {
    return CrudUtil.execute("DELETE FROM Room WHERE roomNumber=?",key);
  }
}
