package dao.custom.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.CrudUtil;
import dao.custom.UserDAO;
import entity.User;

public class UserDAOImpl implements UserDAO {

  @Override
  public String getLastUserId() throws Exception {
    ResultSet rst = CrudUtil.execute("SELECT userId FROM User ORDER BY userId DESC LIMIT 1");
    if (!rst.next()) {
      return null;
    } else {
      return rst.getString(1);
    }
  }

  @Override
  public String login(String username, String password, String role) throws Exception {
    ResultSet rst = CrudUtil
        .execute("SELECT * FROM User WHERE userName=? AND password=? AND userRole=?", username, password, role);
    if (!rst.next()) {
      return null;
    } else {
      return rst.getString(1);
    }

  }

  @Override
  public List<User> findAll() throws Exception {
    ResultSet result = CrudUtil.execute("SELECT * FROM User");
    List<User> guests = new ArrayList<>();
    while (result.next()) {
      guests.add(new User(result.getString(1),result.getString(2),result.getString(3),
          result.getString(4),result.getString(5),result.getString(6),
          result.getString(7)));
    }
    return guests;
  }

  @Override
  public User find(String key) throws Exception {
   return null;
  }

  @Override
  public boolean save(User user) throws Exception {
    return CrudUtil.execute("INSERT INTO User VALUES (?,?,?,?,?,?,?)",
        user.getUserId(),user.getFirstName(),user.getEmail(),user.getContact(),user.getUserRole(),
        user.getUserName(),user.getPassword());
  }

  @Override
  public boolean update(User user) throws Exception {
    return CrudUtil.execute("UPDATE User SET firstName=?,email=?,contact=?,userRole=?,"
            + "userName=?,password=? WHERE userId=?",
        user.getFirstName(),user.getEmail(),user.getContact(),user.getUserRole(),user.getUserName(),
        user.getPassword(),user.getUserId());
  }

  @Override
  public boolean delete(String key) throws Exception {
    return CrudUtil.execute("DELETE FROM User WHERE userId=?",key);
  }
}
