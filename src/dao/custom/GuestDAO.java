package dao.custom;

import java.util.List;

import dao.CrudDAO;
import entity.Guest;

public interface GuestDAO extends CrudDAO<Guest,String> {

  String getLastGuestId() throws Exception;

  List<Guest> findAllCheckedOutGuest(String status) throws Exception;

}
