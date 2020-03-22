package info.wondee.app.financeapp.specialcosts;

import com.fasterxml.jackson.annotation.JsonCreator;
import info.wondee.app.financeapp.FinanceMonth;
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

  @JsonCreator
  public SpecialCostUi(Integer id, String name, int amount, YearMonth dueYearMonth) {
    super(id, name, amount);
    this.dueYearMonth = dueYearMonth;
  }

  public SpecialCostUi(SpecialCost specialCost) {
    super(specialCost);
    this.dueYearMonth = specialCost.getDueDate().toDate();
  }

  @Override
  public CostType getType() {
    return CostType.SPECIAL;
  }

  public SpecialCost toDataObject() {
    return new SpecialCost(getId(), getName(), getAmount(), new FinanceMonth(dueYearMonth));
  }
}
