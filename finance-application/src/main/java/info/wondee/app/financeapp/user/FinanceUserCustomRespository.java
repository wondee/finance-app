package info.wondee.app.financeapp.user;

import java.util.List;

import info.wondee.app.financeapp.fixedcosts.FixedCost;
import info.wondee.app.financeapp.fixedcosts.MonthlyFixedCost;
import info.wondee.app.financeapp.fixedcosts.YearlyFixedCost;
import info.wondee.app.financeapp.specialcosts.SpecialCost;

public interface FinanceUserCustomRespository {

  List<MonthlyFixedCost> findMonthlyFixedCosts();
  List<YearlyFixedCost> findYearlyFixedCosts();
  List<SpecialCost> findSpecialCosts();

  List<FixedCost> findAllFixedCosts();

  
  void save(MonthlyFixedCost cost, Integer id);
  void save(YearlyFixedCost cost, Integer id);
  void save(SpecialCost cost, Integer id);
  
  void deleteMonthlyFixedCost(int id);
  void deleteYearlyFixedCost(int id);
  void deleteSpecialCost(int id);
  
}
