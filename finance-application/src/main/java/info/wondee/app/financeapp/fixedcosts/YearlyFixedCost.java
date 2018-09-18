package info.wondee.app.financeapp.fixedcosts;

import java.time.Month;
import java.util.Objects;

import com.google.common.base.Preconditions;

import info.wondee.app.financeapp.DisplayUtil;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class YearlyFixedCost extends Cost {

  private Month month;

  public YearlyFixedCost(Integer id, String name, int amount, Month month) {
    super(id, name, amount);
    Preconditions.checkNotNull(month);
    this.month = month;
  }
  
  public String getDisplayMonth() {
    return DisplayUtil.createDisplayMonth(month);
  }
  
  @Override
  public boolean appliesAt(Month month) {
    return Objects.equals(this.month, month);
  }
  
}
