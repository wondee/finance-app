package info.wondee.app.financeapp;

import java.io.Serializable;
import java.time.Month;
import java.time.YearMonth;

import org.springframework.data.annotation.PersistenceConstructor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class FinanceMonth implements Comparable<FinanceMonth>, Serializable {

  private static final long serialVersionUID = 1L;
  
  private Month month;
  private int year;
  
  @PersistenceConstructor
  public FinanceMonth(Month month, int year) {
    super();
    this.month = month;
    this.year = year;
  }
  
  
  public FinanceMonth(YearMonth yearMonth) {
    month = yearMonth.getMonth();
    year = yearMonth.getYear();
  }
  
  public YearMonth toDate() {
    return YearMonth.of(year, month);
  }

  @Override
  public int compareTo(FinanceMonth o) {
    return toDate().compareTo(o.toDate());
  }

  
}
