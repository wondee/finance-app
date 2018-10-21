package info.wondee.app.financeapp.fixedcosts;

import java.util.stream.Collectors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import info.wondee.app.financeapp.DisplayUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class QuaterlyFixedCostPresenter extends FixedCostPresenter<QuaterlyFixedCost> {

  @Min(0)
  @Max(2)
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

  @Override
  public String getType() {
    return "quaterly";
  }

}
