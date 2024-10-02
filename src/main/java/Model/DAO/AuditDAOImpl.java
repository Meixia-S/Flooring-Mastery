package Model.DAO;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Exceptions.ModelExceptions;
import Model.Order;

/**
 * The {@code AuditDAO} class implements the {@code IAuditDAO} interface and serves
 * as the data access layer for managing order-related files within the application.
 *
 * This class provides methods for performing various operations on order data,
 * including adding, editing, removing, and exporting orders. It also handles
 * the retrieval of product and tax information from specified data files.
 * The functionality of this class is crucial for maintaining the integrity
 * and persistence of order-related data in the application.
 */
@Component
public class AuditDAOImpl implements AuditDAO {
  private Scanner reader;
  private PrintWriter writer;

  /**
   * Constructs an {@code AuditDAO} and initializes product and tax information
   * by reading from the respective data files.
   */
  public AuditDAOImpl() {
    try {
      getProductInfo("src\\main\\java\\Files\\Data\\Products.txt");
      getTaxInfo("src\\main\\java\\Files\\Data\\Taxes.txt");
    } catch (ModelExceptions e) {
      throw new RuntimeException("File Paths for Products anf Taxes is invalid!");
    }
  }

  /**
   * Adds a new order to the corresponding orders file for the specified date.
   *
   * @param date the date of the order
   * @param order the order to be added
   * @throws ModelExceptions when it detects an IOException if an I/O error occurs while writing to the file
   */
  @Override
  public void addOrder(LocalDate date, Order order) throws ModelExceptions {
    try {
      File file = new File("src\\main\\java\\Files\\Orders_" + dateToString(date) + ".txt");

      if (!file.exists()) {
        file.createNewFile();
      }

      writer = new PrintWriter(new FileWriter(file, true));

      String formatted = order.getOrderNumber() + "," + order.getCustomerName() + "," +
              order.getState() + "," + order.getTaxRate() + "," +
              order.getProductType() + "," + order.getArea() + "," +
              order.getCostPerSquareFoot() + "," + order.getLaborCostPerSquareFoot() + "," +
              order.getMaterialCost() + "," + order.getLaborCost() + "," +
              order.getTax() + "," + order.getTotal();

      writer.println(formatted);
      writer.close();
    } catch (IOException e) {
      throw new ModelExceptions("File: " + "Orders_" + dateToString(date) + ".txt does not exist");
    }
  }

  /**
   * Edits an existing order in the orders file for the specified date.
   *
   * @param date the date of the order to be edited
   * @param order the updated order information
   * @throws ModelExceptions when it detects an IOException if an I/O error occurs while reading or
   * writing to the file
   */
  @Override
  public void editAnOrder(LocalDate date, Order order) throws ModelExceptions {
    try {
      File file = new File("src\\main\\java\\Files\\Orders_" + dateToString(date) + ".txt");
      reader = new Scanner(new BufferedReader(new FileReader(file)));
      List<String> remainingLines = new ArrayList<>();

      while (reader.hasNext()) {
        String currLine = reader.nextLine();
        String[] fields = currLine.split(",");

        if (Integer.valueOf(fields[0]) == order.getOrderNumber()) {
          String formatted = order.getOrderNumber() + "," + order.getCustomerName() + "," +
                  order.getState() + "," + order.getTaxRate() + "," +
                  order.getProductType() + "," + order.getArea() + "," +
                  order.getCostPerSquareFoot() + "," + order.getLaborCostPerSquareFoot() + "," +
                  order.getMaterialCost() + "," + order.getLaborCost() + "," +
                  order.getTax() + "," + order.getTotal();
          remainingLines.add(formatted);
        } else {
          remainingLines.add(currLine);
        }
      }
      reader.close();
      writer = new PrintWriter(new FileWriter(file));

      for (String line : remainingLines) {
        writer.println(line);
      }
      writer.close();
    } catch (IOException e) {
      throw new ModelExceptions("File: " + "Orders_" + dateToString(date) + ".txt does not exist");
    }
  }

  /**
   * Removes an order with the specified order number from the orders file for the specified date.
   *
   * @param date the date of the order to be removed
   * @param orderNum the order number of the order to be removed
   * @throws ModelExceptions when it detects an IOExceptions if an I/O error occurs while reading or
   *    * writing to the file           
   */
  @Override
  public void removeOrder(LocalDate date, int orderNum) throws ModelExceptions {
    try {
      File file = new File("src\\main\\java\\Files\\Orders_" + dateToString(date) + ".txt");
      reader = new Scanner(new BufferedReader(new FileReader(file)));

      List<String> remainingLines = new ArrayList<>();

      // Read the file and collect all lines except the one containing the order number
      while (reader.hasNext()) {
        String currLine = reader.nextLine();
        String[] fields = currLine.split(",");

        if (Integer.valueOf(fields[0]) != orderNum) {
          remainingLines.add(currLine);
        }
      }
      reader.close();
      writer = new PrintWriter(new FileWriter(file));

      for (String line : remainingLines) {
        writer.println(line);
      }

      writer.close();
    } catch (IOException e) {
      throw new ModelExceptions("File: " + "Orders_" + dateToString(date) + ".txt does not exist");
    }
  }

  /**
   * Exports all order data from the orders files into the 'DataExport' file.
   *
   * @throws ModelExceptions when it detects an IOExceptions if an I/O error occurs while reading or
   * writing to the file
   */
  @Override
  public void export() throws ModelExceptions {
    try {
      writer = new PrintWriter(new FileWriter("src\\main\\java\\Files\\Backup\\DataExport.txt"));
      File[] files = new File("src\\main\\java\\Files").listFiles();

      for (File filename : files) {
        if (filename.isFile()) {
          reader = new Scanner(new BufferedReader(new FileReader(filename)));

          // Formatting date for each file
          String datePart = filename.toString().replaceAll("\\D", "").substring(0, 8);
          // Format it with dashes (MM-dd-yyyy)
          String formattedDate = datePart.substring(0, 2) + "-" +  // Month
                                 datePart.substring(2, 4) + "-" +  // Day
                                 datePart.substring(4, 8);         // Year

          while (reader.hasNext()) {
            String currLine = reader.nextLine() + "," + formattedDate;
            writer.println(currLine);
          }
          reader.close();
        }
      }
      writer.close();
    } catch (IOException e) {
      throw new ModelExceptions("DataExport.txt file path changed");
    }
  }

  /**
   * Reads product information from the specified product data file and adds it to the system.
   *
   * @param productFileName the name of the product data file
   * @throws ModelExceptions when it detects a FileNotFoundExceptions if the product data file is not found
   */
  @Override
  public void getProductInfo(String productFileName) throws ModelExceptions {
    try {
      reader = new Scanner(new BufferedReader(new FileReader(productFileName)));
    } catch (FileNotFoundException e) {
      throw new ModelExceptions("Products.txt file path changed");
    }
    String[] currLine;

    while(reader.hasNext()) {
      currLine = reader.nextLine().split(",");
      ProductsDAO.addProduct(currLine[0],
              BigDecimal.valueOf(Double.valueOf(currLine[1])),
              BigDecimal.valueOf(Double.valueOf(currLine[2])));
    }
    reader.close();
  }

  /**
   * Reads tax information from the specified tax data file and adds it to the system.
   *
   * @param taxFileName the name of the tax data file
   * @throws ModelExceptions when it detects a FileNotFoundExceptions if the tax data file is not found
   */
  @Override
  public void getTaxInfo(String taxFileName) throws ModelExceptions {
    try {
      reader = new Scanner(new BufferedReader(new FileReader(taxFileName)));
    } catch (FileNotFoundException e) {
      throw new ModelExceptions("Taxes.txt file path changed");
    }
    String[] currLine;

    while(reader.hasNext()) {
      currLine = reader.nextLine().split(",");
      TaxesDAO.addState(currLine[0],
              currLine[1],
              BigDecimal.valueOf(Double.valueOf(currLine[2])));
    }
    reader.close();
  }

  /**
   * Converts the given date to a string in the format MMddyyyy.
   *
   * @param date the date to be converted
   * @return the formatted date string
   */
  private String dateToString(LocalDate date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
    return date.format(formatter);
  }
}