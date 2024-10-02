import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import Model.Order;
import Model.DAO.AuditDAO;
import Exceptions.ModelExceptions;

import static org.junit.jupiter.api.Assertions.*;

class AuditDAOTest {

  private AuditDAO auditDAO;
  private static final LocalDate TEST_DATE = LocalDate.of(2024, 9, 30);
  private static final String TEST_ORDER_FILE_PATH = "src\\main\\java\\Files\\Orders_" + dateToString(TEST_DATE) + ".txt";
  private static final String BACKUP_FILE_PATH = "src\\main\\java\\Files\\Backup\\DataExport.txt";

  @BeforeEach
  public void setUp() throws IOException {
    // Initialize AuditDAO
    auditDAO = new AuditDAO();

    // Clean up test order file if exists
    File testOrderFile = new File(TEST_ORDER_FILE_PATH);
    if (testOrderFile.exists()) {
      testOrderFile.delete();
    }

    // Clean up backup file
    File backupFile = new File(BACKUP_FILE_PATH);
    if (backupFile.exists()) {
      backupFile.delete();
    }
  }

  @AfterEach
  public void tearDown() {
    // Clean up test order file if exists
    File testOrderFile = new File(TEST_ORDER_FILE_PATH);
    if (testOrderFile.exists()) {
      testOrderFile.delete();
    }

    // Clean up backup file
    File backupFile = new File(BACKUP_FILE_PATH);
    if (backupFile.exists()) {
      backupFile.delete();
    }
  }

  @Test
  public void testAddOrder() throws ModelExceptions, IOException {
    // Create a new order
    Order order = new Order(1, "John Doe", "FL", "Tile", BigDecimal.valueOf(100));

    // Add the order to the file
    auditDAO.addOrder(TEST_DATE, order);

    // Verify that the order was added to the file
    File testOrderFile = new File(TEST_ORDER_FILE_PATH);
    assertTrue(testOrderFile.exists());

    // Check file content
    try (Scanner scanner = new Scanner(testOrderFile)) {
      assertTrue(scanner.hasNextLine());
      String line = scanner.nextLine();
      assertTrue(line.contains("1"));
      assertTrue(line.contains("John Doe"));
      assertTrue(line.contains("FL"));
      assertTrue(line.contains("Tile"));
      assertTrue(line.contains("100"));
    }
  }

  @Test
  public void testEditOrder() throws ModelExceptions, IOException {
    // Create an order and add it to the file
    Order order = new Order(1, "John Doe", "FL", "Tile", BigDecimal.valueOf(100));
    auditDAO.addOrder(TEST_DATE, order);

    // Modify the order and edit it in the file
    order.setCustomerName("Jane Doe");
    order.setState("IL");
    auditDAO.editAnOrder(TEST_DATE, order);

    // Verify that the order was edited in the file
    File testOrderFile = new File(TEST_ORDER_FILE_PATH);
    assertTrue(testOrderFile.exists());

    try (Scanner scanner = new Scanner(testOrderFile)) {
      assertTrue(scanner.hasNextLine());
      String line = scanner.nextLine();
      assertTrue(line.contains("Jane Doe"));
      assertTrue(line.contains("IL"));
    }
  }

  @Test
  public void testRemoveOrder() throws ModelExceptions, IOException {
    // Create an order and add it to the file
    Order order = new Order(1, "John Doe", "FL", "Tile", BigDecimal.valueOf(100));
    auditDAO.addOrder(TEST_DATE, order);

    // Remove the order from the file
    auditDAO.removeOrder(TEST_DATE, order.getOrderNumber());

    // Verify that the order was removed from the file
    File testOrderFile = new File(TEST_ORDER_FILE_PATH);
    assertTrue(testOrderFile.exists());

    try (Scanner scanner = new Scanner(testOrderFile)) {
      assertFalse(scanner.hasNextLine());
    }
  }

  @Test
  public void testExport() throws ModelExceptions, IOException {
    // Create two orders and add them to the file
    Order order1 = new Order(1, "John Doe", "FL", "Tile", BigDecimal.valueOf(100));
    Order order2 = new Order(2, "Jane Doe", "OH", "Wood", BigDecimal.valueOf(200));
    auditDAO.addOrder(TEST_DATE, order1);
    auditDAO.addOrder(TEST_DATE, order2);

    // Perform export operation
    auditDAO.export();

    // Verify that the backup file was created and contains the orders
    File backupFile = new File(BACKUP_FILE_PATH);
    assertTrue(backupFile.exists());

    try (Scanner scanner = new Scanner(backupFile)) {
      assertTrue(scanner.hasNextLine());
      String line1 = scanner.nextLine();
      String line2 = scanner.nextLine();
      assertTrue(line1.contains("John Doe"));
      assertTrue(line1.contains("1"));
      assertTrue(line1.contains("FL"));
      assertTrue(line2.contains("Jane Doe"));
      assertTrue(line2.contains("2"));
      assertTrue(line2.contains("OH"));
    }
  }

  // The Tax and Product Methods are tested in the TaxesAndProductDAOTest file

  // Utility method to format the date
  private static String dateToString(LocalDate date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
    return date.format(formatter);
  }
}