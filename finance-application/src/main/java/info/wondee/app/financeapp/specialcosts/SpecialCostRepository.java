package info.wondee.app.financeapp.specialcosts;

import java.time.Month;
import java.util.List;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
public class SpecialCostRepository {

  private static List<SpecialCost> allCosts = Lists.newArrayList(
          new SpecialCost(1, "Auto", -12000, Month.MARCH, 2019)
        
        );

  public List<SpecialCost> findAllSpecialCosts() {
    return allCosts ;
  }
  
}
