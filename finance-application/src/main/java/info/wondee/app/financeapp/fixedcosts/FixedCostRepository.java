package info.wondee.app.financeapp.fixedcosts;

import java.time.Month;
import java.util.List;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
public class FixedCostRepository {

	public List<FixedCost> findAllFixedCosts() {
		return Lists.newArrayList(
					new MonthlyFixedCost("Gehalt Markus", 2000),
					new MonthlyFixedCost("Lebenmittel", -400),
					new YearlyFixedCost("RMV Jahreskarte", -1000, Month.SEPTEMBER),
					new YearlyFixedCost("Auto Versicherung", -600, Month.MARCH)
				);
	}
	
}
