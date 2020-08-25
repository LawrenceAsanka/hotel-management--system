package bo.custom.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import bo.custom.RoomTypeBo;
import dao.DAOFactory;
import dao.DAOType;
import dao.custom.RoomTypeDAO;
import entity.RoomType;
import util.RoomTypeTM;

public class RoomTypeBoImpl implements RoomTypeBo {

  private final RoomTypeDAO roomTypeDAO = DAOFactory.getInstance().getDAO(DAOType.ROOM_TYPE);

  @Override
  public List<RoomTypeTM> getAllRoomTypes() throws Exception {
    List<RoomType> allRoomTypes = roomTypeDAO.findAll();
    List<RoomTypeTM> roomTypes = new ArrayList<>();
    for (RoomType roomType : allRoomTypes) {
      roomTypes.add(new RoomTypeTM(roomType.getTypeId(), roomType.getTypeName(), roomType.getTypePrice()));
    }
    return roomTypes;
  }

  @Override
  public boolean saveRoomType(String name, double price) throws Exception {
    return roomTypeDAO.save(new RoomType(name,BigDecimal.valueOf(price)));
  }

  @Override
  public boolean deleteRoomType(String roomTypeId) throws Exception {
    return roomTypeDAO.delete(Integer.parseInt(roomTypeId));
  }

  @Override
  public boolean updateRoomType(int id, String typeName, double typePrice) throws Exception {
    return roomTypeDAO.update(new RoomType(id,typeName, BigDecimal.valueOf(typePrice)));
  }

}
