package info.wondee.app.financeapp.specialcosts;

import java.time.YearMonth;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import info.wondee.app.financeapp.DisplayUtil;
import info.wondee.app.financeapp.FutureDateString;
import info.wondee.app.financeapp.fixedcosts.CostPresenter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SpecialCostPresenter extends CostPresenter<SpecialCost> {

  private static final long serialVersionUID = 1L;

  @NotNull
  @Pattern(regexp="[0-9]{2}\\/[0-9]{4}")
  @FutureDateString(message="Das Datum muss in der Zukunft liegen.")
  private String dueYearMonth;
  
  public SpecialCostPresenter(SpecialCost object) {
    super(object);
    
    dueYearMonth = DisplayUtil.format(object.getDueDate());
  }
  
  public String getDisplayDueDate() {
    return DisplayUtil.createDisplayMonthAndYear(DisplayUtil.parse(dueYearMonth));
  }
  
  public void setDueYearMonth(int month, int year) {
    dueYearMonth = DisplayUtil.format(YearMonth.of(year, month));
  }
  
  @Override
  public SpecialCost toPersistentObject() {
    return new SpecialCost(getName(), getRealAmount(), DisplayUtil.parseToDate(dueYearMonth));
  }

  @Override
  public String getType() {
    return "special";
  }
  
}
