package info.wondee.app.financeapp.overview;

import java.time.YearMonth;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import info.wondee.app.financeapp.DisplayUtil;
import info.wondee.app.financeapp.fixedcosts.Cost;
import info.wondee.app.financeapp.fixedcosts.CostPresenter;
import info.wondee.app.financeapp.fixedcosts.FixedCost;
import lombok.Getter;

@Getter
public class OverviewEntry {

  private static final Comparator<CostPresenter<? extends Cost>> amountComparator 
        = ((o1, o2) -> Integer.compare(o2.getAmount(), o1.getAmount()));

  private YearMonth yearMonth;
  private int currentAmount;
  
  private List<CostPresenter<FixedCost>> fixedCosts;
  
  private int sumFixedCosts;
  
  private List<CostPresenter<Cost>> specialCosts;
  
  private int sumSpecialCosts;


  public OverviewEntry(YearMonth yearMonth, int currentAmount, List<CostPresenter<FixedCost>> fixedCosts,
      int sumFixedCosts, List<CostPresenter<Cost>> specialCosts, int sumSpecialCosts) {
    this.yearMonth = yearMonth;
    this.currentAmount = currentAmount;
    this.fixedCosts = fixedCosts;
    this.sumFixedCosts = sumFixedCosts;
    this.specialCosts = specialCosts;
    this.sumSpecialCosts = sumSpecialCosts;
    
    Collections.sort(fixedCosts, amountComparator);
    Collections.sort(specialCosts, amountComparator);
    
  }


  public String getDisplayMonth() {
    return DisplayUtil.createDisplayMonthAndYear(yearMonth);
  }
  
  public String getDisplayFixedAmount() {
    return CostPresenter.displayAmount(sumFixedCosts);
  }
  
  public String getDisplaySpecialAmount() {
    return CostPresenter.displayAmount(sumSpecialCosts);
  }
  
  public String getDisplayCurrentAmount() {
    return CostPresenter.displayAmount(currentAmount);
  }
  
  public int getCountOfSpecialcosts() {
    return specialCosts.size();
  }
  
  public int getCountOfFixedcosts() {
    return fixedCosts.size();
  }
  
  public boolean isNegative() {
    return currentAmount < 0;
  }
}
