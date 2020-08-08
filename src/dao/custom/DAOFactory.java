package dao.custom;

import dao.custom.impl.CheckOutDAOImpl;
import dao.custom.impl.GuestDAOImpl;
import dao.custom.impl.QueryDAOImpl;
import dao.custom.impl.ReservationDAOImpl;
import dao.custom.impl.ReservationDetailDAOImpl;
import dao.custom.impl.RoomDAOImpl;
import dao.custom.impl.RoomTypeDAOImpl;
import dao.custom.impl.UserDAOImpl;

public class DAOFactory {

  private static DAOFactory daoFactory;

  private DAOFactory() {
  }

  public static DAOFactory getInstance() {
    return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
  }

  public <T extends SuperDAO> T getDAO(DAOType daoType) {
    switch (daoType) {
      case ROOM:
        return (T) new RoomDAOImpl();
      case GUEST:
        return (T) new GuestDAOImpl();
      case RESERVATION:
        return (T) new ReservationDAOImpl();
      case RESERVATION_DETAIL:
        return (T) new ReservationDetailDAOImpl();
      case CHECKOUT:
        return (T) new CheckOutDAOImpl();
      case ROOM_TYPE:
        return (T) new RoomTypeDAOImpl();
      case USER:
        return (T) new UserDAOImpl();
      case QUERY:
        return (T) new QueryDAOImpl();
      default:
        return null;
    }
  }
}
