package info.wondee.app.financeapp.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import info.wondee.app.financeapp.fixedcosts.MonthlyFixedCost;
import info.wondee.app.financeapp.fixedcosts.QuaterlyFixedCost;
import info.wondee.app.financeapp.fixedcosts.YearlyFixedCost;
import info.wondee.app.financeapp.specialcosts.SpecialCost;

public class FinanceUserRepositoryImpl implements FinanceUserCustomRespository {

  @Autowired
  private FinanceUserRepository repository;
  
  @Override
  public void save(MonthlyFixedCost cost, Integer id) {
    FinanceUser financeUser = findCurrentUser();
    financeUser.add(cost, id);
    repository.save(financeUser);
  }


  @Override
  public void save(QuaterlyFixedCost cost, Integer id) {
    FinanceUser financeUser = findCurrentUser();
    financeUser.add(cost, id);
    repository.save(financeUser);  
  }

  
  @Override
  public void save(YearlyFixedCost cost, Integer id) {
    FinanceUser financeUser = findCurrentUser();
    financeUser.add(cost, id);
    repository.save(financeUser);  
  }

  @Override
  public void save(SpecialCost cost, Integer id) {
    FinanceUser financeUser = findCurrentUser();
    financeUser.add(cost, id);
    repository.save(financeUser);
  }

  @Override
  public List<MonthlyFixedCost> findMonthlyFixedCosts() {
    return findCurrentUser().getMonthlyFixedCosts();
  }

  @Override
  public List<QuaterlyFixedCost> findQuaterlyFixedCosts() {
    return findCurrentUser().getQuaterlyFixedCosts();
  }
  
  @Override
  public List<YearlyFixedCost> findYearlyFixedCosts() {
    return findCurrentUser().getYearlyFixedCosts();
  }

  @Override
  public List<SpecialCost> findSpecialCosts() {
    return findCurrentUser().getSpecialCosts();
  }

  @Override
  public void deleteMonthlyFixedCost(int id) {
    FinanceUser currentUser = findCurrentUser();
    currentUser.getMonthlyFixedCosts().remove(id);
    repository.save(currentUser);
  }

  @Override
  public void deleteQuaterlyFixedCost(int id) {
    FinanceUser currentUser = findCurrentUser();
    currentUser.getQuaterlyFixedCosts().remove(id);
    repository.save(currentUser);
  }
  
  @Override
  public void deleteYearlyFixedCost(int id) {
    FinanceUser currentUser = findCurrentUser();
    currentUser.getYearlyFixedCosts().remove(id);
    repository.save(currentUser);
  }

  @Override
  public void deleteSpecialCost(int id) {
    FinanceUser currentUser = findCurrentUser();
    currentUser.getSpecialCosts().remove(id);
    repository.save(currentUser);
  }

  @Override
  public FinanceUser findCurrentUser() {
    String userName = SecurityContextHolder.getContext().getAuthentication().getName();
    
    return repository.findByName(userName).orElseThrow(() -> new IllegalStateException("User not found in DB"));
  }


  
}
