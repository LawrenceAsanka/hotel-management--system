package dao.custom.impl;

import java.util.List;

import dao.custom.SuperDAO;
import entity.CustomEntity;

public interface QueryDAO extends SuperDAO {

  List<CustomEntity> getAllRoomDetails() throws Exception;

  CustomEntity findRoom(String roomNumber) throws Exception;

  List<CustomEntity> getReservationsDetails(String status) throws Exception;
}
