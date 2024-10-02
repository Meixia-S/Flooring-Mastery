package Model.DAO;

import java.math.BigDecimal;
import java.util.HashMap;

import Model.Product;

/**
 * The {@code ProductsDAO} class serves as a data access object for managing
 * product information in the application.
 *
 * It contains a static {@code HashMap} that acts as a library of products,
 * allowing for the retrieval and addition of product details, such as cost
 * per square foot and labor cost per square foot. The class is populated by
 * the {@code AuditDAO} class, which initializes the product library with
 * relevant data.
 */
public class ProductsDAO {
  public static HashMap<String, Product> productLibrary = new HashMap<>();

  /**
   * Retrieves a {@code Product} from the product library based on the specified product type.
   *
   * @param productType the type of the product to retrieve
   * @return the {@code Product} object corresponding to the specified product type,
   *         or {@code null} if no product exists for that type
   */
  public static Product getProduct(String productType) {
    return productLibrary.get(productType);
  }

  /**
   * Adds a new product to the product library.
   *
   * @param productType             the type of the product to be added
   * @param costPerSquareFoot       the cost per square foot of the product
   * @param laborCostPerSquareFoot  the labor cost per square foot of the product
   */
  public static void addProduct(String productType, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot) {
    Product product = new Product(productType, costPerSquareFoot, laborCostPerSquareFoot);
    productLibrary.put(productType, product);
  }
}