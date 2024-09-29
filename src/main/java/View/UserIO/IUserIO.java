package View.UserIO;

import java.math.BigDecimal;

public interface IUserIO {

  public void print(String message);

  public String readString(String prompt);

  public int readInt(String prompt);

  public BigDecimal readBigDecimal(String prompt);
}