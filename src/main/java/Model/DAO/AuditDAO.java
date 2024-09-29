package Model.DAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

import Model.Order;

public class AuditDAO implements IAuditDAO {
  private String fileTitle;
  private Scanner reader = new Scanner(new BufferedReader(new FileReader(fileTitle)));
  private PrintWriter writer = new PrintWriter(new FileWriter(fileTitle));

  public AuditDAO() throws IOException {
    getProductInfo("Products.txt");
    getTaxInfo("Taxes.txt");
  }

  @Override
  public void addOrder(LocalDate date, Order order) {
  }

  @Override
  public void editAnOrder(LocalDate date, Order order, String edits) {
  }

  @Override
  public void removeOrder(LocalDate date, int orderNum) {
  }

  @Override
  public void export() {
  }

  @Override
  public void getTaxInfo(String taxFileName) {
  }

  @Override
  public void getProductInfo(String productFileName) {
  }

  public void setFileName(String name) {
    this.fileTitle = name;
  }

  private void localDateToDate(LocalDate date) {
    this.fileTitle = "";
  }
}