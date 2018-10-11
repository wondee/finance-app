package info.wondee.app.financeapp.fixedcosts;

import info.wondee.app.financeapp.DisplayUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class YearlyFixedCostPresenter extends FixedCostPresenter<YearlyFixedCost> {

  private int dueMonth;

  public YearlyFixedCostPresenter(YearlyFixedCost object) {
    super(object);
    
    dueMonth = object.getMonth().getValue();
  }
  

  public String getDisplayMonth() {
    return DisplayUtil.createDisplayMonth(dueMonth);
  }
  
  @Override
  public YearlyFixedCost toPersistentObject() {
    return new YearlyFixedCost(getName(), getRealAmount(), DisplayUtil.toMonth(dueMonth), 
          DisplayUtil.toDate(getFromMonth(), getFromYear()), 
          DisplayUtil.toDate(getToMonth(), getToYear())
        );
  }
  
}
