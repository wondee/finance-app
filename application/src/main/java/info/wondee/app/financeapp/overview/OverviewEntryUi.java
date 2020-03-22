package info.wondee.app.financeapp.overview;


import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import info.wondee.app.financeapp.FinanceMonth;
import info.wondee.app.financeapp.fixedcosts.FixedCost;
import info.wondee.app.financeapp.specialcosts.SpecialCostUi;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class OverviewEntryUi implements Serializable {

  private static final long serialVersionUID = 1L;

  private FinanceMonth yearMonth;
  private int currentAmount;

  @JsonIgnore
  private List<? extends FixedCost> fixedCosts;

  private int sumFixedCosts;

  @JsonIgnore
  private List<SpecialCostUi> specialCosts;

  private int sumSpecialCosts;

}
