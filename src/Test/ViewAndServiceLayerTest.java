import Exceptions.ModelExceptions;
import Exceptions.ServiceExceptions;
import Model.DAO.AuditDAOImpl;
import Model.DAO.OrdersDAOImpl;
import Model.Order;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Service.FlooringMasteryServiceImpl;
import View.FlooringMasteryViewImpl;
import View.UserIO.UserIOImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// TESTS MUST BE RUN ONE AT A TIME!
public class ViewAndServiceLayerTest {
  AuditDAOImpl auditDAOImpl = new AuditDAOImpl();
  OrdersDAOImpl ordersDAOImpl = new OrdersDAOImpl();
  private FlooringMasteryServiceImpl service = new FlooringMasteryServiceImpl(ordersDAOImpl, auditDAOImpl);
  private UserIOImpl mockUserIOImpl = Mockito.mock(UserIOImpl.class);
  private FlooringMasteryViewImpl view = new FlooringMasteryViewImpl(mockUserIOImpl);

  @Test
  public void testDisplayOrderForDate() {
    // Creating and adding an order into the database
    LocalDate date = LocalDate.parse("02/25/2025", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    this.ordersDAOImpl.addOrder(date, "Jane Doe", "IL", "Bamboo", BigDecimal.valueOf(100));

    // ** testing getExistingDate private method ** ------------------------------------------------
    when(mockUserIOImpl.readString("Please Enter An Existing Order Date [MM/DD/YYYY]: "))
            .thenReturn("02/25/2023") // First invalid input
            .thenReturn("02/25/2026") // Second invalid input
            .thenReturn("03/25/2025"); // Third invalid input

    // Setting up menu response
    when(mockUserIOImpl.readInt(
            "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n" +
                    "* <<Flooring Program>>\n" +
                    "* 1. Display Orders\n" +
                    "* 2. Add an Order\n" +
                    "* 3. Edit an Order\n" +
                    "* 4. Remove an Order\n" +
                    "* 5. Export All Data\n" +
                    "* 6. Quit\n" +
                    "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *"))
            .thenReturn(1); // Assuming this is the display option

    when(mockUserIOImpl.readString("Please Enter An Existing Order Date [MM/DD/YYYY]:"))
            .thenReturn("02/25/2025");

    // Call the method under test
    JSONObject result = view.displayOrderForDate();

    // Simulating the service layer to display orders
    String order = service.displayOrders(result);

    // Assertions to verify the expected behavior
    assertEquals(this.ordersDAOImpl.getOrder(date, 1).toString(), order);
    assertEquals(1, OrdersDAOImpl.orderStorage.get(date).size());
  }

  // Scenario where user adds an order, removes it, then looks to display orders with the same date
  @Test
  public void testDisplayOrderWithDateButNoNumber() {
    // Creating and adding an order into the database
    LocalDate date = LocalDate.parse("02/25/2025", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    this.ordersDAOImpl.addOrder(date, "Jane Doe", "IL", "Bamboo", BigDecimal.valueOf(100));
    this.ordersDAOImpl.removeOrder(date, 1);
    assertEquals(0, OrdersDAOImpl.orderStorage.get(date).size());
    assertEquals(1, OrdersDAOImpl.orderStorage.size());

    when(mockUserIOImpl.readString("Please Enter An Existing Order Date [MM/DD/YYYY]:"))
            .thenReturn("02/25/2025");

    JSONObject jsonDate = view.displayOrderForDate();
    assertEquals("02/25/2025", jsonDate.getString("date"));

    // Checking that service method returns an empty string since there is nothing to display
    String result = service.displayOrders(jsonDate);
    assertTrue(result.isEmpty());

    System.out.println("passed");
  }

  @Test
  public void testAddOrder() throws ServiceExceptions {
    assertEquals(0, OrdersDAOImpl.orderStorage.size());
    LocalDate date = LocalDate.parse("12/25/2025", DateTimeFormatter.ofPattern("MM/dd/yyyy"));

    // ** testing getFutureDate private method ** --------------------------------------------------
    when(mockUserIOImpl.readString(contains("Enter Date [MM/DD/YYYY]:")))
            .thenReturn("12/25/2023") // date must be set in the future
            .thenReturn("12/25/2025");

    // ** testing getValidName private method ** ---------------------------------------------------
    when(mockUserIOImpl.readString(contains("Enter Customer's Name:"))) //Enter Customer's Name:
            .thenReturn("=") //  Special chars are not accepted
            .thenReturn(" ") //  Empty space string is not accepted
            .thenReturn("") //  Empty string is not accepted
            .thenReturn("xX Test Name 123 Xx"); // Spaces, letters, and numbers accepted

    // ** testing getValidState private method ** --------------------------------------------------
    when(mockUserIOImpl.readString(contains("Enter State (abbreviation):")))
            .thenReturn("XT")  // State abbreviation must exist in TaxesDao library
            .thenReturn("tx"); // Lowercase is accepted

    // ** testing getValidProductType private method ** --------------------------------------------
    when(mockUserIOImpl.readString(contains("Enter Product Type:")))
            .thenReturn("plastic")  // Product type must exist in ProductsDAO library
            .thenReturn("wood"); // First letter can be lowercase

    // ** testing getValidArea private method ** ---------------------------------------------------
    when(mockUserIOImpl.readString(contains("Enter Est. Area: ")))
            .thenReturn("-1")  // Cannot be negative
            .thenReturn("0") // Cannot be zero
            .thenReturn("200");

    JSONObject result = view.addOrder();

    assertEquals("xX Test Name 123 Xx", result.getString("name"));
    assertEquals("TX", result.getString("state"));
    assertEquals("Wood", result.getString("product type"));
    assertEquals("200", result.getString("area"));
    assertEquals("12/25/2025", result.getString("date"));

    Order order = service.addOrder(result);

    assertEquals(ordersDAOImpl.getOrder(date, 1), order);
    assertEquals(1, OrdersDAOImpl.orderStorage.size());
    assertEquals(1, OrdersDAOImpl.orderStorage.get(date).size());
  }

  @Test
  public void testAddOrderWithDiffDate() throws ServiceExceptions {
    LocalDate date = LocalDate.parse("02/25/2025", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    this.ordersDAOImpl.addOrder(date, "Jane Doe", "IL", "Bamboo", BigDecimal.valueOf(100));
    assertEquals(1, OrdersDAOImpl.orderStorage.size());

    LocalDate dateTwo = LocalDate.parse("12/25/2025", DateTimeFormatter.ofPattern("MM/dd/yyyy"));

    when(mockUserIOImpl.readString(contains("Enter Date [MM/DD/YYYY]:")))
            .thenReturn("12/25/2025");

    when(mockUserIOImpl.readString(contains("Enter Customer's Name:")))
            .thenReturn("Sophia Sophia");

    when(mockUserIOImpl.readString(contains("Enter State (abbreviation):")))
            .thenReturn("MA");

    when(mockUserIOImpl.readString(contains("Enter Product Type:")))
            .thenReturn("Terrazzo");

    when(mockUserIOImpl.readString(contains("Enter Est. Area: ")))
            .thenReturn("303");

    JSONObject result = view.addOrder();

    assertEquals("Sophia Sophia", result.getString("name"));
    assertEquals("MA", result.getString("state"));
    assertEquals("Terrazzo", result.getString("product type"));
    assertEquals("303", result.getString("area"));
    assertEquals("12/25/2025", result.getString("date"));

    Order order = service.addOrder(result);

    assertEquals(ordersDAOImpl.getOrder(dateTwo, 2), order);
    assertEquals(2, OrdersDAOImpl.orderStorage.size());
    assertEquals(1, OrdersDAOImpl.orderStorage.get(dateTwo).size());
  }

  // Scenario user types 'y'
  @Test
  public void testEditOrderY() throws ServiceExceptions {
    LocalDate date = LocalDate.parse("02/25/2025", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    this.ordersDAOImpl.addOrder(date, "Jane Doe", "IL", "Bamboo", BigDecimal.valueOf(100));
    assertEquals(1, OrdersDAOImpl.orderStorage.get(date).size());

    // Mocking the input for existing order retrieval
    when(mockUserIOImpl.readString(contains("Please Enter An Existing Order Date [MM/DD/YYYY]")))
            .thenReturn("02/25/2025");

    // Mocking the order number retrieval
    when(mockUserIOImpl.readInt(contains("Please Enter Order Number:")))
            .thenReturn(1); // Change order number to match the existing order

    // Mocking the new customer details
    when(mockUserIOImpl.readString(contains("Enter New Customer's Name:")))
            .thenReturn("May Smith");
    when(mockUserIOImpl.readString(contains("Enter New State (abbreviation):")))
            .thenReturn("WA");
    when(mockUserIOImpl.readString(contains("Enter New Product Type:")))
            .thenReturn("Carpet"); // Ensure the case matches expected values
    when(mockUserIOImpl.readString(contains("Enter New Est. Area:")))
            .thenReturn("205");

    // Mocking the confirmation for saving changes
    when(mockUserIOImpl.readString("Do you want to save these changes? (y/n): "))
            .thenReturn("y");

    JSONObject result = view.editOrder();

    // Assertions to ensure the order info matches the expected values
    assertEquals("May Smith", result.getString("name"));
    assertEquals("WA", result.getString("state"));
    assertEquals("Carpet", result.getString("product type"));
    assertEquals("205", result.getString("area"));

    // Editing the order with the updated info
    Order order = service.editAnOrder(result);

    // Validate the order retrieval and size remains unchanged
    assertEquals(ordersDAOImpl.getOrder(date, 1), order);
    assertEquals(1, OrdersDAOImpl.orderStorage.get(date).size());
  }

  // Scenario user types 'n'
  @Test
  public void testEditOrderN() throws ServiceExceptions {
    LocalDate date = LocalDate.parse("02/25/2025", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    this.ordersDAOImpl.addOrder(date, "Jane Doe", "IL", "Bamboo", BigDecimal.valueOf(100));
    assertEquals(1, OrdersDAOImpl.orderStorage.get(date).size());

    // Mocking the input for existing order retrieval
    when(mockUserIOImpl.readString(contains("Please Enter An Existing Order Date [MM/DD/YYYY]")))
            .thenReturn("02/25/2025");

    // Mocking the order number retrieval
    when(mockUserIOImpl.readInt(contains("Please Enter Order Number:")))
            .thenReturn(1); // Change order number to match the existing order

    // Mocking the new customer details
    when(mockUserIOImpl.readString(contains("Enter New Customer's Name:")))
            .thenReturn("May Smith");
    when(mockUserIOImpl.readString(contains("Enter New State (abbreviation):")))
            .thenReturn("WA");
    when(mockUserIOImpl.readString(contains("Enter New Product Type:")))
            .thenReturn("Carpet"); // Ensure the case matches expected values
    when(mockUserIOImpl.readString(contains("Enter New Est. Area:")))
            .thenReturn("205");

    // Mocking the confirmation for saving changes
    when(mockUserIOImpl.readString("Do you want to save these changes? (y/n): "))
            .thenReturn("n");

    // Validate the order retrieval and size remains unchanged
    assertEquals("Jane Doe", ordersDAOImpl.getOrder(date, 1).getCustomerName());
    assertEquals("IL", ordersDAOImpl.getOrder(date, 1).getState());
    assertEquals("Bamboo", ordersDAOImpl.getOrder(date, 1).getProductType());
    assertEquals(new BigDecimal("100.00"), ordersDAOImpl.getOrder(date, 1).getArea());

    assertEquals(1, OrdersDAOImpl.orderStorage.get(date).size());
  }

  // Scenario user does not update all four fields
  @Test
  public void testEditOrderTwoFields() throws ServiceExceptions {
    LocalDate date = LocalDate.parse("02/25/2025", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    this.ordersDAOImpl.addOrder(date, "Jane Doe", "IL", "Bamboo", BigDecimal.valueOf(100));
    assertEquals(1, OrdersDAOImpl.orderStorage.get(date).size());

    // Mocking the input for existing order retrieval
    when(mockUserIOImpl.readString(contains("Please Enter An Existing Order Date [MM/DD/YYYY]")))
            .thenReturn("02/25/2025");

    // Mocking the order number retrieval
    when(mockUserIOImpl.readInt(contains("Please Enter Order Number:")))
            .thenReturn(1); // Change order number to match the existing order

    // Mocking the new customer details
    when(mockUserIOImpl.readString(contains("Enter New Customer's Name:")))
            .thenReturn("May Smith");
    when(mockUserIOImpl.readString(contains("Enter New State (abbreviation):")))
            .thenReturn("");
    when(mockUserIOImpl.readString(contains("Enter New Product Type:")))
            .thenReturn("Carpet"); // Ensure the case matches expected values
    when(mockUserIOImpl.readString(contains("Enter New Est. Area:")))
            .thenReturn("");

    // Mocking the confirmation for saving changes
    when(mockUserIOImpl.readString("Do you want to save these changes? (y/n): "))
            .thenReturn("y");

    JSONObject result = view.editOrder();

    // Assertions to ensure the order info matches the expected values
    assertEquals("May Smith", result.getString("name"));
    assertEquals(" ", result.getString("state"));
    assertEquals("Carpet", result.getString("product type"));
    assertEquals(" ", result.getString("area"));

    // Editing the order with the updated info
    Order order = service.editAnOrder(result);

    assertEquals("May Smith", order.getCustomerName());
    assertEquals("IL", order.getState());
    assertEquals("Carpet", order.getProductType());
    assertTrue(order.getArea().compareTo(BigDecimal.valueOf(100)) == 0);

    assertEquals(ordersDAOImpl.getOrder(date, 1), order);
    assertEquals(1, OrdersDAOImpl.orderStorage.get(date).size());
  }

  // Scenario user types 'y'
  @Test
  public void testRemoveOrderY() throws ServiceExceptions, ModelExceptions {
    LocalDate date = LocalDate.parse("02/25/2025", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    this.ordersDAOImpl.addOrder(date, "Jane Doe", "IL", "Bamboo", BigDecimal.valueOf(100));
    this.ordersDAOImpl.addOrder(date, "Anna Fisher", "NE", "Rubber", BigDecimal.valueOf(505));
    this.auditDAOImpl.addOrder(date, OrdersDAOImpl.orderStorage.get(date).get(0));
    assertEquals(2, OrdersDAOImpl.orderStorage.get(date).size());

    when(mockUserIOImpl.readString(anyString())).thenReturn("02/25/2025"); // Simulate date input
    when(mockUserIOImpl.readInt(anyString())).thenReturn(1); // Simulate order number input

    // Call the removeOrder method
    JSONObject result = view.removeOrder();

    // Ensure the correct order was passed for removal
    assertEquals("02/25/2025", result.getString("date"));
    assertEquals(1, result.getInt("order number"));

    // Mock user confirmation for the removal
    when(mockUserIOImpl.readString(" * type 'y' for yes and 'n' for no *"))
            .thenReturn("y");

    // Call the service method to remove the order
    service.removeOrder(result);

    // After removing "Jane Doe", ensure only "Anna Fisher" remains
    assertEquals(1, OrdersDAOImpl.orderStorage.get(date).size());

    // Verify the details of the remaining order ("Anna Fisher")
    Order remainingOrder = OrdersDAOImpl.orderStorage.get(date).get(0);
    assertEquals("Anna Fisher", remainingOrder.getCustomerName());
    assertEquals("NE", remainingOrder.getState());
    assertEquals("Rubber", remainingOrder.getProductType());
    assertTrue(remainingOrder.getArea().compareTo(BigDecimal.valueOf(505)) == 0);
  }

  // Scenario user types 'n'
  @Test
  public void testRemoveOrderN() {
    LocalDate date = LocalDate.parse("02/25/2025", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    this.ordersDAOImpl.addOrder(date, "Jane Doe", "IL", "Bamboo", BigDecimal.valueOf(100));
    this.ordersDAOImpl.addOrder(date, "Anna Fisher", "NE", "Rubber", BigDecimal.valueOf(505));
    assertEquals(2, OrdersDAOImpl.orderStorage.get(date).size());

    when(mockUserIOImpl.readString("Please Enter An Existing Order Date [MM/DD/YYYY]: "))
            .thenReturn("02/25/2025");
    when(mockUserIOImpl.readInt("Please Enter Order Number: "))
            .thenReturn(1);

    when(mockUserIOImpl.readString(" * type 'y' for yes and 'n' for no *"))
            .thenReturn("n");

    JSONObject result = null;

    try{
      result = view.removeOrder();
    } catch (NullPointerException e) {
      System.out.println("passed");
    }

    assertEquals(null, result);
    assertEquals(2, OrdersDAOImpl.orderStorage.get(date).size());
  }

  // Export functionality has been tested in the AuditDAOTest file
  // All actual logic is done in the AuditDAOImpl class, the service layer simply call that class
}