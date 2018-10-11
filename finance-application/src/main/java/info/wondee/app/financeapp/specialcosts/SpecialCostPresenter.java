package info.wondee.app.financeapp.specialcosts;

import java.time.YearMonth;

import info.wondee.app.financeapp.DisplayUtil;
import info.wondee.app.financeapp.fixedcosts.CostPresenter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SpecialCostPresenter extends CostPresenter<SpecialCost> {

  private int dueMonth;
  
  private int dueYear;

  public SpecialCostPresenter(SpecialCost object) {
    super(object);
    dueMonth = object.getDueDate().getMonth().getValue();
    dueYear = object.getDueDate().getYear();
  }
  
  public String getDisplayDueDate() {
    return DisplayUtil.createDisplayMonthAndYear(YearMonth.of(dueYear, dueMonth));
  }
  
  @Override
  public SpecialCost toPersistentObject() {
    return new SpecialCost(getName(), getRealAmount(), DisplayUtil.toDate(dueMonth, dueYear));
  }
  
}
