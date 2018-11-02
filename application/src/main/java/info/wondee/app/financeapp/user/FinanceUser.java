package info.wondee.app.financeapp.user;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.collect.Lists;

import info.wondee.app.financeapp.fixedcosts.Cost;
import info.wondee.app.financeapp.fixedcosts.FixedCost;
import info.wondee.app.financeapp.fixedcosts.MonthlyFixedCost;
import info.wondee.app.financeapp.fixedcosts.QuaterlyFixedCost;
import info.wondee.app.financeapp.fixedcosts.YearlyFixedCost;
import info.wondee.app.financeapp.specialcosts.SpecialCost;
import lombok.Getter;
import lombok.Setter;

@Document
@Getter
public class FinanceUser extends UserLogin {

  private static final long serialVersionUID = 1L;
  
  @Setter
  private int currentAmount;
  
  private List<MonthlyFixedCost> monthlyFixedCosts = Lists.newArrayList();
  
  private List<QuaterlyFixedCost> quaterlyFixedCosts = Lists.newArrayList();
  
  private List<YearlyFixedCost> yearlyFixedCosts = Lists.newArrayList();
  
  private List<SpecialCost> specialCosts = Lists.newArrayList();
  
  public FinanceUser(String name, String password) {
    super(null, name, password);
    currentAmount = 0;
    monthlyFixedCosts = Lists.newArrayList();
    quaterlyFixedCosts = Lists.newArrayList();
    yearlyFixedCosts = Lists.newArrayList();
    specialCosts = Lists.newArrayList();
  }

  public void add(MonthlyFixedCost cost, Integer id) {
    addOrReplace(cost, monthlyFixedCosts, id);
  }
  
  public void add(QuaterlyFixedCost cost, Integer id) {
    addOrReplace(cost, quaterlyFixedCosts, id);
  }
  
  public void add(YearlyFixedCost cost, Integer id) {
    addOrReplace(cost, yearlyFixedCosts, id);
  }
  
  public void add(SpecialCost cost, Integer id) {
    addOrReplace(cost, specialCosts, id);
  }

  private <T extends Cost & Comparable<T>> void addOrReplace(T cost, List<T> coll, Integer id) {
    if (id == null) {
      coll.add(cost);
    } else {
      coll.set(id, cost);
    }
    Collections.sort(coll);
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

  public List<FixedCost> getAllFixedCosts() {
    List<FixedCost> costs = Lists.newArrayList();
    
    costs.addAll(getMonthlyFixedCosts());
    costs.addAll(getQuaterlyFixedCosts());
    costs.addAll(getYearlyFixedCosts());
    
    return costs;
  }
}
