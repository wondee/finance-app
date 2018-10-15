package info.wondee.app.financeapp;

import java.text.DateFormatSymbols;
import java.time.Month;
import java.time.YearMonth;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import info.wondee.app.financeapp.fixedcosts.Cost;
import info.wondee.app.financeapp.fixedcosts.CostPresenter;
import info.wondee.app.financeapp.fixedcosts.FixedCostPresenter;

public class DisplayUtil {

  public static String createDisplayMonth(int month) {
    return createDisplayMonth(toMonth(month));
  }
  
  public static String createDisplayMonth(Month month) {
    
    DateFormatSymbols dfs = new DateFormatSymbols();
    return dfs.getMonths()[month.getValue() - 1];
    
  }

  public static String createDisplayMonthAndYear(YearMonth yearMonth) {
    return createDisplayMonth(yearMonth.getMonth()) + " / " + yearMonth.getYear();
  }

  public static Month toMonth(int month) {
    return Month.values()[month - 1];
  }

  public static FinanceMonth toDate(int month, int year) {
    if (year == 0) return null;
    
    return new FinanceMonth(toMonth(month), year);
  }

  public static <T extends Cost> List<CostPresenter<T>> toPresenter(Collection<T> costs) {
    return costs.stream().map(cost -> FixedCostPresenter.from(cost)).collect(Collectors.toList());
  }
  
}
