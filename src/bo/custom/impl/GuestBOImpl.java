package bo.custom.impl;

import java.util.ArrayList;
import java.util.List;

import business.custom.GuestBO;
import dao.DAOFactory;
import dao.DAOType;
import dao.custom.GuestDAO;
import entity.Guest;
import util.GuestTM;

public class GuestBOImpl implements GuestBO {

  private GuestDAO guestDAO = DAOFactory.getInstance().getDAO(DAOType.GUEST);

  @Override
  public List<GuestTM> getAllCheckedOutGuests(String status) throws Exception {
    List<Guest> allGuests = guestDAO.findAllCheckedOutGuest(status);
    List<GuestTM> guests = new ArrayList<>();
    for (Guest allGuest : allGuests) {
      guests.add(new GuestTM(allGuest.getGuestId(),allGuest.getGuestFirstName(),allGuest.getGuestLastName(),allGuest.getGuestAddress(),
          allGuest.getPassportNumber(),allGuest.getGuestCountry(),allGuest.getGuestContact(),allGuest.getGuestStatus()));
    }
    return guests;
  }

  @Override
  public List<GuestTM> getAllGuest() throws Exception {
    List<Guest> allGuests = guestDAO.findAll();
    List<GuestTM> guests = new ArrayList<>();
    for (Guest allGuest : allGuests) {
      guests.add(new GuestTM(allGuest.getGuestId(),allGuest.getGuestFirstName(),allGuest.getGuestLastName(),allGuest.getGuestAddress(),
          allGuest.getPassportNumber(),allGuest.getGuestCountry(),allGuest.getGuestContact(),allGuest.getGuestStatus()));
    }
    return guests;
  }

  @Override
  public boolean deleteGuest(String guestId) throws Exception {
    return guestDAO.delete(guestId);
  }

  @Override
  public boolean updateGuest(GuestTM guest) throws Exception {
    return guestDAO.update(new Guest(guest.getGuestId(), guest.getGuestFirstName(), guest.getGuestLastName(),
        guest.getGuestAddress(), guest.getPassportNumber(), guest.getGuestCountry(),
        guest.getGuestContact(), guest.getGuestStatus()));
  }

  @Override
  public String getNewGuestId() throws Exception {
    String lastGuestId = guestDAO.getLastGuestId();
    if (lastGuestId == null) {
      return "G001";
    } else {
      int maxId = Integer.parseInt(lastGuestId.replace("G", ""));
      maxId=maxId+1;
      String id="";
      if (maxId<10) {
        id="G00"+maxId;
      } else if (maxId < 100) {
        id = "G0" + maxId;
      } else {
        id="G"+maxId;
      }
      return id;
    }
  }
}
