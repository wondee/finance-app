package info.wondee.app.financeapp.fixedcosts;

import java.time.Month;
import java.util.Objects;

import org.springframework.data.annotation.PersistenceConstructor;

import com.google.common.base.Preconditions;

import info.wondee.app.financeapp.FinanceMonth;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class YearlyFixedCost extends FixedCost implements Comparable<YearlyFixedCost> {

  private static final long serialVersionUID = 1L;
  
  private Month month;

  @PersistenceConstructor
  public YearlyFixedCost(Integer id, String name, int amount, Month month,  FinanceMonth from, FinanceMonth to) {
    super(id, name, amount, from, to);
    Preconditions.checkNotNull(month);
    this.month = month;
  }
  
  public YearlyFixedCost(String name, int amount, Month month,  FinanceMonth from, FinanceMonth to) {
    this(null, name, amount, month, from, to);
  }

  @Override
  public boolean appliesAt(Month month) {
    return Objects.equals(this.month, month);
  }

  @Override
  public int compareTo(YearlyFixedCost o) {
    int result = month.compareTo(o.month);
    
    if (result != 0) return result;
    
    return getName().compareTo(o.getName());
  }
  
  @Override
  public CostType getType() {
    return CostType.YEARLY;
  }
}
