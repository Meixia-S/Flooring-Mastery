package Model.DAO;

import java.math.BigDecimal;
import java.time.LocalDate;

import Model.Order;

public interface IOrdersDAO {
  public void addOrder(LocalDate date, String customerName, String state, String productType, BigDecimal area);

  public void editAnOrder(LocalDate date, Order order, String edits);

  public void removeOrder(LocalDate date, int orderNum);
}