package info.wondee.app.financeapp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Message {

  public static enum Severity {
    INFO, WARN, ERROR;
  }

  private Severity severity;
  
  private String text;
  
}
