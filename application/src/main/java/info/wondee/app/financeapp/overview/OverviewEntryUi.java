package info.wondee.app.financeapp.overview;


import com.fasterxml.jackson.annotation.JsonIgnore;
import info.wondee.app.financeapp.fixedcosts.Cost;
import info.wondee.app.financeapp.fixedcosts.CostPresenter;
import info.wondee.app.financeapp.fixedcosts.FixedCost;
import info.wondee.app.financeapp.specialcosts.SpecialCostUi;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class OverviewEntryUi implements Serializable {

  private static final long serialVersionUID = 1L;

  private YearMonth yearMonth;
  private int currentAmount;

  @JsonIgnore
  private List<? extends FixedCost> fixedCosts;

  private int sumFixedCosts;

  @JsonIgnore
  private List<SpecialCostUi> specialCosts;

  private int sumSpecialCosts;

}
