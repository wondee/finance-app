package info.wondee.app.financeapp.overview;

import java.time.Month;
import java.time.Year;

import info.wondee.app.financeapp.fixedcosts.FixedCost;
import lombok.Getter;

@Getter
public class SpecialCost extends FixedCost {

  private Month month;
  private Year year;
  
  public SpecialCost(String name, int amount) {
    super(name, amount);
    // TODO Auto-generated constructor stub
  }
  
}
