package info.wondee.app.financeapp.fixedcosts;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MonthlyFixedCost extends FixedCost {
	
	public MonthlyFixedCost(String name, int amount) {
		super(name, amount);
	}

}
