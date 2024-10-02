package View.UserIO;

import java.math.BigDecimal;
import java.util.InputMismatchException;

/**
 * The {@code IUserIO} interface defines a contract for user input and output operations.
 * It provides methods for printing messages to the console and reading various types of user inputs,
 * including strings, integers, and {@code BigDecimal} values. Implementations of this interface
 * should facilitate user interaction in a consistent manner, ensuring that input is gathered
 * and output is displayed effectively.
 */
public interface UserIO {
  /**
   * Prints a message to the console.
   *
   * @param message the message to be printed
   */
  public void print(String message);

  /**
   * Prompts the user for a string input.
   *
   * @param prompt the prompt message to be displayed to the user
   * @return the string input entered by the user
   */
  public String readString(String prompt);

  /**
   * Prompts the user for an integer input.
   *
   * @param prompt the prompt message to be displayed to the user
   * @return the integer input entered by the user
   * @throws InputMismatchException if the input is not an integer
   */
  public int readInt(String prompt);

  /**
   * Prompts the user for a BigDecimal input.
   *
   * @param prompt the prompt message to be displayed to the user
   * @return the BigDecimal input entered by the user
   */
  public BigDecimal readBigDecimal(String prompt);

  /**
   * Clears the scanner buffer to prevent input mismatch errors.
   * This method consumes the newline character remaining in the buffer.
   */
  public void clearScannerBuffer();
}