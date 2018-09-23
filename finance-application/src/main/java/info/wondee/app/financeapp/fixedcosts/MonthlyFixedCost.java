package info.wondee.app.financeapp.fixedcosts;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MonthlyFixedCost extends Cost implements Comparable<MonthlyFixedCost> {
	
	public MonthlyFixedCost(Integer id, String name, int amount) {
		super(id, name, amount);
	}

  @Override
  public int compareTo(MonthlyFixedCost o) {
    return getName().compareTo(o.getName());
  }

}
