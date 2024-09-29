package Controller;

import Service.FlooringMasteryService;
import View.FlooringMasteryView;

public class FlooringMasteryController {
  private FlooringMasteryView view;
  private FlooringMasteryService service;

  public FlooringMasteryController(FlooringMasteryView view, FlooringMasteryService service) {
    this.view = view;
    this.service = service;
  }

  public void runProgram() {

  }
}