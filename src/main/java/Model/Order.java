package Model;

import java.math.BigDecimal;

import Model.DAO.ProductsDAO;
import Model.DAO.TaxesDAO;

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

  public Order(int orderNumber, String customerName, String state, String productType, BigDecimal area) {
    this.orderNumber = orderNumber;
    this.customerName = customerName;
    this.state = state;
    this.productType = productType;
    this.area = area;
    this.costPerSquareFoot = ProductsDAO.getProduct(this.productType).getCostPerSquareFoot();
    this.laborCostPerSquareFoot = ProductsDAO.getProduct(this.productType).getCostPerSquareFoot();
    this.materialCost = this.area.multiply(this.costPerSquareFoot);
    this.laborCost = this.area.multiply(this.laborCostPerSquareFoot);
    this.taxRate = TaxesDAO.getTax(this.state).getTaxRate();
    this.tax = (this.materialCost.add(this.laborCost)).multiply(this.taxRate);
    this.total = (this.materialCost.add(this.laborCost)).add(this.tax);
  }

  // Getter and Setter for orderNumber
  public int getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(int orderNumber) {
    this.orderNumber = orderNumber;
  }

  // Getter and Setter for customerName
  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  // Getter and Setter for state
  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  // Getter and Setter for taxRate
  public BigDecimal getTaxRate() {
    return taxRate;
  }

  public void setTaxRate(BigDecimal taxRate) {
    this.taxRate = taxRate;
  }

  // Getter and Setter for productType
  public String getProductType() {
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  // Getter and Setter for area
  public BigDecimal getArea() {
    return area;
  }

  public void setArea(BigDecimal area) {
    this.area = area;
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

  // Getter and Setter for materialCost
  public BigDecimal getMaterialCost() {
    return materialCost;
  }

  public void setMaterialCost(BigDecimal materialCost) {
    this.materialCost = materialCost;
  }

  // Getter and Setter for laborCost
  public BigDecimal getLaborCost() {
    return laborCost;
  }

  public void setLaborCost(BigDecimal laborCost) {
    this.laborCost = laborCost;
  }

  // Getter and Setter for tax
  public BigDecimal getTax() {
    return tax;
  }

  public void setTax(BigDecimal tax) {
    this.tax = tax;
  }

  // Getter and Setter for total
  public BigDecimal getTotal() {
    return total;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }
}