package dao.custom.impl;

import dao.custom.CrudDAO;
import entity.User;

public interface UserDAO extends CrudDAO<User,String> {

  String getLastUserId() throws Exception;
  
  String login(String username,String password,String role) throws Exception;
}
