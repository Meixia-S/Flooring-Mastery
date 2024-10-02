import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Exceptions.ModelExceptions;
import Model.DAO.AuditDAO;
import Model.DAO.OrdersDAO;
import Model.Order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderDAOTest {
  AuditDAO auditDAO = new AuditDAO();

  @Test
  public void testDisplayOrders() throws ModelExceptions {
    OrdersDAO ordersDAO = new OrdersDAO();
    LocalDate date = LocalDate.of(2024, 9, 25);

    // Creating a mock order and adding it to the orderStorage
    Order order1 = new Order(1, "John Doe", "FL", "Tile", BigDecimal.valueOf(100));
    List<Order> ordersList = new ArrayList<>();
    ordersList.add(order1);
    OrdersDAO.orderStorage.put(date, ordersList);

    // Test displayOrders method
    String result = ordersDAO.displayOrders(date);

    // Assert that the result contains the correct order details
    assertTrue(result.contains(order1.toString()));
  }

  @Test
  public void testAddOrder() {
    OrdersDAO ordersDAO = new OrdersDAO();
    LocalDate date = LocalDate.of(2024, 10, 1);

    // Add an order
    Order result = ordersDAO.addOrder(date, "Jane Doe", "CA", "Wood", BigDecimal.valueOf(150));

    // Verify that the order is stored correctly
    List<Order> ordersForDate = OrdersDAO.orderStorage.get(date);
    assertEquals(1, ordersForDate.size());
    assertEquals(result, ordersForDate.get(0));
    assertEquals("Jane Doe", ordersForDate.get(0).getCustomerName());
  }

  @Test
  public void testEditAnOrder() throws ModelExceptions {
    OrdersDAO ordersDAO = new OrdersDAO();
    LocalDate date = LocalDate.of(2024, 9, 25);

    // Adding an initial order
    Order order = new Order(1, "John Doe", "FL", "Carpet", BigDecimal.valueOf(200));
    List<Order> ordersList = new ArrayList<>();
    ordersList.add(order);
    OrdersDAO.orderStorage.put(date, ordersList);

    // Edit the order with new values
    String edits = " ,CA, ,300";
    Order editedOrder = ordersDAO.editAnOrder(date, 1, edits);

    // Verify the changes
    assertEquals("John Doe", editedOrder.getCustomerName());
    assertEquals("CA", editedOrder.getState());
    assertEquals("Carpet", editedOrder.getProductType());
    assertEquals(BigDecimal.valueOf(300.0), editedOrder.getArea());
  }

  @Test
  public void testRemoveOrder() {
    OrdersDAO ordersDAO = new OrdersDAO();
    LocalDate date = LocalDate.of(2024, 10, 1);

    // Adding an order
    Order order = new Order(1, "John Doe", "TX", "Tile", BigDecimal.valueOf(120));
    List<Order> ordersList = new ArrayList<>();
    ordersList.add(order);
    OrdersDAO.orderStorage.put(date, ordersList);

    // Remove the order
    Order removedOrder = ordersDAO.removeOrder(date, 1);

    // Verify that the order was removed
    assertNotNull(removedOrder);
    assertTrue(OrdersDAO.orderStorage.get(date).isEmpty());
  }

  @Test
  public void testGetOrder() {
    OrdersDAO ordersDAO = new OrdersDAO();
    LocalDate date = LocalDate.of(2024, 9, 25);

    // Adding an order
    Order order = new Order(1, "John Doe", "FL", "Vinyl", BigDecimal.valueOf(150));
    List<Order> ordersList = new ArrayList<>();
    ordersList.add(order);
    OrdersDAO.orderStorage.put(date, ordersList);

    // Retrieve the order
    Order retrievedOrder = ordersDAO.getOrder(date, 1);

    // Verify the retrieved order
    assertNotNull(retrievedOrder);
    assertEquals(order, retrievedOrder);
  }
}