package dao.custom.impl;

import java.util.List;

import dao.custom.CrudDAO;
import entity.Guest;

public interface GuestDAO extends CrudDAO<Guest,String> {

  String getLastGuestId() throws Exception;

  List<Guest> findAllCheckedOutGuest(String status) throws Exception;

  boolean updateGuestStatus(String guestId,String status) throws Exception;
}
