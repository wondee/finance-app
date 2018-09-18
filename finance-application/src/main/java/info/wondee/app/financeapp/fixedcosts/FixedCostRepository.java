package info.wondee.app.financeapp.fixedcosts;

import java.time.Month;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
public class FixedCostRepository {

  private static List<Cost> allCosts = Lists.newArrayList(
              new MonthlyFixedCost(1, "Gehalt Markus", 2000),
              new MonthlyFixedCost(2, "Lebenmittel", -400),
              
              new YearlyFixedCost(3, "RMV Jahreskarte", -1000, Month.SEPTEMBER),
              new YearlyFixedCost(4, "Auto Versicherung", -600, Month.MARCH)
          );

  public List<Cost> findAllFixedCosts() {
    return allCosts;
  }

  public void add(Cost newFixedCost) {
    
    Optional<Integer> max = allCosts.stream().map(cost -> cost.getId()).max(Integer::compare);
    
    newFixedCost.setId(max.get() + 1);
    
    allCosts.add(newFixedCost);
  }

  public void delete(int id) {
    allCosts.removeIf(cost -> cost.getId() == id);
  }
	
}
