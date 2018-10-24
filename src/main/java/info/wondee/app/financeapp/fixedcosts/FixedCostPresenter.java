package info.wondee.app.financeapp.fixedcosts;

import java.time.YearMonth;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import info.wondee.app.financeapp.DisplayUtil;
import info.wondee.app.financeapp.FinanceMonth;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@DateRange
@NoArgsConstructor
@Getter
@Setter
public abstract class FixedCostPresenter<T extends FixedCost> extends CostPresenter<T> {

  @Min(1)
  @Max(12)
  private int fromMonth;
  
  @Min(2000)
  @Max(2199)
  private Integer fromYear;
  
  @Min(1)
  @Max(12)
  private int toMonth;
  
  @Min(2000)
  @Max(2199)
  private Integer toYear;
 
  public FixedCostPresenter(T object) {
    super(object);
    FinanceMonth from = object.getFrom();
    
    if (from != null) {
      fromMonth = from.getMonth().getValue();
      fromYear = from.getYear();
    }
    
    FinanceMonth to = object.getTo();
    
    if (to != null) {
      toMonth = to.getMonth().getValue();
      toYear = to.getYear();
    }
   
  }
  

  private String getDisplayDate(Integer year, int month) {
    return (year == null) ? "-" : DisplayUtil.createDisplayMonthAndYear(YearMonth.of(year, month));
  }

  public String getDisplayFromDate() {
    return getDisplayDate(fromYear, fromMonth);
  }
  
  public String getDisplayToDate() {
    return getDisplayDate(toYear, toMonth);
  }
  
}
