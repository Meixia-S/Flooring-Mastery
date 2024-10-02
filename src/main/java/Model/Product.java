package Model;

import java.math.BigDecimal;

/**
 * The {@code Product} class represents a product with associated cost
 * information for materials and labor. It contains details such as the
 * product type, cost per square foot, and labor cost per square foot.
 */
public class Product {
  private String productType;
  private BigDecimal costPerSquareFoot;
  private BigDecimal laborCostPerSquareFoot;

  /**
   * Constructs a new {@code Product} object with the specified details.
   *
   * @param productType           the type of the product
   * @param costPerSquareFoot     the cost per square foot of the product
   * @param laborCostPerSquareFoot the labor cost per square foot for the product
   */
  public Product(String productType, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot) {
    this.productType = productType;
    this.costPerSquareFoot = costPerSquareFoot;
    this.laborCostPerSquareFoot = laborCostPerSquareFoot;
  }

  // Getter and Setter for productType

  /**
   * Returns the type of the product.
   *
   * @return the product type
   */
  public String getProductType() {
    return productType;
  }

  /**
   * Sets the type of the product.
   *
   * @param productType the new product type
   */
  public void setProductType(String productType) {
    this.productType = productType;
  }

  // Getter and Setter for costPerSquareFoot

  /**
   * Returns the cost per square foot of the product.
   *
   * @return the cost per square foot
   */
  public BigDecimal getCostPerSquareFoot() {
    return costPerSquareFoot;
  }

  /**
   * Sets the cost per square foot of the product.
   *
   * @param costPerSquareFoot the new cost per square foot
   */
  public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
    this.costPerSquareFoot = costPerSquareFoot;
  }

  // Getter and Setter for laborCostPerSquareFoot
  
  /**
   * Returns the labor cost per square foot of the product.
   *
   * @return the labor cost per square foot
   */
  public BigDecimal getLaborCostPerSquareFoot() {
    return laborCostPerSquareFoot;
  }

  /**
   * Sets the labor cost per square foot of the product.
   *
   * @param laborCostPerSquareFoot the new labor cost per square foot
   */
  public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
    this.laborCostPerSquareFoot = laborCostPerSquareFoot;
  }
}