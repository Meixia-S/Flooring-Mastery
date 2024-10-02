package Model;

import java.math.BigDecimal;

/**
 * The {@code Tax} class represents tax information associated with a
 * specific state. It includes details such as the state abbreviation,
 * state name, and the applicable tax rate.
 */
public class Tax {
  private String stateAbbreviation;
  private String stateName;
  private BigDecimal taxRate;

  /**
   * Constructs a new {@code Tax} object with the specified state
   * abbreviation, state name, and tax rate.
   *
   * @param stateAbbreviation the abbreviation of the state
   * @param stateName        the name of the state
   * @param taxRate          the tax rate for the state
   */
  public Tax(String stateAbbreviation, String stateName, BigDecimal taxRate) {
    this.stateAbbreviation = stateAbbreviation;
    this.stateName = stateName;
    this.taxRate = taxRate;
  }

  // Getter and Setter for stateAbbreviation

  /**
   * Returns the abbreviation of the state.
   *
   * @return the state abbreviation
   */
  public String getStateAbbreviation() {
    return stateAbbreviation;
  }

  /**
   * Sets the abbreviation of the state.
   *
   * @param stateAbbreviation the new state abbreviation
   */
  public void setStateAbbreviation(String stateAbbreviation) {
    this.stateAbbreviation = stateAbbreviation;
  }

  // Getter and Setter for stateName

  /**
   * Returns the name of the state.
   *
   * @return the state name
   */
  public String getStateName() {
    return stateName;
  }

  /**
   * Sets the name of the state.
   *
   * @param stateName the new state name
   */
  public void setStateName(String stateName) {
    this.stateName = stateName;
  }

  // Getter and Setter for taxRate

  /**
   * Returns the tax rate for the state.
   *
   * @return the tax rate
   */
  public BigDecimal getTaxRate() {
    return taxRate;
  }

  /**
   * Sets the tax rate for the state.
   *
   * @param taxRate the new tax rate
   */
  public void setTaxRate(BigDecimal taxRate) {
    this.taxRate = taxRate;
  }
}