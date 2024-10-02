import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import Model.DAO.AuditDAOImpl;
import Model.DAO.ProductsDAO;
import Model.DAO.TaxesDAO;
import Model.Product;
import Model.Tax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TaxesAndProductDAOTest {
  AuditDAOImpl auditDAOImpl = new AuditDAOImpl();

  @Test
  public void testAddStateAndGetTax() {
    assertEquals(50, TaxesDAO.taxLibrary.size());

    Tax ilTax = TaxesDAO.taxLibrary.get("IL");
    assertEquals("IL", ilTax.getStateAbbreviation());
    assertEquals("Illinois", ilTax.getStateName());
    assertEquals(BigDecimal.valueOf(6.25), ilTax.getTaxRate());

    // checks to see if it will return false if trying to get state tax that is not in the library.
    assertFalse(TaxesDAO.taxLibrary.containsKey("AC"));
  }

  @Test
  public void testAddStateAndGetProduct() {
    assertEquals(15, ProductsDAO.productLibrary.size());

    Product product = ProductsDAO.productLibrary.get("Bamboo");

    assertEquals("Bamboo", product.getProductType());
    assertEquals(BigDecimal.valueOf(3.95), product.getCostPerSquareFoot());
    assertEquals(BigDecimal.valueOf(4.20), product.getLaborCostPerSquareFoot());

    // checks to see if it will return false if trying to get a product that is not in the library.
    assertFalse(TaxesDAO.taxLibrary.containsKey("AC"));
  }
}