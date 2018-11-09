package info.wondee.app.financeapp;

public class BusinessException extends RuntimeException {


  private static final long serialVersionUID = 1L;

  public BusinessException(String msg, String ... params) {
    super(String.format(msg, (Object[]) params));
  }

}
