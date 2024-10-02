package Model.DAO;

import java.math.BigDecimal;
import java.util.HashMap;

import Model.Tax;

/**
 * The {@code TaxesDAO} class serves as a data access object for managing
 * tax information in the application.
 *
 * It contains a static {@code HashMap} that acts as a library of state taxes,
 * allowing for the retrieval and addition of tax details, such as state
 * abbreviation, state name, and tax rate. The class is populated by
 * the {@code AuditDAO} class, which initializes the tax library with
 * relevant data.
 */
public class TaxesDAO {
  public static HashMap<String, Tax> taxLibrary = new HashMap<>();

  /**
   * Retrieves a {@code Tax} object from the tax library based on the specified state abbreviation.
   *
   * @param stateAbbreviation the abbreviation of the state for which to retrieve tax information
   * @return the {@code Tax} object corresponding to the specified state abbreviation,
   *         or {@code null} if no tax information exists for that abbreviation
   */
  public static Tax getTax(String stateAbbreviation) {
    return taxLibrary.get(stateAbbreviation);
  }

  /**
   * Adds a new state tax entry to the tax library.
   *
   * @param stateAbbreviation the abbreviation of the state to be added
   * @param stateName        the full name of the state to be added
   * @param taxRate          the tax rate for the state
   */
  public static void addState(String stateAbbreviation, String stateName, BigDecimal taxRate) {
    Tax tax = new Tax(stateAbbreviation, stateName, taxRate);
    taxLibrary.put(stateAbbreviation, tax);
  }
}