package dao.custom;

import java.util.List;

import dao.SuperDAO;
import entity.CustomEntity;

public interface QueryDAO extends SuperDAO {

  List<CustomEntity> getAllRoomDetails() throws Exception;

  CustomEntity findRoom(String roomNumber) throws Exception;

  List<CustomEntity> getReservationsDetails(String status) throws Exception;
}
