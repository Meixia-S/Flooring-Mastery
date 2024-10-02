package Controller;

import org.json.JSONObject;

import java.util.InputMismatchException;

import Exceptions.ServiceExceptions;
import Service.FlooringMasteryService;
import View.FlooringMasteryView;

/**
 * The {@code FlooringMasteryController} class serves as the controller in the MVC
 * design pattern for the Flooring Mastery application. It acts as an intermediary
 * between the view and the service layer, handling user input and updating the
 * view accordingly.
 */
public class FlooringMasteryController {
  private FlooringMasteryView view;

  private FlooringMasteryService service;

  /**
   * Constructs a {@code FlooringMasteryController} with the specified view
   * and service.
   *
   * @param view   the view used to display information to the user
   * @param service the service used to handle business logic
   */
  public FlooringMasteryController(FlooringMasteryView view, FlooringMasteryService service) {
    this.view = view;
    this.service = service;
  }

  /**
   * Runs the main program loop, displaying the menu and processing user choices.
   * It handles user input, interacts with the service to perform actions, and
   * updates the view with the results.
   */
  public void runProgram() {
    int choice;

    while(true) {
      try {
        choice = view.displayMenu();
      } catch (InputMismatchException e) {
        view.userIO.print("     *** Please Input an Integer ***");
        view.userIO.clearScannerBuffer();
        continue;
      }

      switch(choice) {
        case 1: // Display order for a given date
          JSONObject dateAndNumber = view.displayOrderForDate();
          if (dateAndNumber.getString("date").isEmpty()) {
            break;
          }
          String result = service.displayOrders(dateAndNumber);
          view.userIO.print(result);
          break;
        case 2: // Add an order
          try {
            view.userIO.print(service.addOrder(view.addOrder()).toString());
          } catch (ServiceExceptions e) {
            throw new RuntimeException(e);
          }
          break;
        case 3: // Edit an existing order
          try {
            view.userIO.print(service.editAnOrder(view.editOrder()).toString());
          } catch (ServiceExceptions e) {
            throw new RuntimeException(e);
          }
          break;
        case 4: // Remove an order
          JSONObject orderInfo = view.removeOrder();
          boolean shouldRemove = view.displayOrder(service.getOrder(orderInfo));
          if (shouldRemove) {
            try {
              service.removeOrder(orderInfo);
            } catch (ServiceExceptions e) {
              view.userIO.print("Error involving file paths");
            }
          }
          break;
        case 5: // Export all existing order files to one file titled 'DataExport'
          try {
            service.exportAllData();
            view.userIO.print(" * Check the 'DataExport.txt' file in the 'Export' folder for the complete order list *");
          } catch (ServiceExceptions e) {
            throw new RuntimeException(e);
          }
          break;
        case 6: // Quit program
          view.userIO.print("\n *** Thank You for Your Business! ***");
          return;
        default:
          view.userIO.print("Invalid Input - If You Want To Quit Press 6");
          break;
      }
    }
  }
}