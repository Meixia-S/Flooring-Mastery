package Service;

import java.math.BigDecimal;
import java.time.LocalDate;

import Model.DAO.AuditDAO;
import Model.DAO.OrdersDAO;
import Model.DAO.ProductsDAO;

public class FlooringMasteryService implements IService {
  private OrdersDAO ordersDAO;
  private AuditDAO auditDAO;

  public FlooringMasteryService(OrdersDAO ordersDAO, AuditDAO auditDAO) {
    this.ordersDAO = ordersDAO;
    this.auditDAO = auditDAO;
  }

  @Override
  public void addOrder(LocalDate date, String customerName, String state, String productType, BigDecimal area) {
  }

  @Override
  public void editAnOrder(LocalDate date, int orderNum, String edits) {
  }

  @Override
  public void removeOrder(LocalDate date, int orderNum) {
  }

  @Override
  public void exportALlDate() {
  }
}