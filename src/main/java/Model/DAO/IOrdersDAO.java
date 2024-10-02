package Model.DAO;

import java.math.BigDecimal;
import java.time.LocalDate;

import Exceptions.ModelExceptions;
import Model.Order;

/**
 * The {@code IOrdersDAO} interface defines the contract for data access
 * operations related to orders in the application.
 *
 * It provides methods for displaying, adding, editing, removing, and
 * retrieving orders, ensuring that any implementing class adheres to this
 * structure for managing order data efficiently.
 */
public interface IOrdersDAO {

  /**
   * Displays all orders for a specified date.
   *
   * @param date the date for which orders are to be displayed
   * @return a string representation of all orders for the specified date
   */
  public String displayOrders(LocalDate date);

  /**
   * Adds a new order for a specified date and returns the created order.
   *
   * @param date         the date of the order
   * @param customerName the name of the customer placing the order
   * @param state       the state where the order is placed
   * @param productType  the type of product ordered
   * @param area        the area for which the order is placed
   * @return the newly created {@code Order} object
   */
  public Order addOrder(LocalDate date, String customerName, String state, String productType, BigDecimal area);

  /**
   * Edits an existing order for a specified date and returns the updated order.
   *
   * @param date     the date of the order to be edited
   * @param orderNum the order number of the order to be edited
   * @param edits    a string containing the new values for the order, separated by commas
   * @return the updated {@code Order} object
   * @throws ModelExceptions if the specified order number or date does not exist in the database
   */
  public Order editAnOrder(LocalDate date, int orderNum, String edits) throws ModelExceptions;

  /**
   * Removes an existing order for a specified date and returns the removed order.
   *
   * @param date     the date of the order to be removed
   * @param orderNum the order number of the order to be removed
   * @return the removed {@code Order} object, or null if the order was not found
   */
  public Order removeOrder(LocalDate date, int orderNum);

  /**
   * Retrieves a specific order by its order number for a specified date.
   *
   * @param date     the date of the order
   * @param orderNum the order number of the order to be retrieved
   * @return the {@code Order} object if found
   */
  public Order getOrder(LocalDate date, int orderNum);
}