package Model.DAO;

import java.time.LocalDate;

import Model.Order;

public interface IAuditDAO {
  public void addOrder(LocalDate date, Order order);

  public void editAnOrder(LocalDate date, Order order, String edits);

  public void removeOrder(LocalDate date, int orderNum);

  public void export();

  public void getTaxInfo(String taxFileName);

  public void getProductInfo(String productFileName);
}