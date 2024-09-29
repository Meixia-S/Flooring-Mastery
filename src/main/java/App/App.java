package App;

import java.io.IOException;

import Controller.FlooringMasteryController;
import Model.DAO.AuditDAO;
import Model.DAO.OrdersDAO;
import Model.DAO.ProductsDAO;
import Service.FlooringMasteryService;
import View.FlooringMasteryView;
import View.UserIO.UserIO;

public class App {
  public static void main(String[] args) throws IOException {
    UserIO userIO = new UserIO();
    FlooringMasteryView view = new FlooringMasteryView(userIO);
    AuditDAO auditDAO = new AuditDAO();
    OrdersDAO ordersDAO = new OrdersDAO();
    ProductsDAO productsDAO = new ProductsDAO();
    FlooringMasteryService service = new FlooringMasteryService(ordersDAO, auditDAO);
    FlooringMasteryController controller = new FlooringMasteryController(view, service);
    controller.runProgram();
  }
}