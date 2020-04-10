package info.wondee.app.financeapp.fixedcosts;

import static com.google.common.collect.ImmutableMap.of;
import static java.util.Collections.emptyList;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import info.wondee.app.financeapp.financedata.FinanceData;
import info.wondee.app.financeapp.financedata.FinanceDataRepository;
import info.wondee.app.financeapp.specialcosts.SpecialCost;
import info.wondee.app.financeapp.user.UserService;

@RestController
@RequestMapping("/api")
public class CostController {

	@Autowired
	private UserService userService;

	@Autowired
	private FinanceDataRepository financeDataRepository;

	@GetMapping("/costs")
	public Map<String, Object> getFixedCosts() {

		
		FinanceData financeData = userService.findFinanceData();

		List<MonthlyFixedCost> monthly = financeData.getMonthlyFixedCosts();
		List<QuaterlyFixedCost> quaterly = financeData.getQuaterlyFixedCosts();
		List<YearlyFixedCost> yearly = financeData.getYearlyFixedCosts();
		
		return of(
				"monthly", monthly,
				"quaterly", quaterly, 
				"halfyearly", emptyList(), // TODO implement my!
				"yearly", yearly,
				"currentBalance", calculateCurrentBalance(monthly, quaterly, yearly)
			);

	}


  @GetMapping("/specialcosts")
  public List<SpecialCost> getSpecialCosts() {
    return userService.findFinanceData().getSpecialCosts();
  }
  
  @PostMapping("/specialcosts")
  public SpecialCost postSpecialCosts(@RequestBody SpecialCost cost) {
  	
  	financeDataRepository.save(userService.findFinanceData(), cost, cost.getId());
  	
    return cost;
  }
  
  
	
  
  private int calculateCurrentBalance(
      List<MonthlyFixedCost> monthly, 
      List<QuaterlyFixedCost> quaterly,
      List<YearlyFixedCost> yearly) {
    
    AtomicInteger sum = new AtomicInteger(0);
    
    YearMonth now = YearMonth.now();
    
    Predicate<FixedCost> appliable= cost -> cost.isActive(now);
    
    monthly.stream().filter(appliable).forEach(cost -> sum.addAndGet(cost.getAmount()));
    quaterly.stream().filter(appliable).forEach(cost -> sum.addAndGet(cost.getAmount() * 4 / 12));
    yearly.stream().filter(appliable).forEach(cost -> sum.addAndGet(cost.getAmount() / 12));
    
    return sum.get();
  }
	
}
