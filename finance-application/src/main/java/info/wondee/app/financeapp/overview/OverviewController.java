package info.wondee.app.financeapp.overview;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import info.wondee.app.financeapp.fixedcosts.Cost;
import info.wondee.app.financeapp.specialcosts.SpecialCost;
import info.wondee.app.financeapp.user.FinanceUserRepository;

@Controller
@RequestMapping({"/", "/overview"})
public class OverviewController {

  @Autowired
  private FinanceUserRepository repository;

  
  @GetMapping
  public String getOverview(Model model) {
    
    model.addAttribute("entries", createOverviewEntries(10000, 100));
    return "overview";
    
  }

  private List<OverviewEntry> createOverviewEntries(int currentAmount, int maxEntries) {
    
    Multimap<Month, Cost> costMap = createFixedCostMap();
    Multimap<YearMonth, Cost> specialCostMap = createSpecialCostMap();
    
    LocalDate entryDate = LocalDate.now();

    List<OverviewEntry> overviewEntries = Lists.newLinkedList();
    
    int tmpAmount = currentAmount;
    
    for (int entryIndex = 0; entryIndex < maxEntries; entryIndex++) {
      OverviewEntry entry = new OverviewEntry(entryDate, tmpAmount, 
            costMap.get(entryDate.getMonth()), 
            specialCostMap.get(YearMonth.from(entryDate))
          );
      
      tmpAmount = entry.getCurrentAmount();
      overviewEntries.add(entry);
      
      entryDate = entryDate.plusMonths(1);
    }
    
    return overviewEntries;
  }


  private Multimap<Month, Cost> createFixedCostMap() {
    Multimap<Month, Cost> costMap = HashMultimap.create();
    
    List<Cost> allFixedCosts = repository.findAllFixedCosts();
    
    for (Month month : Month.values()) {
      for (Cost fixedCost : allFixedCosts) {
        
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
      specialCostMap.put(specialCost.getDueDate(), specialCost);
    }
    
    return specialCostMap;
  }
}
