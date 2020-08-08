package dao.custom.impl;

import java.util.List;

import dao.CrudUtil;
import entity.CheckOut;

public class CheckOutDAOImpl implements CheckOutDAO {

  @Override
  public List<CheckOut> findAll() throws Exception {
    return null;
  }

  @Override
  public CheckOut find(String key) throws Exception {
    return null;
  }

  @Override
  public boolean save(CheckOut checkOut) throws Exception {
  return CrudUtil.execute("INSERT INTO CheckOut (resvId, userId, date, noOfNight, totalPrice) VALUES (?,?,?,?,?)",
      checkOut.getResvId(),checkOut.getUserId(),checkOut.getDate(),checkOut.getNoOfNight(),
      checkOut.getTotalPrice());
  }

  @Override
  public boolean update(CheckOut entity) throws Exception {
    return false;
  }

  @Override
  public boolean delete(String key) throws Exception {
    return false;
  }
}
