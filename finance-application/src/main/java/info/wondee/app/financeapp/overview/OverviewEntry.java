package info.wondee.app.financeapp.overview;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import info.wondee.app.financeapp.DisplayUtil;
import info.wondee.app.financeapp.fixedcosts.Cost;
import info.wondee.app.financeapp.fixedcosts.CostPresenter;
import info.wondee.app.financeapp.fixedcosts.FixedCost;
import info.wondee.app.financeapp.fixedcosts.MonthlyFixedCostPresenter;
import lombok.Getter;

@Getter
public class OverviewEntry {

  private YearMonth yearMonth;
  private int currentAmount;
  
  private List<CostPresenter<FixedCost>> fixedCosts;
  
  private int sumFixedCosts;
  
  private List<CostPresenter<Cost>> specialCosts;
  
  private int sumSpecialCosts;
  private List<CostPresenter<? extends Cost>> nonMonthlyCosts;


  public OverviewEntry(YearMonth yearMonth, int currentAmount, List<CostPresenter<FixedCost>> fixedCosts,
      int sumFixedCosts, List<CostPresenter<Cost>> specialCosts, int sumSpecialCosts) {
    this.yearMonth = yearMonth;
    this.currentAmount = currentAmount;
    this.fixedCosts = fixedCosts;
    this.sumFixedCosts = sumFixedCosts;
    this.specialCosts = specialCosts;
    this.sumSpecialCosts = sumSpecialCosts;
    
    nonMonthlyCosts = Lists.newArrayList(Iterables.concat(
        fixedCosts.stream().filter(c -> isMonthly(c)).collect(Collectors.toList()),
        specialCosts));
  }
  

  private boolean isMonthly(@SuppressWarnings("rawtypes") CostPresenter c) {
    return !(c instanceof MonthlyFixedCostPresenter);
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

  public String getDisplayNumberOfNonMonthCosts() {
    int number = nonMonthlyCosts.size();
    
    return (number < 1) ? "-" : String.valueOf(number);
  }
  
  public boolean isNegative() {
    return currentAmount < 0;
  }
}
