package bo.custom.impl;

import java.util.ArrayList;
import java.util.List;

import business.custom.UserBO;
import dao.DAOFactory;
import dao.DAOType;
import dao.custom.UserDAO;
import entity.User;
import util.UserTM;

public class UserBOImpl implements UserBO {

  private final UserDAO userDAO = DAOFactory.getInstance().getDAO(DAOType.USER);

  @Override
  public List<UserTM> getAllUsers() throws Exception {
    List<User> allUsers = userDAO.findAll();
    ArrayList<UserTM> allUsersArray = new ArrayList<>();
    for (User user : allUsers) {
      allUsersArray.add(new UserTM(user.getUserId(),user.getFirstName(),user.getEmail(),user.getContact(),
          user.getUserRole(),user.getUserName(),user.getPassword()));
    }
    return allUsersArray;
  }

  @Override
  public boolean saveGuest(User user) throws Exception {
    return userDAO.save(user);
  }

  @Override
  public boolean deleteUser(String userId) throws Exception {
    return userDAO.delete(userId);
  }

  @Override
  public boolean updateUser(UserTM user) throws Exception {
    return userDAO.update(new User(user.getUserId(),user.getFirstName(),user.getEmail(),
        user.getContact(),user.getUserRole(),user.getUserName(),
        user.getPassword()));
  }

  @Override
  public String getNewUserId() throws Exception {
    String lastUserId = userDAO.getLastUserId();
    if (lastUserId == null) {
      return "U001";
    } else {
      int maxId = Integer.parseInt(lastUserId.replace("U", ""));
      int id = maxId + 1;
      String newId = "";
      if (id < 10) {
        newId = "U00" + id;
      } else if (id < 100) {
        newId = "U0" + id;
      } else {
        newId = "U" + id;
      }
      return newId;
    }
  }

  @Override
  public String login(String username, String password, String role) throws Exception {
    return userDAO.login(username, password, role);
  }
}
