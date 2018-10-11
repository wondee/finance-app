package info.wondee.app.financeapp.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.google.common.collect.Lists;

import info.wondee.app.financeapp.fixedcosts.FixedCost;
import info.wondee.app.financeapp.fixedcosts.MonthlyFixedCost;
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
  public List<FixedCost> findAllFixedCosts() {
    List<FixedCost> costs = Lists.newArrayList();
    
    FinanceUser currentUser = findCurrentUser();

    costs.addAll(currentUser.getMonthlyFixedCosts());
    costs.addAll(currentUser.getYearlyFixedCosts());
    
    return costs;
  }

  @Override
  public List<MonthlyFixedCost> findMonthlyFixedCosts() {
    return findCurrentUser().getMonthlyFixedCosts();
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

  
  private FinanceUser findCurrentUser() {
    String userName = SecurityContextHolder.getContext().getAuthentication().getName();
    
    return repository.findByName(userName).orElseThrow(() -> new IllegalStateException("User not found in DB"));
  }

  
}
