package info.wondee.app.financeapp.specialcosts;

import org.springframework.data.annotation.PersistenceConstructor;

import com.fasterxml.jackson.annotation.JsonCreator;

import info.wondee.app.financeapp.FinanceMonth;
import info.wondee.app.financeapp.fixedcosts.Cost;
import info.wondee.app.financeapp.fixedcosts.CostType;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class SpecialCost extends Cost implements Comparable<SpecialCost> {

  private static final long serialVersionUID = 1L;
  
  private FinanceMonth dueDate;
  
  @JsonCreator
  @PersistenceConstructor
  public SpecialCost(Integer id, String name, int amount, FinanceMonth dueDate) {
    super(id, name, amount);
    
    this.dueDate = dueDate;
  }

  public SpecialCost(String name, int amount, FinanceMonth dueDate) {
    this(null, name, amount, dueDate);
  }

  @Override
  public int compareTo(SpecialCost o) {
    int result = getDueDate().compareTo(o.getDueDate());
    
    if (result != 0) return result;
    
    return getName().compareTo(o.getName());
  }
  
  @Override
  public CostType getType() {
    return CostType.SPECIAL;
  }

  public void setId(int id) {
    this.id = id;
  }
  
}
