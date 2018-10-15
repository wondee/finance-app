package info.wondee.app.financeapp.fixedcosts;

import java.util.stream.Collectors;

import info.wondee.app.financeapp.DisplayUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class QuaterlyFixedCostPresenter extends FixedCostPresenter<QuaterlyFixedCost> {

  private int dueMonth;
  
  public QuaterlyFixedCostPresenter(QuaterlyFixedCost object) {
    super(object);
  }
  
  public String getDisplayDueMonths() {
    return QuaterlyFixedCost.getQuaterlyMonths(dueMonth)
      .mapToObj(month -> DisplayUtil.createDisplayMonth(month))
      .collect(Collectors.joining(", "));
    
  }
  
  @Override
  public QuaterlyFixedCost toPersistentObject() {
    
    return new QuaterlyFixedCost(getName(), getRealAmount(), dueMonth,  
        DisplayUtil.toDate(getFromMonth(), getFromYear()), 
        DisplayUtil.toDate(getToMonth(), getToYear()));
  }

}
