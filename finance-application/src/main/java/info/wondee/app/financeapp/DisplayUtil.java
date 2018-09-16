package info.wondee.app.financeapp;

import java.text.DateFormatSymbols;
import java.time.Month;

public class DisplayUtil {

  public static String createDisplayMonth(Month month) {
    
    DateFormatSymbols dfs = new DateFormatSymbols();
    return dfs.getMonths()[month.getValue()];
    
  }

}
