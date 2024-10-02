package Model;

import java.math.RoundingMode;
import java.math.BigDecimal;

import Model.DAO.ProductsDAO;
import Model.DAO.TaxesDAO;

/**
 * The {@code Order} class represents an order in the application.
 * It contains details such as the order number, customer information,
 * state, product type, area, costs, and tax information. The class
 * also provides methods to calculate costs based on the product and
 * tax information retrieved from the {@code ProductsDAO} and
 * {@code TaxesDAO} classes.
 */
public class Order {
  private int orderNumber;
  private String customerName;
  private String state;
  private BigDecimal taxRate;
  private String productType;
  private BigDecimal area;
  private BigDecimal costPerSquareFoot;
  private BigDecimal laborCostPerSquareFoot;
  private BigDecimal materialCost;
  private BigDecimal laborCost;
  private BigDecimal tax;
  private BigDecimal total;

  /**
   * Constructs a new {@code Order} object with the specified details.
   *
   * @param orderNumber  the unique identifier for the order
   * @param customerName the name of the customer placing the order
   * @param state        the state where the order is placed
   * @param productType  the type of product ordered
   * @param area         the area for which the order is placed
   */
  public Order(int orderNumber, String customerName, String state, String productType, BigDecimal area) {
    this.orderNumber = orderNumber;
    this.customerName = customerName;
    this.state = state;
    this.productType = productType;
    this.area = area.setScale(2, RoundingMode.HALF_UP);
    set();
  }

  /**
   * Calculates and sets the cost, labor cost, tax, and total for the order
   * based on the product and tax information retrieved from the
   * {@code ProductsDAO} and {@code TaxesDAO} classes.
   */
  public void set() {
    this.costPerSquareFoot = ProductsDAO.getProduct(this.productType)
            .getCostPerSquareFoot().setScale(2, RoundingMode.HALF_UP);
    this.laborCostPerSquareFoot = ProductsDAO.getProduct(this.productType)
            .getCostPerSquareFoot().setScale(2, RoundingMode.HALF_UP);
    this.materialCost = this.area.multiply(this.costPerSquareFoot)
            .setScale(2, RoundingMode.HALF_UP);
    this.laborCost = this.area.multiply(this.laborCostPerSquareFoot)
            .setScale(2, RoundingMode.HALF_UP);
    this.taxRate = TaxesDAO.getTax(this.state).getTaxRate();
    this.tax = (this.materialCost.add(this.laborCost)).multiply(this.taxRate)
            .setScale(2, RoundingMode.HALF_UP);
    this.total = (this.materialCost.add(this.laborCost)).add(this.tax)
            .setScale(2, RoundingMode.HALF_UP);
  }

  // Getter and Setter for orderNumber

  /**
   * Returns the order number of this order.
   *
   * @return the order number
   */
  public int getOrderNumber() {
    return orderNumber;
  }

  /**
   * Sets the order number for this order.
   *
   * @param orderNumber the new order number
   */
  public void setOrderNumber(int orderNumber) {
    this.orderNumber = orderNumber;
  }

  // Getter and Setter for customerName

  /**
   * Returns the customer name for this order.
   *
   * @return the customer name
   */
  public String getCustomerName() {
    return customerName;
  }

  /**
   * Sets the customer name for this order.
   *
   * @param customerName the new customer name
   */
  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  // Getter and Setter for state

  /**
   * Returns the state where this order is placed.
   *
   * @return the state
   */
  public String getState() {
    return state;
  }

  /**
   * Sets the state for this order.
   *
   * @param state the new state
   */
  public void setState(String state) {
    this.state = state;
  }

  // Getter and Setter for taxRate

  /**
   * Returns the tax rate for this order.
   *
   * @return the tax rate
   */
  public BigDecimal getTaxRate() {
    return taxRate;
  }

  /**
   * Sets the tax rate for this order.
   *
   * @param taxRate the new tax rate
   */
  public void setTaxRate(BigDecimal taxRate) {
    this.taxRate = taxRate;
  }

  // Getter and Setter for productType

  /**
   * Returns the product type for this order.
   *
   * @return the product type
   */
  public String getProductType() {
    return productType;
  }

  /**
   * Sets the product type for this order.
   *
   * @param productType the new product type
   */
  public void setProductType(String productType) {
    this.productType = productType;
  }

  // Getter and Setter for area

  /**
   * Returns the area for which this order is placed.
   *
   * @return the area
   */
  public BigDecimal getArea() {
    return area;
  }

  /**
   * Sets the area for this order.
   *
   * @param area the new area
   */
  public void setArea(BigDecimal area) {
    this.area = area;
  }

  // Getter and Setter for costPerSquareFoot

  /**
   * Returns the cost per square foot for this order.
   *
   * @return the cost per square foot
   */
  public BigDecimal getCostPerSquareFoot() {
    return costPerSquareFoot;
  }

  /**
   * Sets the cost per square foot for this order.
   *
   * @param costPerSquareFoot the new cost per square foot
   */
  public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
    this.costPerSquareFoot = costPerSquareFoot;
  }

  // Getter and Setter for laborCostPerSquareFoot

  /**
   * Returns the labor cost per square foot for this order.
   *
   * @return the labor cost per square foot
   */
  public BigDecimal getLaborCostPerSquareFoot() {
    return laborCostPerSquareFoot;
  }

  /**
   * Sets the labor cost per square foot for this order.
   *
   * @param laborCostPerSquareFoot the new labor cost per square foot
   */
  public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
    this.laborCostPerSquareFoot = laborCostPerSquareFoot;
  }

  // Getter and Setter for materialCost

  /**
   * Returns the material cost for this order.
   *
   * @return the material cost
   */
  public BigDecimal getMaterialCost() {
    return materialCost;
  }

  /**
   * Sets the material cost for this order.
   *
   * @param materialCost the new material cost
   */
  public void setMaterialCost(BigDecimal materialCost) {
    this.materialCost = materialCost;
  }

  // Getter and Setter for laborCost

  /**
   * Returns the labor cost for this order.
   *
   * @return the labor cost
   */
  public BigDecimal getLaborCost() {
    return laborCost;
  }

  /**
   * Sets the labor cost for this order.
   *
   * @param laborCost the new labor cost
   */
  public void setLaborCost(BigDecimal laborCost) {
    this.laborCost = laborCost;
  }

  // Getter and Setter for tax

  /**
   * Returns the tax for this order.
   *
   * @return the tax amount
   */
  public BigDecimal getTax() {
    return tax;
  }

  /**
   * Sets the tax for this order.
   *
   * @param tax the new tax amount
   */
  public void setTax(BigDecimal tax) {
    this.tax = tax;
  }

  // Getter and Setter for total

  /**
   * Returns the total cost for this order, including materials, labor, and tax.
   *
   * @return the total cost
   */
  public BigDecimal getTotal() {
    return total;
  }

  /**
   * Sets the total cost for this order.
   *
   * @param total the new total cost
   */
  public void setTotal(BigDecimal total) {
    this.total = total;
  }

  /**
   * Returns a string representation of this order, including all details.
   *
   * @return a formatted string containing order details
   */
  @Override
  public String toString() {
    String order = "\n+------------------------------------------------+\n" +
            "Order Number: " + orderNumber + "\n" +
            "Order Customer Name: " + customerName + "\n" +
            "Order State: " + state + "\n" +
            "Order Tax Rate: " + taxRate + "\n" +
            "Order Product Type: " + productType + "\n" +
            "Order Area: " + area + "\n" +
            "Order Cost Per Square Foot: " + costPerSquareFoot + "\n" +
            "Order Labor Cost Per Square Foot: " + laborCostPerSquareFoot + "\n" +
            "Order Material Cost: " + materialCost + "\n" +
            "Order Labor Cost: " + laborCost + "\n" +
            "Order Tax: " + tax + "\n" +
            "Order TOTAL: " + total + "\n" +
            "+------------------------------------------------+\n";
    return order;
  }
}