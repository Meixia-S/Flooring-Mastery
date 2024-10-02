package Exceptions;

/**
 * Custom exception class to handle service-related exceptions in the application.
 *
 * This class extends the {@link Exception} class and is specifically designed
 * to handle exceptions that occur within the service layer of the application.
 * It takes a {@link ModelExceptions} instance as a parameter, allowing for
 * chaining of exceptions and maintaining context regarding model-related issues.
 *
 * Example usage:
 *
 *     try {
 *         // Some service operation that may throw ModelExceptions
 *     } catch (ModelExceptions e) {
 *         throw new ServiceExceptions(e);
 *     }
 *
 * @see Exception
 * @see ModelExceptions
 */
public class ServiceExceptions extends Exception {
  /**
   * Constructs a new ServiceExceptions with the specified detail message
   * from the given ModelExceptions.
   *
   * @param msg the ModelExceptions instance which is saved for later
   *            retrieval by the {@link #getCause()} method
   */
  public ServiceExceptions(ModelExceptions msg) {
    super(msg);
  }
}