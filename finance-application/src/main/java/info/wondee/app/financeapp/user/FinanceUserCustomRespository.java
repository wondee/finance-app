package info.wondee.app.financeapp.user;

import java.util.List;

import info.wondee.app.financeapp.fixedcosts.MonthlyFixedCost;
import info.wondee.app.financeapp.fixedcosts.QuaterlyFixedCost;
import info.wondee.app.financeapp.fixedcosts.YearlyFixedCost;
import info.wondee.app.financeapp.specialcosts.SpecialCost;

public interface FinanceUserCustomRespository {

  FinanceUser findCurrentUser();
  
  List<MonthlyFixedCost> findMonthlyFixedCosts();
  List<QuaterlyFixedCost> findQuaterlyFixedCosts();
  List<YearlyFixedCost> findYearlyFixedCosts();
  List<SpecialCost> findSpecialCosts();
  
  void save(MonthlyFixedCost cost, Integer id);
  void save(QuaterlyFixedCost cost, Integer id);
  void save(YearlyFixedCost cost, Integer id);
  void save(SpecialCost cost, Integer id);
  
  void deleteMonthlyFixedCost(int id);
  void deleteQuaterlyFixedCost(int id);
  void deleteYearlyFixedCost(int id);
  void deleteSpecialCost(int id);
  
}
