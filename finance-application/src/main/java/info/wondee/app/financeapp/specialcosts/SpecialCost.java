package info.wondee.app.financeapp.specialcosts;

import java.time.Month;
import java.time.YearMonth;

import info.wondee.app.financeapp.DisplayUtil;
import info.wondee.app.financeapp.fixedcosts.Cost;
import lombok.Getter;

@Getter
public class SpecialCost extends Cost {

  private YearMonth dueDate;
  
  public SpecialCost(Integer id, String name, int amount, Month dueMonth, int dueYear) {
    super(id, name, amount);
    
    dueDate = YearMonth.of(dueYear, dueMonth);
  }

  public String getDisplayDueDate() {
    return DisplayUtil.createDisplayMonthAndYear(dueDate);
  }
  
}
