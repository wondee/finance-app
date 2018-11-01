package info.wondee.app.financeapp.financedata;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import info.wondee.app.financeapp.fixedcosts.FixedCost;
import info.wondee.app.financeapp.fixedcosts.MonthlyFixedCost;
import info.wondee.app.financeapp.fixedcosts.QuaterlyFixedCost;
import info.wondee.app.financeapp.fixedcosts.YearlyFixedCost;
import info.wondee.app.financeapp.specialcosts.SpecialCost;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FinanceData {

  @Id
  private ObjectId id;
  
  @Setter
  private int currentAmount;
  
  private List<MonthlyFixedCost> monthlyFixedCosts = Lists.newArrayList();
  
  private List<QuaterlyFixedCost> quaterlyFixedCosts = Lists.newArrayList();
  
  private List<YearlyFixedCost> yearlyFixedCosts = Lists.newArrayList();
  
  private List<SpecialCost> specialCosts = Lists.newArrayList();

  public List<FixedCost> getAllFixedCosts() {
    return Lists.newArrayList(Iterables.concat(monthlyFixedCosts, quaterlyFixedCosts, yearlyFixedCosts));
  }
  
}
