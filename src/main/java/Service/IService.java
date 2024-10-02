package Service;

import org.json.JSONObject;

import Exceptions.ServiceExceptions;
import Model.Order;

public interface IService {
  /**
   * Displays all orders for a specified date.
   *
   * @param ordersDate a {@code JSONObject} containing the date for which orders
   *                   are to be displayed
   * @return a string representation of all orders for the specified date
   * @throws ServiceExceptions if the specified date does not exist in the
   *                           database or if an error occurs while retrieving
   *                           the orders
   */
  public String displayOrders(JSONObject ordersDate) throws ServiceExceptions;

  /**
   * Adds a new order based on the provided order information.
   *
   * @param orderInfo a {@code JSONObject} containing the details of the order
   *                  to be added
   * @return the newly created {@code Order} object
   * @throws ServiceExceptions if an error occurs while adding the order or
   *                           if the file is not found
   */
  public Order addOrder(JSONObject orderInfo) throws ServiceExceptions;

  /**
   * Edits an existing order based on the provided new order information.
   *
   * @param newOrderInfo a {@code JSONObject} containing the updated details
   *                     of the order
   * @return the updated {@code Order} object
   * @throws ServiceExceptions if an error occurs while editing the order
   */
  public Order editAnOrder(JSONObject newOrderInfo) throws ServiceExceptions;

  /**
   * Removes an existing order based on the provided order information.
   *
   * @param orderInfo a {@code JSONObject} containing the details of the order
   *                  to be removed
   * @throws ServiceExceptions if an error occurs while removing the order
   */
  public void removeOrder(JSONObject orderInfo) throws ServiceExceptions;

  /**
   * Retrieves a specific order based on the provided order information.
   *
   * @param orderInfo a {@code JSONObject} containing the details of the order
   *                  to be retrieved
   * @return the {@code Order} object if found
   * @throws RuntimeException if an error occurs while retrieving the order
   */
  public Order getOrder(JSONObject orderInfo);

  /**
   * Exports all data to the 'DataExport' file.
   *
   * @throws ServiceExceptions if an error occurs while exporting the data
   */
  public void exportAllData() throws ServiceExceptions;
}