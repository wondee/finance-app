package info.wondee.app.financeapp.specialcosts;

import info.wondee.app.financeapp.fixedcosts.Cost;
import info.wondee.app.financeapp.fixedcosts.CostType;
import lombok.Getter;
import lombok.ToString;

import java.time.YearMonth;

@Getter
@ToString
public class SpecialCostUi extends Cost {

  private static final long serialVersionUID = 1L;

  YearMonth dueYearMonth;

  public SpecialCostUi(SpecialCost specialCost) {
    super(specialCost);
    this.dueYearMonth = specialCost.getDueDate().toDate();
  }

  @Override
  public CostType getType() {
    return CostType.SPECIAL;
  }
}
