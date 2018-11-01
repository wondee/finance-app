package info.wondee.app.financeapp.fixedcosts;

import java.time.Month;

import org.springframework.data.annotation.PersistenceConstructor;

import info.wondee.app.financeapp.FinanceMonth;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MonthlyFixedCost extends FixedCost implements Comparable<MonthlyFixedCost> {

  @PersistenceConstructor
  public MonthlyFixedCost(Integer id, String name, int amount, FinanceMonth from, FinanceMonth to) {
    super(id, name, amount, from, to);
  }

  public MonthlyFixedCost(String name, int amount, FinanceMonth from, FinanceMonth to) {
    this(null, name, amount, from, to);
  }

  @Override
  public int compareTo(MonthlyFixedCost o) {
    return getName().compareTo(o.getName());
  }
  
  @Override
  public CostType getType() {
    return CostType.MONTHLY;
  }

  @Override
  public boolean appliesAt(Month month) {
    return true;
  }

}
