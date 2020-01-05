package info.wondee.app.financeapp.fixedcosts;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.wondee.app.financeapp.DisplayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@ToString
public abstract class Cost implements Serializable {

  private static final long serialVersionUID = 1L;

  protected Integer id;
  
  private String name;
  private int amount;

  public Cost(Cost cost) {
    this(cost.id, cost.name, cost.amount);
  }

  public abstract CostType getType();

  @JsonProperty
  public String getDisplayType() {
    return DisplayUtil.toDisplayString(getType());
  }
}
