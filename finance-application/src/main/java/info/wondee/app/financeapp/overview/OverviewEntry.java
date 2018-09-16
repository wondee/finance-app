package info.wondee.app.financeapp.overview;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import com.google.common.collect.Lists;
import com.google.common.collect.Streams;

import info.wondee.app.financeapp.fixedcosts.FixedCost;
import lombok.Getter;

@Getter
public class OverviewEntry {

  private Month month;
  private int year;
  private int currentAmount;
  
  private List<FixedCost> fixedCosts;
  
  private List<FixedCost> specialCosts;
  
  public OverviewEntry(LocalDate date, int lastAmount, Collection<FixedCost> fixedCosts, Collection<FixedCost> specialCosts) {
    this.month = date.getMonth();
    this.year = date.getYear();
    
    this.fixedCosts = Lists.newArrayList(fixedCosts);
    this.specialCosts = Lists.newArrayList(specialCosts);
    
    currentAmount = calculateCurrentAmount(lastAmount, Streams.concat(fixedCosts.stream(), specialCosts.stream()));
  }

  private int calculateCurrentAmount(int lastAmount, Stream<FixedCost> concat) {
    
    int tmpAmount = lastAmount;
    
    for (FixedCost fixedCost : fixedCosts) {
      tmpAmount += fixedCost.getAmount();
    }
    
    return tmpAmount;
  }

  public String getDisplayMonth() {
    return month.getValue() + " / " + year;
  }
  
  public String getFixedAmount() {
    return FixedCost.sumList(fixedCosts);
  }
  
  public String getSpecialAmount() {
    return FixedCost.sumList(specialCosts);
  }
  
  public String getDisplayCurrentAmount() {
    return FixedCost.displayAmount(currentAmount);
  }

}
