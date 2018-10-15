package info.wondee.app.financeapp.overview;

import java.time.YearMonth;
import java.util.List;

import info.wondee.app.financeapp.DisplayUtil;
import info.wondee.app.financeapp.fixedcosts.Cost;
import info.wondee.app.financeapp.fixedcosts.CostPresenter;
import info.wondee.app.financeapp.fixedcosts.FixedCost;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OverviewEntry {

  private YearMonth yearMonth;
  private int currentAmount;
  
  private List<CostPresenter<FixedCost>> fixedCosts;
  
  private int sumFixedCosts;
  
  private List<CostPresenter<Cost>> specialCosts;
  
  private int sumSpecialCosts;


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

  public boolean isNegative() {
    return currentAmount < 0;
  }
}
