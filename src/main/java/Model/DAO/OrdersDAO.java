package Model.DAO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import Model.Order;

public class OrdersDAO implements IOrdersDAO {
  private static HashMap<LocalDate, List<Order>> orderStorage = new HashMap<>();

  @Override
  public void addOrder(LocalDate date, String customerName, String state, String productType, BigDecimal area) {
  }

  @Override
  public void editAnOrder(LocalDate date, Order order, String edits) {
  }

  @Override
  public void removeOrder(LocalDate date, int orderNum) {
  }
}