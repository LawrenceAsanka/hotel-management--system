package bo.custom;

import java.util.List;

import bo.SuperBO;
import entity.User;
import util.UserTM;

public interface UserBO extends SuperBO {

  List<UserTM> getAllUsers() throws Exception;

  boolean saveGuest(User user) throws Exception;

  public boolean deleteUser(String userId)throws Exception;

  public boolean updateUser(UserTM user)throws Exception;

  String getNewUserId() throws Exception;

  String login(String username, String password, String role) throws Exception;
}
