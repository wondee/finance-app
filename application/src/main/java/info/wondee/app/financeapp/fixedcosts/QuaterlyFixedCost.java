package info.wondee.app.financeapp.fixedcosts;

import java.time.Month;
import java.util.stream.IntStream;

import org.springframework.data.annotation.PersistenceConstructor;

import com.google.common.base.Preconditions;

import info.wondee.app.financeapp.DisplayUtil;
import info.wondee.app.financeapp.FinanceMonth;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class QuaterlyFixedCost extends FixedCost implements Comparable<QuaterlyFixedCost> {
  
  private static final long serialVersionUID = 1L;
  
  private int dueMonth;
  
  @PersistenceConstructor
  public QuaterlyFixedCost(Integer id, String name, int amount, int dueMonth, FinanceMonth from, FinanceMonth to) {
    super(id, name, amount, from, to);
    
    Preconditions.checkArgument(dueMonth < 3 && dueMonth > -1, "dueMonth must be between 0 and 2");
    this.dueMonth = dueMonth;
  }

  public QuaterlyFixedCost(String name, int amount, int dueMonth, FinanceMonth from, FinanceMonth to) {
    this(null, name, amount, dueMonth, from, to);
  }

  @Override
  public CostType getType() {
    return CostType.QUATERLY;
  }

  @Override
  public int compareTo(QuaterlyFixedCost o) {
    int result = Integer.compare(dueMonth, o.dueMonth);
    
    if (result != 0) return result;
    
    return getName().compareTo(o.getName());
  }

  @Override
  public boolean appliesAt(Month month) {
    
    return getQuaterlyMonths(dueMonth)
        .mapToObj(DisplayUtil::toMonth)
        .filter(month::equals)
        .count() != 0;
  }
  
  public static IntStream getQuaterlyMonths(int dueMonth) {
    return IntStream.of(1, 4, 7, 10)
        .map(i -> i + dueMonth);
  }
  
  
}
