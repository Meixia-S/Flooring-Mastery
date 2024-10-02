package View;

import org.json.JSONObject;

import Model.Order;

/**
 * The IView interface defines the methods for interacting with the user
 * in the Flooring Program. Implementing classes must provide the
 * functionality to display menus, collect user input, and return
 * order-related information.
 */
public interface IView {
  /**
   * Displays the menu options for the flooring program and prompts the user to make a selection.
   *
   * @return the user's menu choice as an integer
   */
  public int displayMenu();

  /**
   * Prompts the user for a date to view existing orders and returns the date as a JSONObject.
   *
   * @return a JSONObject containing the date input by the user
   */
  public JSONObject displayOrderForDate();

  /**
   * Collects the necessary information from the user to create a new order
   * and returns it as a JSONObject.
   *
   * @return a JSONObject containing the details of the new order
   */
  public JSONObject addOrder();

  /**
   * Prompts the user for existing order information and allows them to
   * enter new details, returning the updated order information as a JSONObject.
   *
   * @return a JSONObject containing the updated order information
   */
  public JSONObject editOrder();

  /**
   * Prompts the user for the date and order number of the order they wish to remove
   * and returns this information as a JSONObject.
   *
   * @return a JSONObject containing the date and order number of the order to be removed
   */
  public JSONObject removeOrder();

  /**
   * Displays the details of a specific order to the user and asks for confirmation
   * on whether to remove it or not.
   *
   * @param order the order to be displayed and confirmed for removal
   * @return true if the order should be removed; false otherwise
   */
  public boolean displayOrder(Order order);
}