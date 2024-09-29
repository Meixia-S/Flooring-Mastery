package Service;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface IService {

  public void displayOrders();

  // Order Date – Must be in the future
  //Customer Name – May not be blank and is limited to characters [a-z][0-9] as well as periods and comma characters. "Acme, Inc." is a valid name.
  //State – Entered states must be checked against the tax file. If the state does not exist in the tax file, we cannot sell there. If the tax file is modified to include the state, it should be allowed without changing the application code.
  //Product Type – Show a list of available products and pricing information to choose from. Again, if a product is added to the file it should show up in the application without a code change.
  //Area – The area must be a positive decimal. Minimum order size is 100 sq ft.
  public void addOrder(LocalDate date, String customerName, String state, String productType, BigDecimal area);

  public void editAnOrder(LocalDate date, int orderNum, String edits);

  public void removeOrder(LocalDate date, int orderNum);

  public void exportALlDate();
}