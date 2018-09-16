package info.wondee.app.financeapp.overview;

import java.time.Month;
import java.time.Year;

import info.wondee.app.financeapp.fixedcosts.FixedCost;
import lombok.Getter;

@Getter
public class SpecialCost extends FixedCost {

  private Month month;
  private Year year;
  
  public SpecialCost(Integer id, String name, int amount) {
    super(id, name, amount);
  }
  
}
