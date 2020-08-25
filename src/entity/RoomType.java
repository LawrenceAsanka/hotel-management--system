package entity;

import java.math.BigDecimal;

public class RoomType implements SuperEntity{

  private int typeId;
  private String typeName;
  private BigDecimal typePrice;

  public RoomType() {
  }

  public RoomType(int typeId, String typeName, BigDecimal typePrice) {
    this.typeId = typeId;
    this.typeName = typeName;
    this.typePrice = typePrice;
  }

  public RoomType(String typeName, BigDecimal typePrice) {
    this.typeName = typeName;
    this.typePrice = typePrice;
  }

  public int getTypeId() {
    return typeId;
  }

  public void setTypeId(int typeId) {
    this.typeId = typeId;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public BigDecimal getTypePrice() {
    return typePrice;
  }

  public void setTypePrice(BigDecimal typePrice) {
    this.typePrice = typePrice;
  }

  @Override
  public String toString() {
    return "RoomType{" +
        "typeId=" + typeId +
        ", typeName='" + typeName + '\'' +
        ", typePrice=" + typePrice +
        '}';
  }
}
