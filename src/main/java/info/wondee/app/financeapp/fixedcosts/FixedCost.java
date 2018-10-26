package info.wondee.app.financeapp.fixedcosts;

import java.time.Month;
import java.time.YearMonth;

import com.google.common.base.Preconditions;

import info.wondee.app.financeapp.FinanceMonth;
import lombok.Getter;

@Getter
public abstract class FixedCost extends Cost {

  private FinanceMonth from;
  private FinanceMonth to;


  public FixedCost(Integer id, String name, int amount, FinanceMonth from, FinanceMonth to) {
    super(id, name, amount);

    this.from = from;
    this.to = to;

    Preconditions.checkArgument(getFromDateAsDate().isBefore(getToDateAsDate()),
        "from date must be before to date! (from: %s, to: %s)", from, to);
  }

  public boolean isActive(YearMonth month) {
    return month.isAfter(getFromDateAsDate().minusMonths(1)) &&
        month.isBefore(getToDateAsDate().plusMonths(1));
  }

  private YearMonth getFromDateAsDate() {
    return getAsDate(from, YearMonth.of(0, Month.JANUARY));
  }

  private YearMonth getToDateAsDate() {
    return getAsDate(to, YearMonth.of(9999, Month.DECEMBER));
  }

  private YearMonth getAsDate(FinanceMonth month, YearMonth defaultIfNull) {
    return (month != null) ? month.toDate() : defaultIfNull;
  }

  public abstract boolean appliesAt(Month month);

}
