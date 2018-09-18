package info.wondee.app.financeapp;

import java.text.DateFormatSymbols;
import java.time.Month;
import java.time.YearMonth;

public class DisplayUtil {

  public static String createDisplayMonth(Month month) {
    
    DateFormatSymbols dfs = new DateFormatSymbols();
    return dfs.getMonths()[month.getValue()];
    
  }

  public static String createDisplayMonthAndYear(YearMonth yearMonth) {
    return createDisplayMonth(yearMonth.getMonth()) + " / " + yearMonth.getYear();
  }

  
}
