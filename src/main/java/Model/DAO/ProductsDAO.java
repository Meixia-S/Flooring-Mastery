package Model.DAO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import Model.Product;

public class ProductsDAO {
  private static HashMap<String, Product> productLibrary = new HashMap<>();

  public static Product getProduct(String productType) {
    return productLibrary.get(productType);
  }

  public void addProduct(String productType, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot) {
  }
}