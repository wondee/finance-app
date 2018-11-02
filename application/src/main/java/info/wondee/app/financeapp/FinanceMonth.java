package info.wondee.app.financeapp;

import java.io.Serializable;
import java.time.Month;
import java.time.YearMonth;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class FinanceMonth implements Comparable<FinanceMonth>, Serializable {

  private static final long serialVersionUID = 1L;
  
  private Month month;
  private int year;
  
  public YearMonth toDate() {
    return YearMonth.of(year, month);
  }

  @Override
  public int compareTo(FinanceMonth o) {
    return toDate().compareTo(o.toDate());
  }
  
}
