package App;

import Controller.FlooringMasteryController;
import Service.FlooringMasteryService;
import View.FlooringMasteryView;
import Model.DAO.AuditDAO;
import Model.DAO.OrdersDAO;
import View.UserIO.UserIO;

/**
 * The App class serves as the entry point for the Flooring Mastery
 * application. It initializes the necessary components, including
 * the user interface, data access objects, and service layers,
 * and starts the program by invoking the main run method of the
 * controller.
 */
public class App {

  /**
  * The main method that serves as the entry point for the Flooring
  * Mastery application. It initializes the UserIO, view, data access
  * objects, service layer, and controller, then runs the program.
  */
  public static void main(String[] args) {
    UserIO userIO = new UserIO();
    FlooringMasteryView view = new FlooringMasteryView(userIO);
    AuditDAO auditDAO = new AuditDAO();
    OrdersDAO ordersDAO = new OrdersDAO();
    FlooringMasteryService service = new FlooringMasteryService(ordersDAO, auditDAO);
    FlooringMasteryController controller = new FlooringMasteryController(view, service);
    controller.runProgram();
  }
}