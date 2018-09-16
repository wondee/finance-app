package info.wondee.app.financeapp.overview;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import info.wondee.app.financeapp.fixedcosts.FixedCost;
import info.wondee.app.financeapp.fixedcosts.FixedCostRepository;

@Controller
@RequestMapping("/overview")
public class OverviewController {

  @Autowired
  private FixedCostRepository fixedCostPRepository;
  
  @GetMapping
  public String getOverview(Model model) {
    
    model.addAttribute("entries", createOverviewEntries(10000, 100));
    return "overview";
    
  }

  private List<OverviewEntry> createOverviewEntries(int currentAmount, int maxEntries) {
    
    Multimap<Month, FixedCost> costMap = HashMultimap.create();
    
    List<FixedCost> allFixedCosts = fixedCostPRepository.findAllFixedCosts();
    
    for (Month month : Month.values()) {
      for (FixedCost fixedCost : allFixedCosts) {
        
        if (fixedCost.appliesAt(month)) {
          costMap.put(month, fixedCost);
        }
        
      }
    }
    
    System.out.println(costMap);
    
    LocalDate entryDate = LocalDate.now();

    List<OverviewEntry> overviewEntries = Lists.newLinkedList();
    
    int tmpAmount = currentAmount;
    
    for (int entryIndex = 0; entryIndex < maxEntries; entryIndex++) {
      OverviewEntry entry = new OverviewEntry(entryDate, tmpAmount, costMap.get(entryDate.getMonth()), Lists.<FixedCost>newArrayList());
      
      
      tmpAmount = entry.getCurrentAmount();
      overviewEntries.add(entry);
      
      entryDate = entryDate.plusMonths(1);
    }
    
    return overviewEntries;
  }
  
}
