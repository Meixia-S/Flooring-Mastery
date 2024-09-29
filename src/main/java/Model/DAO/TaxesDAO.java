package Model.DAO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import Model.Tax;

public class TaxesDAO {
  public static HashMap<String, Tax> taxLibrary = new HashMap<>();

  public static Tax getTax(String stateAbbreviation) {
    return taxLibrary.get(stateAbbreviation);
  }

  public void addProduct(String stateAbbreviation, String stateName, BigDecimal taxRate) {
  }

  public void genTaxesFromFile(String fileNames) {

  }
}