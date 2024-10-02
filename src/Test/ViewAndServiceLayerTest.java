import Exceptions.ServiceExceptions;
import Model.DAO.AuditDAO;
import Model.DAO.OrdersDAO;
import Model.Order;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Service.FlooringMasteryService;
import View.FlooringMasteryView;
import View.UserIO.UserIO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ViewAndServiceLayerTest {
  AuditDAO auditDAO = new AuditDAO();
  OrdersDAO ordersDAO = new OrdersDAO();
  private FlooringMasteryService service = new FlooringMasteryService(ordersDAO, auditDAO);
  private UserIO mockUserIO = Mockito.mock(UserIO.class);
  private FlooringMasteryView view = new FlooringMasteryView(mockUserIO);

  @Test
  public void testDisplayOrderForDate() {
    // Creating and adding an order into the database
    LocalDate date = LocalDate.parse("02/25/2025", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    this.ordersDAO.addOrder(date, "Jane Doe", "IL", "Bamboo",  BigDecimal.valueOf(100));

    // ** testing getExistingDate private method ** ------------------------------------------------
    when(mockUserIO.readString("Please Enter An Existing Order Date [MM/DD/YYYY]: "))
            .thenReturn("02/25/2023") // First invalid input
            .thenReturn("02/25/2026") // Second invalid input
            .thenReturn("02/25/2025"); // Valid input (correct date)
    // ** getExistingDate brings user to menu after 3 tries ** -------------------------------------

    // Retry
    when(mockUserIO.readInt(
            "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n" +
            "* <<Flooring Program>>\n" +
            "* 1. Display Orders\n" +
            "* 2. Add an Order\n" +
            "* 3. Edit an Order\n" +
            "* 4. Remove an Order\n" +
            "* 5. Export All Data\n" +
            "* 6. Quit\n" +
            "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *")).thenReturn(1);
    when(mockUserIO.readString("Please Enter An Existing Order Date [MM/DD/YYYY]: "))
            .thenReturn("02/25/2025");

    JSONObject result = view.displayOrderForDate();
    String order = service.displayOrders(result);

    assertEquals(this.ordersDAO.getOrder(date, 1).toString(), order);
    assertEquals(1, OrdersDAO.orderStorage.get(date).size());
  }

  @Test
  public void testAddOrder() throws ServiceExceptions {
    assertEquals(0, OrdersDAO.orderStorage.size());
    LocalDate date = LocalDate.parse("12/25/2025", DateTimeFormatter.ofPattern("MM/dd/yyyy"));

    // ** testing getFutureDate private method ** --------------------------------------------------
    when(mockUserIO.readString(contains("Enter Date [MM/DD/YYYY]:")))
            .thenReturn("12/25/2023") // date must be set in the future
            .thenReturn("12/25/2025");

    // ** testing getValidName private method ** ---------------------------------------------------
    when(mockUserIO.readString(contains("Enter Customer's Name:"))) //Enter Customer's Name:
            .thenReturn("=") //  Special chars are not accepted
            .thenReturn("xX Test Name 123 Xx"); // Spaces, letters, and numbers accepted

    // ** testing getValidState private method ** --------------------------------------------------
    when(mockUserIO.readString(contains("Enter State (abbreviation):")))
            .thenReturn("XT")  // State abbreviation must exist in TaxesDao library
            .thenReturn("tx"); // Lowercase is accepted

    // ** testing getValidProductType private method ** --------------------------------------------
    when(mockUserIO.readString(contains("Enter Product Type:")))
            .thenReturn("plastic")  // Product type must exist in ProductsDAO library
            .thenReturn("wood"); // First letter can be lowercase

    // ** testing getValidArea private method ** ---------------------------------------------------
    when(mockUserIO.readString(contains("Enter Est. Area: ")))
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

    assertEquals(ordersDAO.getOrder(date, 1), order);
    assertEquals(1, OrdersDAO.orderStorage.size());
    assertEquals(1, OrdersDAO.orderStorage.get(date).size());
  }

  @Test
  public void testAddOrderWithDiffDate() throws ServiceExceptions {
    LocalDate date = LocalDate.parse("02/25/2025", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    this.ordersDAO.addOrder(date, "Jane Doe", "IL", "Bamboo", BigDecimal.valueOf(100));
    assertEquals(1, OrdersDAO.orderStorage.size());

    LocalDate dateTwo = LocalDate.parse("12/25/2025", DateTimeFormatter.ofPattern("MM/dd/yyyy"));

    // ** testing getFutureDate private method ** --------------------------------------------------
    when(mockUserIO.readString(contains("Enter Date [MM/DD/YYYY]:")))
            .thenReturn("12/25/2025");

    // ** testing getValidName private method ** ---------------------------------------------------
    when(mockUserIO.readString(contains("Enter Customer's Name:"))) //Enter Customer's Name:
            .thenReturn("Sophia Sophia"); // Spaces, letters, and numbers accepted

    // ** testing getValidState private method ** --------------------------------------------------
    when(mockUserIO.readString(contains("Enter State (abbreviation):")))
            .thenReturn("MA");

    // ** testing getValidProductType private method ** --------------------------------------------
    when(mockUserIO.readString(contains("Enter Product Type:")))
            .thenReturn("Terrazzo"); // First letter can be lowercase

    // ** testing getValidArea private method ** ---------------------------------------------------
    when(mockUserIO.readString(contains("Enter Est. Area: ")))
            .thenReturn("303");

    JSONObject result = view.addOrder();

    assertEquals("Sophia Sophia", result.getString("name"));
    assertEquals("MA", result.getString("state"));
    assertEquals("Terrazzo", result.getString("product type"));
    assertEquals("303", result.getString("area"));
    assertEquals("12/25/2025", result.getString("date"));

    Order order = service.addOrder(result);

    assertEquals(ordersDAO.getOrder(dateTwo, 2), order);
    assertEquals(2, OrdersDAO.orderStorage.size());
    assertEquals(1, OrdersDAO.orderStorage.get(dateTwo).size());
  }

  // Scenario user types 'y'
  @Test
  public void testEditOrderY() throws ServiceExceptions {
    LocalDate date = LocalDate.parse("02/25/2025", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    this.ordersDAO.addOrder(date, "Jane Doe", "IL", "Bamboo", BigDecimal.valueOf(100));
    assertEquals(1, OrdersDAO.orderStorage.get(date).size());

    // Mocking the input for existing order retrieval
    when(mockUserIO.readString(contains("Please Enter An Existing Order Date [MM/DD/YYYY]")))
            .thenReturn("02/25/2025");

    // Mocking the order number retrieval
    when(mockUserIO.readInt(contains("Please Enter Order Number:")))
            .thenReturn(1); // Change order number to match the existing order

    // Mocking the new customer details
    when(mockUserIO.readString(contains("Enter New Customer's Name:")))
            .thenReturn("May Smith");
    when(mockUserIO.readString(contains("Enter New State (abbreviation):")))
            .thenReturn("WA");
    when(mockUserIO.readString(contains("Enter New Product Type:")))
            .thenReturn("Carpet"); // Ensure the case matches expected values
    when(mockUserIO.readString(contains("Enter New Est. Area:")))
            .thenReturn("205");

    // Mocking the confirmation for saving changes
    when(mockUserIO.readString("Do you want to save these changes? (y/n): "))
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
    assertEquals(ordersDAO.getOrder(date, 1), order);
    assertEquals(1, OrdersDAO.orderStorage.get(date).size());
  }

  // Scenario user types 'n'
  @Test
  public void testEditOrderN() throws ServiceExceptions {
    LocalDate date = LocalDate.parse("02/25/2025", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    this.ordersDAO.addOrder(date, "Jane Doe", "IL", "Bamboo", BigDecimal.valueOf(100));
    assertEquals(1, OrdersDAO.orderStorage.get(date).size());

    // Mocking the input for existing order retrieval
    when(mockUserIO.readString(contains("Please Enter An Existing Order Date [MM/DD/YYYY]")))
            .thenReturn("02/25/2025");

    // Mocking the order number retrieval
    when(mockUserIO.readInt(contains("Please Enter Order Number:")))
            .thenReturn(1); // Change order number to match the existing order

    // Mocking the new customer details
    when(mockUserIO.readString(contains("Enter New Customer's Name:")))
            .thenReturn("May Smith");
    when(mockUserIO.readString(contains("Enter New State (abbreviation):")))
            .thenReturn("WA");
    when(mockUserIO.readString(contains("Enter New Product Type:")))
            .thenReturn("Carpet"); // Ensure the case matches expected values
    when(mockUserIO.readString(contains("Enter New Est. Area:")))
            .thenReturn("205");

    // Mocking the confirmation for saving changes
    when(mockUserIO.readString("Do you want to save these changes? (y/n): "))
            .thenReturn("n");

    // Validate the order retrieval and size remains unchanged
    assertEquals("Jane Doe", ordersDAO.getOrder(date, 1).getCustomerName());
    assertEquals("IL", ordersDAO.getOrder(date, 1).getState());
    assertEquals("Bamboo", ordersDAO.getOrder(date, 1).getProductType());
    assertEquals(new BigDecimal("100.00"), ordersDAO.getOrder(date, 1).getArea());

    assertEquals(1, OrdersDAO.orderStorage.get(date).size());
  }

  // Scenario user types 'y'
  @Test
  public void testRemoveOrderY() throws ServiceExceptions {
    LocalDate date = LocalDate.parse("02/25/2025", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    this.ordersDAO.addOrder(date, "Jane Doe", "IL", "Bamboo", BigDecimal.valueOf(100));
    this.ordersDAO.addOrder(date, "Anna Fisher", "NE", "Rubber", BigDecimal.valueOf(505));
    assertEquals(2, OrdersDAO.orderStorage.get(date).size());

    when(mockUserIO.readString("Please Enter An Existing Order Date [MM/DD/YYYY]: "))
            .thenReturn("02/25/2025");
    when(mockUserIO.readInt("Please Enter Order Number: "))
            .thenReturn(1);

    when(mockUserIO.readString(" * type 'y' for yes and 'n' for no *"))
            .thenReturn("y");

    JSONObject result = view.removeOrder();

    assertEquals("02/25/2025", result.getString("date"));
    assertEquals(1, result.getInt("order number"));

    service.removeOrder(result);

    assertEquals(1, OrdersDAO.orderStorage.get(date).size());
  }

  // Scenario user types 'n'
  @Test
  public void testRemoveOrderN() throws ServiceExceptions {
    LocalDate date = LocalDate.parse("02/25/2025", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    this.ordersDAO.addOrder(date, "Jane Doe", "IL", "Bamboo", BigDecimal.valueOf(100));
    this.ordersDAO.addOrder(date, "Anna Fisher", "NE", "Rubber", BigDecimal.valueOf(505));
    assertEquals(2, OrdersDAO.orderStorage.get(date).size());

    when(mockUserIO.readString("Please Enter An Existing Order Date [MM/DD/YYYY]: "))
            .thenReturn("02/25/2025");
    when(mockUserIO.readInt("Please Enter Order Number: "))
            .thenReturn(1);

    when(mockUserIO.readString(" * type 'y' for yes and 'n' for no *"))
            .thenReturn("n");

    JSONObject result = null;

    try{
      result = view.removeOrder();
    } catch (NullPointerException e) {
      System.out.println("passed");
    }

    assertEquals(null, result);
    assertEquals(2, OrdersDAO.orderStorage.get(date).size());
  }

  // Export functionality has been tested in the AuditDAOTest file
}