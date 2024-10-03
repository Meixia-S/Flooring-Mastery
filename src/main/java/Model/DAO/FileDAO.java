package Model.DAO;

import java.time.LocalDate;

import Exceptions.ModelExceptions;
import Model.Order;

/**
 * The {@code IAuditDAO} interface defines the contract for the data access
 * layer responsible for managing order-related files within the application.
 *
 * This interface specifies methods for adding, editing, removing, and exporting
 * order data, as well as retrieving product and tax information from specified
 * data files. Implementations of this interface will ensure that order data
 * is handled consistently and reliably throughout the application.
 */
public interface FileDAO {

  /**
   * Adds a new order to the corresponding orders file for the specified date.
   *
   * @param date the date of the order
   * @param order the order to be added
   * @throws ModelExceptions when it detects an IOException if an I/O error occurs while writing to
   * the file
   */
  public void addOrder(LocalDate date, Order order) throws ModelExceptions;

  /**
   * Edits an existing order in the orders file for the specified date.
   *
   * @param date the date of the order to be edited
   * @param order the updated order information
   * @throws ModelExceptions when it detects an IOException if an I/O error occurs while reading or
   * writing to the file
   */
  public void editAnOrder(LocalDate date, Order order) throws ModelExceptions;

  /**
   * Removes an order with the specified order number from the orders file for the specified date.
   *
   * @param date the date of the order to be removed
   * @param orderNum the order number of the order to be removed
   * @throws ModelExceptions when it detects an IOException if an I/O error occurs while reading or
   * writing to the file
   */
  public void removeOrder(LocalDate date, int orderNum) throws ModelExceptions;

  /**
   * Exports all order data from the orders files into a single backup file.
   *
   * @throws ModelExceptions when it detects an IOException if an I/O error occurs while reading or
   * writing to the file
   */
  public void export() throws ModelExceptions;

  /**
   * Reads product information from the specified product data file and adds it to the system.
   *
   * @param productFileName the name of the product data file
   * @throws ModelExceptions when it detects a FileNotFoundException if the product data file is
   * not found
   */
  public void getProductInfo(String productFileName) throws ModelExceptions;

  /**
   * Reads tax information from the specified tax data file and adds it to the system.
   *
   * @param taxFileName the name of the tax data file
   * @throws ModelExceptions when it detects a FileNotFoundException if the tax data file is not
   * found
   */
  public void getTaxInfo(String taxFileName) throws ModelExceptions;
}