package info.wondee.app.financeapp.fixedcosts;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public abstract class Cost implements Serializable {

  private static final long serialVersionUID = 1L;

  protected Integer id;
  
  private String name;
  private int amount;
  
  public abstract CostType getType();
}
