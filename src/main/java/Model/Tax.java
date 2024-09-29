package Model;

import java.math.BigDecimal;

public class Tax {
  private String stateAbbreviation;
  private String stateName;
  private BigDecimal taxRate;

  public Tax(String stateAbbreviation, String stateName, BigDecimal taxRate) {
    this.stateAbbreviation = stateAbbreviation;
    this.stateName = stateName;
    this.taxRate = taxRate;
  }

  // Getter and Setter for stateAbbreviation
  public String getStateAbbreviation() {
    return stateAbbreviation;
  }

  public void setStateAbbreviation(String stateAbbreviation) {
    this.stateAbbreviation = stateAbbreviation;
  }

  // Getter and Setter for stateName
  public String getStateName() {
    return stateName;
  }

  public void setStateName(String stateName) {
    this.stateName = stateName;
  }

  // Getter and Setter for taxRate
  public BigDecimal getTaxRate() {
    return taxRate;
  }

  public void setTaxRate(BigDecimal taxRate) {
    this.taxRate = taxRate;
  }
}