package Model.DAO;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import Exceptions.ModelExceptions;
import Model.Order;

/**
 * The {@code OrdersDAO} class implements the {@code IOrdersDAO} interface
 * and serves as the data access object for managing orders within the application.
 *
 * This class provides methods to display, add, edit, and remove orders, as well as
 * retrieve specific orders based on their order number and date. It utilizes a
 * HashMap to store orders indexed by their date.
 */
@Component
public class OrdersDAOImpl implements OrdersDAO {
  public static HashMap<LocalDate, List<Order>> orderStorage = new HashMap<>();
  private static int orderAmount = 1;

  /**
   * Displays all orders for a specified date.
   *
   * @param date the date for which orders are to be displayed
   * @return a string representation of all orders for the specified date
   */
  @Override
  public String displayOrders(LocalDate date) {
    String orders = "";

    orders = orderStorage.get(date).stream()
            .map(Order::toString)
            .collect(Collectors.joining());

    return orders;
  }

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
  @Override
  public Order addOrder(LocalDate date, String customerName, String state, String productType, BigDecimal area) {
    int orderNum = orderAmount;
    Order order = new Order(orderNum, customerName, state, productType, area);
    List<Order> ordersForDate = orderStorage.get(date);

    if (!orderStorage.containsKey(date)) {
      ordersForDate = new ArrayList<>();
    }

    ordersForDate.add(order);
    orderStorage.put(date, ordersForDate);
    orderAmount++;

    return order;
  }

  /**
   * Edits an existing order for a specified date and returns the updated order.
   *
   * @param date     the date of the order to be edited
   * @param orderNum the order number of the order to be edited
   * @param edits    a string containing the new values for the order, separated by commas
   * @return the updated {@code Order} object
   * @throws ModelExceptions if the specified order number or date does not exist in the database
   */
  @Override
  public Order editAnOrder(LocalDate date, int orderNum, String edits) throws ModelExceptions {
    if (orderStorage.containsKey(date)) {
      List<Order> ordersForDate = orderStorage.get(date);

      return ordersForDate.stream()
              .filter(order -> order.getOrderNumber() == orderNum)
              .findFirst()
              .map(order -> update(edits, order))
              .orElse(null);
    }
    throw new ModelExceptions("Order number " + orderNum + " not found for the specified date.");
  }

  /**
   * Updates the specified order with new values based on the provided edits string.
   *
   * @param edits the string containing new values for the order
   * @param order the order object to be updated
   * @return the updated {@code Order} object
   */
  private Order update(String edits, Order order) {
    String[] fields = edits.split(",");

    if (!fields[0].equals(" ")) {
      order.setCustomerName(fields[0]);
    }

    if (!fields[1].equals(" ")) {
      order.setState(fields[1]);
    }
    if (!fields[2].equals(" ")) {
      order.setProductType(fields[2]);
    }

    if (!fields[3].equals(" ")) {
      order.setArea(BigDecimal.valueOf(Double.valueOf(fields[3])));
    }

    order.set();
    return order;
  }

  /**
   * Removes an existing order for a specified date and returns the removed order.
   *
   * @param date     the date of the order to be removed
   * @param orderNum the order number of the order to be removed
   * @return the removed {@code Order} object, or null if the order was not found
   */
  @Override
  public Order removeOrder(LocalDate date, int orderNum) {
    Order order = null;
    if (orderStorage.containsKey(date)) {
      List<Order> ordersForDate = orderStorage.get(date);
      int index = -1;

      for (int i = 0; i < ordersForDate.size(); i++) {
        if (ordersForDate.get(i).getOrderNumber() == orderNum) {
          order = ordersForDate.get(i);
          index = i;
          break;
        }
      }

      if (index != -1) {
        ordersForDate.remove(index);
        orderStorage.put(date, ordersForDate);
      }
    }
    return order;
  }

  /**
   * Retrieves a specific order by its order number for a specified date.
   *
   * @param date     the date of the order
   * @param orderNum the order number of the order to be retrieved
   * @return the {@code Order} object if found
   */
  @Override
  public Order getOrder(LocalDate date, int orderNum) {
    List<Order> ordersForDate = orderStorage.get(date);

    return ordersForDate.stream()
            .filter(order -> order.getOrderNumber() == orderNum)
            .findFirst()
            .orElse(null);
  }
}