package entity;

public class Guest implements SuperEntity{

  private String guestId;
  private String guestFirstName;
  private String guestLastName;
  private String guestAddress;
  private String passportNumber;
  private String guestCountry;
  private String guestContact;
  private String guestStatus;

  public Guest() {
  }

  public Guest(String guestId, String guestFirstName, String guestLastName, String guestAddress,
      String passportNumber, String guestCountry, String guestContact, String guestStatus) {
    this.setGuestId(guestId);
    this.setGuestFirstName(guestFirstName);
    this.setGuestLastName(guestLastName);
    this.setGuestAddress(guestAddress);
    this.setPassportNumber(passportNumber);
    this.setGuestCountry(guestCountry);
    this.setGuestContact(guestContact);
    this.setGuestStatus(guestStatus);
  }

  public String getGuestId() {
    return guestId;
  }

  public void setGuestId(String guestId) {
    this.guestId = guestId;
  }

  public String getGuestFirstName() {
    return guestFirstName;
  }

  public void setGuestFirstName(String guestFirstName) {
    this.guestFirstName = guestFirstName;
  }

  public String getGuestLastName() {
    return guestLastName;
  }

  public void setGuestLastName(String guestLastName) {
    this.guestLastName = guestLastName;
  }

  public String getGuestAddress() {
    return guestAddress;
  }

  public void setGuestAddress(String guestAddress) {
    this.guestAddress = guestAddress;
  }

  public String getPassportNumber() {
    return passportNumber;
  }

  public void setPassportNumber(String passportNumber) {
    this.passportNumber = passportNumber;
  }

  public String getGuestCountry() {
    return guestCountry;
  }

  public void setGuestCountry(String guestCountry) {
    this.guestCountry = guestCountry;
  }

  public String getGuestContact() {
    return guestContact;
  }

  public void setGuestContact(String guestContact) {
    this.guestContact = guestContact;
  }

  public String getGuestStatus() {
    return guestStatus;
  }

  public void setGuestStatus(String guestStatus) {
    this.guestStatus = guestStatus;
  }

  @Override
  public String toString() {
    return "Guest{" +
        "guestId='" + guestId + '\'' +
        ", guestFirstName='" + guestFirstName + '\'' +
        ", guestLastName='" + guestLastName + '\'' +
        ", guestAddress='" + guestAddress + '\'' +
        ", passportNumber='" + passportNumber + '\'' +
        ", guestCountry='" + guestCountry + '\'' +
        ", guestContact='" + guestContact + '\'' +
        ", guestStatus='" + guestStatus + '\'' +
        '}';
  }
}
