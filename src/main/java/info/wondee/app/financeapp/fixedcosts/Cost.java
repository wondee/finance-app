package info.wondee.app.financeapp.fixedcosts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public abstract class Cost {

  protected Integer id;
  
  private String name;
  private int amount;
  
  public abstract CostType getType();
}
