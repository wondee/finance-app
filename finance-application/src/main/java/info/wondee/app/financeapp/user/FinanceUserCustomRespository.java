package info.wondee.app.financeapp.user;

import java.util.List;

import info.wondee.app.financeapp.fixedcosts.Cost;
import info.wondee.app.financeapp.fixedcosts.MonthlyFixedCost;
import info.wondee.app.financeapp.fixedcosts.YearlyFixedCost;
import info.wondee.app.financeapp.specialcosts.SpecialCost;

public interface FinanceUserCustomRespository {

  List<MonthlyFixedCost> findMonthlyFixedCosts();
  List<YearlyFixedCost> findYearlyFixedCosts();
  List<SpecialCost> findSpecialCosts();

  List<Cost> findAllFixedCosts();

  
  void add(MonthlyFixedCost cost);
  void add(YearlyFixedCost cost);
  void add(SpecialCost cost);
  
  void deleteMonthlyFixedCost(int id);
  void deleteYearlyFixedCost(int id);
  void deleteSpecialCost(int id);
  
}
