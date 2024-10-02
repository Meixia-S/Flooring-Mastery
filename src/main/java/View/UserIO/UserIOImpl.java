package View.UserIO;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The {@code UserIO} class provides methods for user input and output.
 * It implements the {@code IUserIO} interface and utilizes a {@code Scanner}
 * to facilitate reading various types of input from the user.
 */
@Component
public class UserIOImpl implements UserIO {
  private Scanner userInput = new Scanner(System.in);

  /**
   * Prints a message to the console.
   *
   * @param message the message to be printed
   */
  @Override
  public void print(String message) {
    System.out.println(message);
  }

  /**
   * Prompts the user for a string input.
   *
   * @param prompt the prompt message to be displayed to the user
   * @return the string input entered by the user
   */
  @Override
  public String readString(String prompt) {
    System.out.println(prompt);
    return userInput.nextLine();
  }

  /**
   * Prompts the user for an integer input.
   *
   * @param prompt the prompt message to be displayed to the user
   * @return the integer input entered by the user
   * @throws InputMismatchException if the input is not an integer
   */
  @Override
  public int readInt(String prompt) {
    System.out.println(prompt);
    return userInput.nextInt();
  }

  /**
   * Prompts the user for a BigDecimal input.
   *
   * @param prompt the prompt message to be displayed to the user
   * @return the BigDecimal input entered by the user
   */
  @Override
  public BigDecimal readBigDecimal(String prompt) {
    System.out.println(prompt);
    return userInput.nextBigDecimal();
  }

  /**
   * Clears the scanner buffer to prevent input mismatch errors.
   * This method consumes the newline character remaining in the buffer.
   */
  @Override
  public void clearScannerBuffer() {
    userInput.nextLine();
  }
}