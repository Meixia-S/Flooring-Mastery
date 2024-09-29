package Model;

import java.math.BigDecimal;

public class Product {
  private String productType;
  private BigDecimal costPerSquareFoot;
  private BigDecimal laborCostPerSquareFoot;

  public Product(String productType, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot) {
    this.productType = productType;
    this.costPerSquareFoot = costPerSquareFoot;
    this.laborCostPerSquareFoot = laborCostPerSquareFoot;
  }

  // Getter and Setter for productType
  public String getProductType() {
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  // Getter and Setter for costPerSquareFoot
  public BigDecimal getCostPerSquareFoot() {
    return costPerSquareFoot;
  }

  public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
    this.costPerSquareFoot = costPerSquareFoot;
  }

  // Getter and Setter for laborCostPerSquareFoot
  public BigDecimal getLaborCostPerSquareFoot() {
    return laborCostPerSquareFoot;
  }

  public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
    this.laborCostPerSquareFoot = laborCostPerSquareFoot;
  }
}