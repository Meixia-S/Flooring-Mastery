package Service;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Exceptions.ModelExceptions;
import Exceptions.ServiceExceptions;
import Model.DAO.AuditDAO;
import Model.DAO.OrdersDAO;
import Model.Order;

/**
 * The {@code FlooringMasteryService} class provides services for managing
 * flooring orders. It interacts with data access objects to perform operations
 * such as displaying, adding, editing, removing, and retrieving orders.
 */
public class FlooringMasteryService implements IService {
  private OrdersDAO ordersDAO;
  private AuditDAO auditDAO;

  /**
   * Constructs a new {@code FlooringMasteryService} with the specified
   * {@code OrdersDAO} and {@code AuditDAO}.
   *
   * @param ordersDAO the data access object for orders
   * @param auditDAO  the data access object for audit operations
   */
  public FlooringMasteryService(OrdersDAO ordersDAO, AuditDAO auditDAO) {
    this.ordersDAO = ordersDAO;
    this.auditDAO = auditDAO;
   //LocalDate date = LocalDate.parse("02/25/2025", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    //his.ordersDAO.addOrder(date, "Jane Doe", "IL", "Bamboo",  BigDecimal.valueOf(100));
  }

  /**
   * Displays all orders for a specified date.
   *
   * @param ordersDate a {@code JSONObject} containing the date for which orders
   *                   are to be displayed
   * @return a string representation of all orders for the specified date
   */
  @Override
  public String displayOrders(JSONObject ordersDate) {
    String stringDate = ordersDate.getString("date");
    LocalDate date = LocalDate.parse(stringDate.toString(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));

    return ordersDAO.displayOrders(date);
  }

  /**
   * Adds a new order based on the provided order information.
   *
   * @param orderInfo a {@code JSONObject} containing the details of the order
   *                  to be added
   * @return the newly created {@code Order} object
   * @throws ServiceExceptions if an error occurs while adding the order or
   *                           if the file is not found
   */
  @Override
  public Order addOrder(JSONObject orderInfo) throws ServiceExceptions {
    String stringDate = orderInfo.getString("date");
    LocalDate date = LocalDate.parse(stringDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));

    String name = orderInfo.getString("name");
    String state = orderInfo.getString("state");
    String productType = orderInfo.getString("product type");
    BigDecimal area = BigDecimal.valueOf(Long.parseLong(orderInfo.getString("area")));

    try {
      Order order = ordersDAO.addOrder(date, name, state, productType, area);
      auditDAO.addOrder(date, order);
      return order;
    } catch (ModelExceptions e) {
      throw new ServiceExceptions(e);
    }
  }

  /**
   * Edits an existing order based on the provided new order information.
   *
   * @param newOrderInfo a {@code JSONObject} containing the updated details
   *                     of the order
   * @return the updated {@code Order} object
   * @throws ServiceExceptions if an error occurs while editing the order
   */
  @Override
  public Order editAnOrder(JSONObject newOrderInfo) throws ServiceExceptions {
    String stringDate = newOrderInfo.getString("date");
    LocalDate date = LocalDate.parse(stringDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    int orderNum = newOrderInfo.getInt("order number");

    String name = newOrderInfo.getString("name");
    String state = newOrderInfo.getString("state");
    String productType = newOrderInfo.getString("product type");
    String area = newOrderInfo.getString("area");

    String edits = name + "," + state + "," + productType + "," + area;

    try {
      Order order = ordersDAO.editAnOrder(date, orderNum, edits);
      auditDAO.editAnOrder(date, order);
      return order;
    } catch (ModelExceptions e) {
      throw new ServiceExceptions(e);
    }
  }

  /**
   * Removes an existing order based on the provided order information.
   *
   * @param orderInfo a {@code JSONObject} containing the details of the order
   *                  to be removed
   * @throws ServiceExceptions if an error occurs while removing the order
   */
  @Override
  public void removeOrder(JSONObject orderInfo) throws ServiceExceptions {
    String stringDate = orderInfo.getString("date");
    LocalDate date = LocalDate.parse(stringDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    int orderNumber = orderInfo.getInt("order number");

    try {
      ordersDAO.removeOrder(date, orderNumber);
      auditDAO.removeOrder(date, orderNumber);
    } catch (ModelExceptions e) {
      throw new ServiceExceptions(e);
    }
  }

  /**
   * Retrieves a specific order based on the provided order information.
   *
   * @param orderInfo a {@code JSONObject} containing the details of the order
   *                  to be retrieved
   * @return the {@code Order} object if found
   */
  @Override
  public Order getOrder(JSONObject orderInfo) {
    String stringDate = orderInfo.getString("date");
    LocalDate date = LocalDate.parse(stringDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    int orderNum = orderInfo.getInt("order number");

    return ordersDAO.getOrder(date, orderNum);
  }

  /**
   * Exports all data to the 'DataExport' file.
   *
   * @throws ServiceExceptions if an error occurs while exporting the data
   */
  @Override
  public void exportAllData() throws ServiceExceptions {
    try {
      auditDAO.export();
    } catch (ModelExceptions e) {
      throw new ServiceExceptions(e);
    }
  }
}