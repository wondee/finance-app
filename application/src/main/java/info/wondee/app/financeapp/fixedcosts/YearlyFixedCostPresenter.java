package info.wondee.app.financeapp.fixedcosts;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import info.wondee.app.financeapp.DisplayUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class YearlyFixedCostPresenter extends FixedCostPresenter<YearlyFixedCost> {

  private static final long serialVersionUID = 1L;
  
  @Min(1)
  @Max(12)
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
          DisplayUtil.parseToDate(getFromYearMonth()), 
          DisplayUtil.parseToDate(getToYearMonth())
        );
  }

  @Override
  public String getType() {
    return "yearly";
  }
  
}
