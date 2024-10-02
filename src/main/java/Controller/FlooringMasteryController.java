package Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.json.JSONObject;

import java.util.InputMismatchException;

import Exceptions.ServiceExceptions;
import Service.FlooringMasteryServiceImpl;
import View.FlooringMasteryViewImpl;

/**
 * The {@code FlooringMasteryController} class serves as the controller in the MVC
 * design pattern for the Flooring Mastery application. It acts as an intermediary
 * between the view and the service layer, handling user input and updating the
 * view accordingly.
 */
@Component
public class FlooringMasteryController {
  private FlooringMasteryViewImpl view;

  private FlooringMasteryServiceImpl service;

  /**
   * Constructs a {@code FlooringMasteryController} with the specified view
   * and service.
   *
   * @param view   the view used to display information to the user
   * @param service the service used to handle business logic
   */
  @Autowired
  public FlooringMasteryController(FlooringMasteryViewImpl view, FlooringMasteryServiceImpl service) {
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
      } catch (InputMismatchException e) { // ensures the user input for the menu is valid
        view.userIOImpl.print("     *** Please Input an Integer ***");
        view.userIOImpl.clearScannerBuffer();
        continue;
      }

      switch(choice) {
        case 1: // Display order for a given date
          JSONObject date = view.displayOrderForDate();
          if (date.getString("date").isEmpty()) {
            break;  // if date is empty there is nothing to display - how the view communicates that
          }
          String result = service.displayOrders(date);
          view.userIOImpl.print(result);
          break;
        case 2: // Add an order
          try {
            view.userIOImpl.print(service.addOrder(view.addOrder()).toString());
          } catch (ServiceExceptions e) {
            view.userIOImpl.print(String.valueOf(e));
          }
          break;
        case 3: // Edit an existing order
          try {
            JSONObject orderInfo = view.editOrder(); // if JSON object is empty that means user typed 'n'
            if (orderInfo.length() != 0) {
              view.userIOImpl.print(service.editAnOrder(orderInfo).toString());
            }
          } catch (ServiceExceptions e) {
            view.userIOImpl.print(String.valueOf(e));
          }
          break;
        case 4: // Remove an order
          JSONObject orderInfo = view.removeOrder();
          boolean shouldRemove = view.displayOrder(service.getOrder(orderInfo));
          if (shouldRemove) { // checks to see if the user typed 'y' before editing the database & files
            try {
              service.removeOrder(orderInfo);
            } catch (ServiceExceptions e) {
              view.userIOImpl.print(String.valueOf(e));
            }
          }
          break;
        case 5: // Export all existing order files to one file titled 'DataExport'
          try {
            service.exportAllData();
            view.userIOImpl.print(" * Check the 'DataExport.txt' file in the 'Backup' folder for the complete order list *");
          } catch (ServiceExceptions e) {
            view.userIOImpl.print(String.valueOf(e));
          }
          break;
        case 6: // Quit program
          view.userIOImpl.print("\n *** Thank You for Your Business! ***");
          return;
        default:
          view.userIOImpl.print("Invalid Input - If You Want To Quit Press 6");
          break;
      }
    }
  }
}