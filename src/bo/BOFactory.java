package bo;

import bo.custom.impl.CheckoutBOImpl;
import bo.custom.impl.GuestBOImpl;
import bo.custom.impl.ReservationBOImpl;
import bo.custom.impl.RoomBOImpl;
import bo.custom.impl.RoomTypeBoImpl;
import bo.custom.impl.UserBOImpl;

public class BOFactory {

  private static BOFactory boFactory;

  private BOFactory() {

  }

  public static BOFactory getInstance() {
    return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
  }

  public <T extends SuperBO> T getBO(BOType boType) {
    switch (boType) {
      case USER:
        return (T) new UserBOImpl();
      case GUEST:
        return (T) new GuestBOImpl();
      case RESERVATION:
        return (T) new ReservationBOImpl();
      case ROOM_TYPE:
        return (T) new RoomTypeBoImpl();
      case ROOM:
        return (T) new RoomBOImpl();
      case CHECKOUT:
        return (T) new CheckoutBOImpl();
      default:
        return null;
    }
  }
}
