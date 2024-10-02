package App;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import Controller.FlooringMasteryController;

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
    ApplicationContext ctx =
            new ClassPathXmlApplicationContext("applicationContext.xml");
    FlooringMasteryController controller =
            ctx.getBean("controller", FlooringMasteryController.class);
    controller.runProgram();
  }
}