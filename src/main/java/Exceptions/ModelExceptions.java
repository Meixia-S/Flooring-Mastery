package Exceptions;

/**
 * Custom exception class to handle model-related exceptions in the application.
 *
 * This class extends the {@link Exception} class to provide specific exception
 * handling for model-related issues. It allows for a message to be passed when
 * the exception is thrown, which can help in diagnosing the issue.
 *
 * Example usage:
 *
 *     try {
 *       // some code operation
 *     } catch (error e) {
 *       throw new ModelExceptions("custom error msg here");
 *     }
 *
 * @see Exception
 */
public class ModelExceptions extends Exception {
  /**
   * Constructs a new ModelExceptions with the specified detail message.
   *
   * @param msg the detail message which is saved for later retrieval by the
   *            {@link #getMessage()} method
   */
  public ModelExceptions(String msg) {
    super(msg);
  }
}