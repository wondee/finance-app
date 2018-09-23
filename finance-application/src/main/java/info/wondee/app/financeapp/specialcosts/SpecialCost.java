package info.wondee.app.financeapp.specialcosts;

import java.time.Month;
import java.time.YearMonth;

import info.wondee.app.financeapp.DisplayUtil;
import info.wondee.app.financeapp.fixedcosts.Cost;
import lombok.Getter;

@Getter
public class SpecialCost extends Cost implements Comparable<SpecialCost> {

  private int dueYear;
  private Month dueMonth;
  
  public SpecialCost(Integer id, String name, int amount, Month dueMonth, int dueYear) {
    super(id, name, amount);
    
    this.dueYear = dueYear;
    this.dueMonth = dueMonth;
  }

  public String getDisplayDueDate() {
    return DisplayUtil.createDisplayMonthAndYear(getDueDate());
  }

  public YearMonth getDueDate() {
    return YearMonth.of(dueYear, dueMonth);
  }
  
  @Override
  public int compareTo(SpecialCost o) {
    int result = getDueDate().compareTo(o.getDueDate());
    
    if (result != 0) return result;
    
    return getName().compareTo(o.getName());
  }
  
}
