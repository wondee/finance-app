package info.wondee.app.financeapp.overview;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;

import info.wondee.app.financeapp.DisplayUtil;
import info.wondee.app.financeapp.fixedcosts.Cost;
import lombok.Getter;

@Getter
public class OverviewEntry {

  private YearMonth yearMonth;
  private int currentAmount;
  
  private List<Cost> fixedCosts;
  
  private List<Cost> specialCosts;
  
  public OverviewEntry(LocalDate date, int lastAmount, Collection<Cost> fixedCosts, Collection<Cost> specialCosts) {
    yearMonth = YearMonth.from(date);    
    
    this.fixedCosts = Lists.newArrayList(fixedCosts);
    this.specialCosts = Lists.newArrayList(specialCosts);
    
    currentAmount = lastAmount + Cost.sumList(fixedCosts) + Cost.sumList(specialCosts);
  }

  public String getDisplayMonth() {
    return DisplayUtil.createDisplayMonthAndYear(yearMonth);
  }
  
  public String getFixedAmount() {
    return Cost.sumListDisplay(fixedCosts);
  }
  
  public String getSpecialAmount() {
    return Cost.sumListDisplay(specialCosts);
  }
  
  public String getDisplayCurrentAmount() {
    return Cost.displayAmount(currentAmount);
  }

}
