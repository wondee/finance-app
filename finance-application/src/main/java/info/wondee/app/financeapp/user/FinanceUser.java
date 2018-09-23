package info.wondee.app.financeapp.user;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.collect.Lists;

import info.wondee.app.financeapp.fixedcosts.Cost;
import info.wondee.app.financeapp.fixedcosts.MonthlyFixedCost;
import info.wondee.app.financeapp.fixedcosts.YearlyFixedCost;
import info.wondee.app.financeapp.specialcosts.SpecialCost;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FinanceUser {
  @Id
  private String id;
  
  @Indexed(unique=true)
  private String name;
  
  private String password;
  
  private int currentAmount;
  
  private List<MonthlyFixedCost> monthlyFixedCosts;
  
  private List<YearlyFixedCost> yearlyFixedCosts;
  
  private List<SpecialCost> specialCosts;
  
  public FinanceUser(String name, String password) {
    this(null, name, password, 0, Lists.newArrayList(), Lists.newArrayList(), Lists.newArrayList());
  }

  public void add(MonthlyFixedCost cost) {
    monthlyFixedCosts.add(cost);
    Collections.sort(monthlyFixedCosts);
  }
  
  public void add(YearlyFixedCost cost) {
    yearlyFixedCosts.add(cost);
    Collections.sort(monthlyFixedCosts);
  }
  
  public void add(SpecialCost cost) {
    specialCosts.add(cost);
    Collections.sort(monthlyFixedCosts);
  }

  public void removeIfFound(int id) {
    removeIfFound(id, monthlyFixedCosts);
    removeIfFound(id, yearlyFixedCosts);
    removeIfFound(id, specialCosts);
    
  }
  
  public void removeIfFound(int id, Collection<? extends Cost> costs) {
    for (Iterator<? extends Cost> iterator = costs.iterator(); iterator.hasNext();) {
      Cost monthlyFixedCost = iterator.next();
      
      if (monthlyFixedCost.getId().equals(id)) {
        iterator.remove();
        break;
      }
    }
  }
}
