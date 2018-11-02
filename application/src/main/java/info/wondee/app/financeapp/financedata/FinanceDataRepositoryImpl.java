package info.wondee.app.financeapp.financedata;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import info.wondee.app.financeapp.fixedcosts.Cost;
import info.wondee.app.financeapp.fixedcosts.MonthlyFixedCost;
import info.wondee.app.financeapp.fixedcosts.QuaterlyFixedCost;
import info.wondee.app.financeapp.fixedcosts.YearlyFixedCost;
import info.wondee.app.financeapp.specialcosts.SpecialCost;

public class FinanceDataRepositoryImpl implements FinanceDataCustomRepository {

  @Autowired
  FinanceDataRepository repository;

  @Override
  public void save(FinanceData financeData, MonthlyFixedCost cost, Integer id) {
    addOrReplace(cost, financeData.getMonthlyFixedCosts(), id);
    repository.save(financeData);
  }

  @Override
  public void save(FinanceData financeData, QuaterlyFixedCost cost, Integer id) {
    addOrReplace(cost, financeData.getQuaterlyFixedCosts(), id);
    repository.save(financeData);
  }

  @Override
  public void save(FinanceData financeData, YearlyFixedCost cost, Integer id) {
    addOrReplace(cost, financeData.getYearlyFixedCosts(), id);
    repository.save(financeData);
    
  }

  @Override
  public void save(FinanceData financeData, SpecialCost cost, Integer id) {
    addOrReplace(cost, financeData.getSpecialCosts(), id);
    repository.save(financeData);
  }

  private <T extends Cost & Comparable<T>> void addOrReplace(T cost, List<T> coll, Integer id) {
    if (id == null) {
      coll.add(cost);
    } else {
      coll.set(id, cost);
    }
    Collections.sort(coll);
  }
  
  @Override
  public void deleteMonthlyFixedCost(FinanceData financeData, int id) {
    financeData.getMonthlyFixedCosts().remove(id);
    repository.save(financeData);
  }

  @Override
  public void deleteQuaterlyFixedCost(FinanceData financeData, int id) {
    financeData.getQuaterlyFixedCosts().remove(id);
    repository.save(financeData);
  }

  @Override
  public void deleteYearlyFixedCost(FinanceData financeData, int id) {
    financeData.getYearlyFixedCosts().remove(id);
    repository.save(financeData);
  }

  @Override
  public void deleteSpecialCost(FinanceData financeData, int id) {
    financeData.getSpecialCosts().remove(id);
    repository.save(financeData);
  }


}
