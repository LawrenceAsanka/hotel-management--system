package bo.custom;

import java.util.List;

import business.SuperBO;
import util.GuestTM;

public interface GuestBO extends SuperBO {

  public List<GuestTM> getAllCheckedOutGuests(String status) throws Exception;

  public List<GuestTM> getAllGuest() throws Exception;

 public boolean deleteGuest(String guestId)throws Exception;

  public boolean updateGuest(GuestTM guest)throws Exception;

  public String getNewGuestId()throws Exception;
}
