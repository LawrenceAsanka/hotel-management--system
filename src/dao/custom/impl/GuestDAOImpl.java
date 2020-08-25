package dao.custom.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.CrudUtil;
import dao.custom.GuestDAO;
import entity.Guest;

public class GuestDAOImpl implements GuestDAO {


  @Override
  public List<Guest> findAll() throws Exception {
    ResultSet rst = CrudUtil.execute("SELECT * FROM Guest");
    List<Guest> guests = new ArrayList<>();
    while (rst.next()) {
      guests.add(new Guest(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),
          rst.getString(5),rst.getString(6),rst.getString(7),rst.getString(8)));
    }
    return guests;
  }

  @Override
  public Guest find(String key) throws Exception {
    ResultSet rst = CrudUtil.execute("SELECT * FROM Guest WHERE guestId=?",key);
    if (!rst.next()) {
      return null;
    }
    return new Guest(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),
        rst.getString(5),rst.getString(6),rst.getString(7),rst.getString(8));
  }

  @Override
  public boolean save(Guest guest) throws Exception {
    return CrudUtil.execute("INSERT INTO Guest "
        + "(guestId,guestFirstName,guestLastName,guestAddress,passportNumber,guestCountry,guestContact)"
        + " VALUES (?,?,?,?,?,?,?)"
        ,guest.getGuestId(),guest.getGuestFirstName(),guest.getGuestLastName(),guest.getGuestAddress(),guest.getPassportNumber(),
        guest.getGuestCountry(),guest.getGuestContact());
  }

  @Override
  public boolean update(Guest guest) throws Exception {
    return CrudUtil.execute("UPDATE Guest SET guestFirstName=?,guestLastName=?,"
        + "guestAddress=?,passportNumber=?,guestCountry=?,guestContact=?,guestStatus=? WHERE guestId=?",
        guest.getGuestFirstName(),guest.getGuestLastName(),guest.getGuestAddress(),
        guest.getPassportNumber(),guest.getGuestCountry(),guest.getGuestContact(),guest.getGuestStatus(),guest.getGuestId());
  }

  @Override
  public boolean delete(String key) throws Exception {
    return CrudUtil.execute("DELETE FROM Guest WHERE guestId=?",key);
  }

  @Override
  public String getLastGuestId() throws Exception {
    ResultSet rst =  CrudUtil.execute("SELECT guestId FROM Guest ORDER BY guestId DESC LIMIT 1");
    if (!rst.next()) {
      return null;
    }
    return rst.getString(1);
  }

  @Override
  public List<Guest> findAllCheckedOutGuest(String status) throws Exception {
    ResultSet rst = CrudUtil.execute("SELECT * FROM Guest WHERE guestStatus=?",status);
    List<Guest> guests = new ArrayList<>();
    while (rst.next()) {
      guests.add(new Guest(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),
          rst.getString(5),rst.getString(6),rst.getString(7),rst.getString(8)));
    }
    return guests;
  }

}
