package info.wondee.app.financeapp.fixedcosts;

import com.google.common.base.Preconditions;
import lombok.Getter;

import java.time.YearMonth;

@Getter
public abstract class FixedCostUi extends Cost {

  private final YearMonth from;
  private final YearMonth to;

  public FixedCostUi(Integer id, String name, int amount, YearMonth from, YearMonth to) {
    super(id, name, amount);

    this.from = from;
    this.to = to;

    if (from != null && to != null) {
      Preconditions.checkArgument(from.isBefore(to),
          "from date must be before to date! (from: %s, to: %s)", from, to);
    }
  }

}
