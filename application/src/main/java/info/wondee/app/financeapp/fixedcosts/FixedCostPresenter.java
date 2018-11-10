package info.wondee.app.financeapp.fixedcosts;

import javax.validation.constraints.Pattern;

import info.wondee.app.financeapp.DisplayUtil;
import info.wondee.app.financeapp.FinanceMonth;
import info.wondee.app.financeapp.FutureDateString;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@DateRange(message="Das 'Ab-Datum' muss vor dem 'Bis-Datum' liegen")
@NoArgsConstructor
@Getter
@Setter
public abstract class FixedCostPresenter<T extends FixedCost> extends CostPresenter<T> {

  private static final String FUTURE_DATE_MESSAGE = "Das Datum muss in der Zukunft liegen.";

  private static final long serialVersionUID = 1L;

  @Pattern(regexp="[0-9]{2}\\/[0-9]{4}")
  @FutureDateString(message=FUTURE_DATE_MESSAGE)
  private String fromYearMonth;
  
  @Pattern(regexp="[0-9]{2}\\/[0-9]{4}")
  @FutureDateString(message=FUTURE_DATE_MESSAGE)
  private String toYearMonth;

  public FixedCostPresenter(T object) {
    super(object);
    FinanceMonth from = object.getFrom();

    if (from != null) {
      fromYearMonth = DisplayUtil.format(from);
    }

    FinanceMonth to = object.getTo();

    if (to != null) {
      toYearMonth = DisplayUtil.format(to);
    }

  }

  private String getDisplayDate(String date) {
    return (date == null) ? "-" : DisplayUtil.createDisplayMonthAndYear(date);
  }

  public String getDisplayFromDate() {
    return getDisplayDate(fromYearMonth);
  }

  public String getDisplayToDate() {
    return getDisplayDate(toYearMonth);
  }

  public boolean isBounded() {
    return fromYearMonth != null || toYearMonth != null;
  }

}
