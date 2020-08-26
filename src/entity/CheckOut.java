package entity;

import java.math.BigDecimal;
import java.sql.Date;

public class CheckOut implements SuperEntity{

  private int checkOutId;
  private String resvId;
  private String userId;
  private Date date;
  private int noOfNight;
  private BigDecimal totalPrice;

  public CheckOut() {
  }

  public CheckOut(int checkOutId, String resvId, String userId, Date date, int noOfNight,BigDecimal totalPrice) {
    this.setCheckOutId(checkOutId);
    this.setResvId(resvId);
    this.setUserId(userId);
    this.setDate(date);
    this.setNoOfNight(noOfNight);
    this.setTotalPrice(totalPrice);
  }

  public CheckOut(String resvId, String userId, Date date, int noOfNight, BigDecimal totalPrice) {
    this.resvId = resvId;
    this.userId = userId;
    this.date = date;
    this.noOfNight = noOfNight;
    this.totalPrice = totalPrice;
  }

  public int getCheckOutId() {
    return checkOutId;
  }

  public void setCheckOutId(int checkOutId) {
    this.checkOutId = checkOutId;
  }

  public String getResvId() {
    return resvId;
  }

  public void setResvId(String resvId) {
    this.resvId = resvId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public int getNoOfNight() {
    return noOfNight;
  }

  public void setNoOfNight(int noOfNight) {
    this.noOfNight = noOfNight;
  }

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
  }

  @Override
  public String toString() {
    return "CheckOut{" +
        "checkOutId=" + checkOutId +
        ", resvId='" + resvId + '\'' +
        ", userId='" + userId + '\'' +
        ", date=" + date +
        ", noOfNight=" + noOfNight +
        ", totalPrice=" + totalPrice +
        '}';
  }
}
