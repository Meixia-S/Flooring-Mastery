package View;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;

import Model.DAO.OrdersDAOImpl;
import Model.DAO.ProductsDAO;
import Model.DAO.TaxesDAO;
import Model.Order;
import View.UserIO.UserIOImpl;

/**
 * The {@code FlooringMasteryView} class provides a user interface for the Flooring Mastery application,
 * allowing users to interact with the system through various options such as displaying orders,
 * adding, editing, and removing orders, as well as exporting data.
 *
 * This class utilizes the {@link UserIOImpl} class for handling user input and output, ensuring a
 * consistent user experience. It also includes methods for validating user inputs related to orders
 * and other relevant data, managing both order details and customer information.
 *
 * Key functionalities of the {@code FlooringMasteryView} include:
 *   Displaying a menu of options for user selection.
 *   Gathering and validating order information from the user
 *   Displaying and confirming order details.
 *   Handling input for existing and future dates, order numbers, customer names, states, product types, and areas.
 *
 * This class implements the {@link View} interface, ensuring that it adheres to the defined
 * contract for view components within the Flooring Mastery application.
 */
@Component
public class FlooringMasteryViewImpl implements View {
  public UserIOImpl userIOImpl;
  public FlooringMasteryViewImpl(UserIOImpl userIOImpl) {
    this.userIOImpl = userIOImpl;
  }

  /**
   * Displays the menu options for the flooring program and prompts the user to make a selection.
   *
   * @return the user's menu choice as an integer
   */
  @Override
  public int displayMenu() {
    int choice = userIOImpl.readInt(
            "\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n" +
            "* <<Flooring Program>>\n" +
            "* 1. Display Orders\n" +
            "* 2. Add an Order\n" +
            "* 3. Edit an Order\n" +
            "* 4. Remove an Order\n" +
            "* 5. Export All Data\n" +
            "* 6. Quit\n" +
            "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
    return choice;
  }

  /**
   * Prompts the user for a date to view existing orders and returns the date as a JSONObject.
   *
   * @return a JSONObject containing the date input by the user
   */
  @Override
  public JSONObject displayOrderForDate() {
    userIOImpl.print("\nPlease Enter a Date of the Order to be Viewed");
    userIOImpl.print("----------------------------------------------+");
    userIOImpl.readString("");

    JSONObject orderInfo = new JSONObject();
    orderInfo.put("date", getExistingDate());
    return orderInfo;
  }

  /**
   * Collects the necessary information from the user to create a new order
   * and returns it as a JSONObject.
   *
   * @return a JSONObject containing the details of the new order
   */
  @Override
  public JSONObject addOrder(){
    userIOImpl.print("\nPlease Enter the Required Fields to Create New Order");
    userIOImpl.print("-----------------------------------------------------+");
    userIOImpl.readString("");

    JSONObject orderInfo = new JSONObject();
    orderInfo.put("date", getFutureDate());
    orderInfo.put("name", getValidName("\nEnter Customer's Name: ", false));
    orderInfo.put("state", getValidState("\nEnter State (abbreviation): ", false));
    orderInfo.put("product type", getValidProductType("\nEnter Product Type: ", false));
    orderInfo.put("area", getValidArea("\nEnter Est. Area: ", false));

    return orderInfo;
  }

  /**
   * Prompts the user for existing order information and allows them to
   * enter new details, returning the updated order information as a JSONObject.
   *
   * @return a JSONObject containing the updated order information
   */
  @Override
  public JSONObject editOrder() {
    JSONObject orderInfo = new JSONObject();

    introEditOrder(orderInfo);

    while(true) {
      orderInfo.put("name", getValidName("\nEnter New Customer's Name: ", true));
      orderInfo.put("state", getValidState("\nEnter New State (abbreviation): ", true));
      orderInfo.put("product type", getValidProductType("\nEnter New Product Type: ", true));
      orderInfo.put("area", getValidArea("\nEnter New Est. Area: ", true));

      userIOImpl.print("\n----------------+\n" +
              "Changes: \n" +
              "----------------+\n" +
              "New Name: " + orderInfo.getString("name") + "\n" +
              "New State: " + orderInfo.getString("state") + "\n" +
              "New Product Type: " + orderInfo.getString("product type") + "\n" +
              "New Area: " + orderInfo.getString("area") +
              "\n-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --\n" +
              " * If the field is empty it means that field will remain as is.\n");

      String shouldUpdate = userIOImpl.readString("Do you want to save these changes? (y/n): ");
      if (shouldUpdate.equalsIgnoreCase("y")) {
       break;
      } else {
        userIOImpl.print("   * No changes were made *");
        orderInfo.remove("date");
        orderInfo.remove("order number");
        orderInfo.remove("name");
        orderInfo.remove("state");
        orderInfo.remove("product type");
        orderInfo.remove("area");
        return orderInfo;
      }
    }

    userIOImpl.print("\nHere is the updated order:");
    userIOImpl.print("------------------------------");
    return orderInfo;
  }

  /**
   * Displays the introduction messages as well as retrieving the date and order number of the order
   * the user wants to edit.
   *
   * @param orderInfo the JSON object that contains the order information
   */
  private void introEditOrder(JSONObject orderInfo) {
    userIOImpl.print("\nPlease Enter the New Information When Prompted OR Press Enter to Skip");
    userIOImpl.print("---------------------------------------------------------------");
    userIOImpl.readString("");

    String date = getExistingDate();
    orderInfo.put("date", date);
    orderInfo.put("order number", getExistingOrderNumber(date));

    userIOImpl.print("----------------------------------");
    userIOImpl.print("Now Please Enter New Information: ");
    userIOImpl.print("----------------------------------");
  }

  /**
   * Prompts the user for the date and order number of the order they wish to remove
   * and returns this information as a JSONObject.
   *
   * @return a JSONObject containing the date and order number of the order to be removed
   */
  @Override
  public JSONObject removeOrder() {
    userIOImpl.print("\nPlease Enter the Date and Order Number to Remove Order");
    userIOImpl.print("-------------------------------------------------------+");
    userIOImpl.readString("");

    JSONObject orderInfo = new JSONObject();
    String date = getExistingDate();
    orderInfo.put("date", date);
    orderInfo.put("order number", getExistingOrderNumber(date));

    return orderInfo;
  }

  /**
   * Displays the details of a specific order to the user and asks for confirmation
   * on whether to remove it or not.
   *
   * @param order the order to be displayed and confirmed for removal
   * @return true if the order should be removed; false otherwise
   */
  @Override
  public boolean displayOrder(Order order) {
    userIOImpl.print("This is the order to be removed:");
    userIOImpl.print(order.toString());

    String shouldUpdate = userIOImpl.readString(" * type 'y' for yes and 'n' for no *");

    if (shouldUpdate.equals("y")) {
      userIOImpl.print("\nOrder Removed");
      return true;
    }
    userIOImpl.print("\nAction Revoked");
    return false;
  }

  // Input Getters ---------------------------------------------------------------------------------
  /**
   * Prompts the user to enter an existing order date and validates the input.
   * If the input is empty or the date does not exist in the database,
   * the user is prompted to try again.
   *
   * @return a string representing a valid existing order date in MM/DD/YYYY format
   */
  private String getExistingDate() {
    String stringDate;
    LocalDate date;
    int count = 0;

    while (count < 3) {
      stringDate = userIOImpl.readString("Please Enter An Existing Order Date [MM/DD/YYYY]:");

      if (stringDate.isEmpty()) {
        userIOImpl.print("    * Input cannot be empty. Please try again. *\n");
        continue;
      }

      try {
        date = LocalDate.parse(stringDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        if (!OrdersDAOImpl.orderStorage.containsKey(date)) {
          userIOImpl.print("    * Date does not exist in database. Please try again. *\n");
          count ++;
          continue;
        }

        return stringDate;

      } catch (DateTimeParseException e) {
        userIOImpl.print("    * Invalid date format. Please enter the date in MM/DD/YYYY format. *\n");
      }
    }
    userIOImpl.print("    * Infinite loop break *");
    return "";
  }

  /**
   * Prompts the user to enter a future date and validates the input.
   * If the input is not in the correct format or is not a future date,
   * the user is prompted to try again.
   *
   * @return a string representing a valid future date in MM/DD/YYYY format
   */
  private String getFutureDate() {
    LocalDate currDate = LocalDate.now();
    LocalDate date;
    String stringDate;

    while(true) {
      stringDate = userIOImpl.readString("Enter Date [MM/DD/YYYY]: ");

      try {
        date = LocalDate.parse(stringDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        if (!date.isAfter(currDate)) {
          userIOImpl.print("    * The date must be set in the future - please enter a valid date. *\n");
          continue;
        }

      } catch (DateTimeParseException e) {
        userIOImpl.print("    * Invalid date format. Please enter the date in MM/DD/YYYY format. *\n");
        continue;
      }

      return stringDate;
    }
  }

  /**
   * Prompts the user for an existing order number associated with a specified date
   * and validates the input. If the order number does not exist for the given date,
   * the user is prompted to try again.
   *
   * @param date the date associated with the order to be retrieved
   * @return an integer representing a valid existing order number
   */
  private int getExistingOrderNumber(String date) {
    LocalDate existingDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    int orderNum;

    while(true) {
      try {
        orderNum = userIOImpl.readInt("\nPlease Enter Order Number: ");
        userIOImpl.readString("");

      } catch (InputMismatchException e) {
        userIOImpl.print("  * Invalid input - must be int *\n");
        userIOImpl.readString("");
        continue;
      }

      List<Order> ordersForDate = OrdersDAOImpl.orderStorage.get(existingDate);
      for (int i = 0; i < ordersForDate.size(); i++) {
        if (ordersForDate.get(i).getOrderNumber() == orderNum) {
          return orderNum;
        }
      }
      userIOImpl.print("    * That order number does not exist in the database. *\n");
    }
  }

  /**
   * Prompts the user to enter a customer's name and validates the input.
   * If the input is empty and can be empty, it returns a space.
   * Otherwise, it checks that the name contains only letters and numbers.
   *
   * @param canBeEmpty boolean indicating if the name can be empty
   * @return a string representing a valid customer name
   */
  private String getValidName(String prompt, boolean canBeEmpty) {
    String name;

    while(true) {
      name = userIOImpl.readString(prompt);

      if (canBeEmpty && name.isEmpty()) {
        return " ";
      } else if (name.matches("[a-zA-Z0-9 ]+")) {
        if (name.trim().isEmpty()) {
          userIOImpl.print("    * The customer's name may not be empty - please try again. *\n");
          continue;
        }
        return name;
      }

      userIOImpl.print("    * The customer's name must contains only letters or numbers " +
              "- please enter a valid name. *\n");
    }
  }

  /**
   * Prompts the user to enter a state abbreviation and validates the input.
   * If the input is empty and can be empty, it returns a space.
   * It checks if the state exists in the tax library and prompts the user
   * to try again if it does not.
   *
   * @param canBeEmpty boolean indicating if the state can be empty
   * @return a string representing a valid state abbreviation
   */
  private String getValidState(String prompt, boolean canBeEmpty) {
    String state;

    while(true) {
      state = userIOImpl.readString(prompt).toUpperCase();

      if (canBeEmpty && state.isEmpty()) {
        return " ";
      } else if (TaxesDAO.taxLibrary.containsKey(state)) {
        return state;
      }

      userIOImpl.print("    * Sorry we do NOT conduct business in the given state " +
              "- please enter a valid state. *\n");
    }
  }

  /**
   * Prompts the user to enter a product type and validates the input.
   * If the input is empty and can be empty, it returns a space.
   * It checks if the product type exists in the product library and prompts
   * the user to try again if it does not.
   *
   * @param canBeEmpty boolean indicating if the product type can be empty
   * @return a string representing a valid product type
   */
  private String getValidProductType(String prompt, boolean canBeEmpty) {
    String productType;

    while(true) {
      productType = userIOImpl.readString(prompt);

      if (!productType.isEmpty()) {
        productType = productType.substring(0, 1).toUpperCase() + productType.substring(1).toLowerCase();
      }

      if (canBeEmpty && productType.isEmpty()) {
        return " ";
      } else if (ProductsDAO.productLibrary.containsKey(productType)) {
        return productType;
      }

      userIOImpl.print("    * Sorry we do NOT have that type of product" +
                    "- please enter a valid product type. *\n");
    }
  }

  /**
   * Prompts the user to enter an estimated area and validates the input.
   * If the input is empty and can be empty, it returns a space.
   * It ensures the area is a positive integer, prompting the user to try again
   * if it is not.
   *
   * @param canBeEmpty boolean indicating if the area can be empty
   * @return a string representing a valid estimated area as a positive integer
   */
  private String getValidArea(String prompt, boolean canBeEmpty) {
    String area;

    while(true) {
      area = userIOImpl.readString(prompt);

      if (canBeEmpty && area.isEmpty()) {
        return " ";
      } else if (Integer.valueOf(area) < 1) {
        userIOImpl.print("    * The area must be positive - please enter a positive number. *\n");
        continue;
      } else if (Integer.valueOf(area) < 100 && Integer.valueOf(area) > 1) {
        userIOImpl.print("    * The minimum area is 100 - please enter a number at or above 100. *\n");
        continue;
      }
      return area;
    }
  }
}