package info.wondee.app.financeapp.fixedcosts;

import java.time.YearMonth;

import info.wondee.app.financeapp.DisplayUtil;
import info.wondee.app.financeapp.FinanceMonth;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public abstract class FixedCostPresenter<T extends FixedCost> extends CostPresenter<T> {

  private int fromMonth;
  private int fromYear;
  
  private int toMonth;
  private int toYear;
 
  public FixedCostPresenter(T object) {
    super(object);
    FinanceMonth from = object.getFrom();
    
    if (from != null) {
      fromMonth = from.getMonth().getValue();
      fromYear = from.getYear();
    }
    
    FinanceMonth to = object.getTo();
    
    if (to != null) {
      toMonth = to.getMonth().getValue();
      toYear = to.getYear();
    }
   
  }
  

  private String getDisplayDate(int year, int month) {
    return (year == 0) ? "-" : DisplayUtil.createDisplayMonthAndYear(YearMonth.of(year, month));
  }

  public String getDisplayFromDate() {
    return getDisplayDate(fromYear, fromMonth);
  }
  
  public String getDisplayToDate() {
    return getDisplayDate(toYear, toMonth);
  }
  
}
