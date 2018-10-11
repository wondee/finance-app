package info.wondee.app.financeapp.fixedcosts;

import info.wondee.app.financeapp.DisplayUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MonthlyFixedCostPresenter extends FixedCostPresenter<MonthlyFixedCost> {
  
  public MonthlyFixedCostPresenter(MonthlyFixedCost object) {
    super(object);
    
  }
  
  @Override
  public MonthlyFixedCost toPersistentObject() {
    return new MonthlyFixedCost(getName(), getRealAmount(), 
          DisplayUtil.toDate(getFromMonth(), getFromYear()), 
          DisplayUtil.toDate(getToMonth(), getToYear())
        );
  }
  
  
}
