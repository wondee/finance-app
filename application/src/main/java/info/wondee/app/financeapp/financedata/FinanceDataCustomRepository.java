package info.wondee.app.financeapp.financedata;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;

import info.wondee.app.financeapp.fixedcosts.MonthlyFixedCost;
import info.wondee.app.financeapp.fixedcosts.QuaterlyFixedCost;
import info.wondee.app.financeapp.fixedcosts.YearlyFixedCost;
import info.wondee.app.financeapp.specialcosts.SpecialCost;

public interface FinanceDataCustomRepository {
  
  void save(FinanceData financeData, MonthlyFixedCost persistentObject, Integer id);
  void save(FinanceData financeData, QuaterlyFixedCost persistentObject, Integer id);
  void save(FinanceData financeData, YearlyFixedCost persistentObject, Integer id);
  void save(FinanceData financeData, SpecialCost persistentObject, Integer id);
  

  void deleteMonthlyFixedCost(FinanceData financeData, int id);
  void deleteQuaterlyFixedCost(FinanceData financeData, int id);
  void deleteYearlyFixedCost(FinanceData financeData, int id);
  void deleteSpecialCost(FinanceData financeData, int id);
  
  @Scheduled(cron = "1 0 1 * * *") // schedule eviction every month at 00:01
  @CacheEvict(value = {"financeDataCache", "overviewCache"})
  default void clearCache() {}
  
}
