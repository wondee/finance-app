package info.wondee.app.financeapp.overview;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import info.wondee.app.financeapp.DisplayUtil;
import info.wondee.app.financeapp.fixedcosts.Cost;
import info.wondee.app.financeapp.fixedcosts.FixedCost;
import info.wondee.app.financeapp.specialcosts.SpecialCost;
import info.wondee.app.financeapp.user.FinanceUserRepository;

@Controller
@RequestMapping({"/", "/overview"})
public class OverviewController {

  private static final Logger LOG = LoggerFactory.getLogger(OverviewController.class);
  
  @Autowired
  private FinanceUserRepository repository;

  
  @GetMapping
  public String getOverview(Model model) {
    
    model.addAttribute("entries", createOverviewEntries(10000, 100));
    return "overview";
    
  }

  private List<OverviewEntry> createOverviewEntries(int currentAmount, int maxEntries) {
    
    Multimap<Month, FixedCost> fixedCostMap = createFixedCostMap();
    Multimap<YearMonth, Cost> specialCostMap = createSpecialCostMap();
    
    LocalDate entryDate = LocalDate.now();

    List<OverviewEntry> overviewEntries = Lists.newLinkedList();
    
    int tmpAmount = currentAmount;
    
    for (int entryIndex = 0; entryIndex < maxEntries; entryIndex++) {
      YearMonth currentMonth = YearMonth.from(entryDate);
      
      List<FixedCost> appliableFixedCosts = fixedCostMap.get(entryDate.getMonth())
          .stream()
          .filter(cost -> cost.isActive(currentMonth))
          .collect(Collectors.toList());
      
      int sumFixedCosts = sumCosts(appliableFixedCosts);
      
      Collection<Cost> appliableSpecialCosts = specialCostMap.get(currentMonth);
      
      int sumSpecialCosts = sumCosts(appliableSpecialCosts);
      
      tmpAmount += sumFixedCosts + sumSpecialCosts;

      LOG.trace("tmpAmount: {}", tmpAmount);
      LOG.trace("calculating {}: FixedCosts: {}", currentMonth, appliableFixedCosts);
      
      
      OverviewEntry entry = new OverviewEntry(
            YearMonth.from(entryDate), 
            tmpAmount, 
            DisplayUtil.toPresenter(appliableFixedCosts), 
            sumFixedCosts,
            DisplayUtil.toPresenter(appliableSpecialCosts),
            sumSpecialCosts
          );
      
      tmpAmount = entry.getCurrentAmount();
      overviewEntries.add(entry);
      
      entryDate = entryDate.plusMonths(1);
    }
    
    return overviewEntries;
  }

  private int sumCosts(Collection<? extends Cost> costs) {
    int result = 0;
    
    for(Cost cost : costs) {
      result += cost.getAmount();
    }
    return result;
  }

  private Multimap<Month, FixedCost> createFixedCostMap() {
    Multimap<Month, FixedCost> costMap = HashMultimap.create();
    
    List<FixedCost> allFixedCosts = repository.findAllFixedCosts();
    
    for (Month month : Month.values()) {
      for (FixedCost fixedCost : allFixedCosts) {
        
        if (fixedCost.appliesAt(month)) {
          costMap.put(month, fixedCost);
        }
        
      }
    }
    return costMap;
  }
  
  private Multimap<YearMonth, Cost> createSpecialCostMap() {
    
    List<SpecialCost> allSpecialCosts = repository.findSpecialCosts();
    
    Multimap<YearMonth, Cost> specialCostMap = HashMultimap.create();
    
    for (SpecialCost specialCost : allSpecialCosts) {
      specialCostMap.put(specialCost.getDueDate().toDate(), specialCost);
    }
    
    return specialCostMap;
  }
}
