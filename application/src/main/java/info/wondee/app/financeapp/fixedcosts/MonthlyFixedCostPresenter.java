package info.wondee.app.financeapp.fixedcosts;

import info.wondee.app.financeapp.DisplayUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MonthlyFixedCostPresenter extends FixedCostPresenter<MonthlyFixedCost> {
  
  private static final long serialVersionUID = 1L;
  
  public MonthlyFixedCostPresenter(MonthlyFixedCost object) {
    super(object);
    
  }
  
  @Override
  public String getType() {
    return "monthly";
  }
  
  @Override
  public MonthlyFixedCost toPersistentObject() {
    return new MonthlyFixedCost(getName(), getRealAmount(), 
          DisplayUtil.parseToDate(getFromYearMonth()), 
          DisplayUtil.parseToDate(getToYearMonth())
        );
  }
  
  
}
