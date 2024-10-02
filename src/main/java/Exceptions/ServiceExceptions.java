package Exceptions;

public class ServiceExceptions extends Exception {
  public ServiceExceptions(ModelExceptions msg) {
    super(msg);
  }
}