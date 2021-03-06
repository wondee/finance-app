package info.wondee.app.financeapp.overview;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import info.wondee.app.financeapp.DisplayUtil;
import info.wondee.app.financeapp.financedata.FinanceData;
import info.wondee.app.financeapp.fixedcosts.Cost;
import info.wondee.app.financeapp.fixedcosts.FixedCost;
import info.wondee.app.financeapp.specialcosts.SpecialCost;

@Service
public class OverviewService {

  private static final Logger LOG = LoggerFactory.getLogger(OverviewService.class);

  
  @Cacheable(cacheNames="overviewCache", key="#data.id")
  public List<OverviewEntry> createOverviewEntries(FinanceData data, int maxEntries) {
    
    Multimap<Month, FixedCost> fixedCostMap = createFixedCostMap(data);
    Multimap<YearMonth, Cost> specialCostMap = createSpecialCostMap(data);
    
    LocalDate entryDate = LocalDate.now();

    List<OverviewEntry> overviewEntries = Lists.newLinkedList();
    
    int tmpAmount = data.getCurrentAmount();
    
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

  private Multimap<Month, FixedCost> createFixedCostMap(FinanceData data) {
    Multimap<Month, FixedCost> costMap = HashMultimap.create();
    
    List<FixedCost> allFixedCosts = data.getAllFixedCosts();
    
    for (Month month : Month.values()) {
      for (FixedCost fixedCost : allFixedCosts) {
        
        if (fixedCost.appliesAt(month)) {
          costMap.put(month, fixedCost);
        }
        
      }
    }
    return costMap;
  }
  
  private Multimap<YearMonth, Cost> createSpecialCostMap(FinanceData data) {
    
    List<SpecialCost> allSpecialCosts = data.getSpecialCosts();
    
    for (int i = 0; i < allSpecialCosts.size(); i++) {
      allSpecialCosts.get(i).setId(i);
    }
    
    Multimap<YearMonth, Cost> specialCostMap = HashMultimap.create();
    
    for (SpecialCost specialCost : allSpecialCosts) {
      specialCostMap.put(specialCost.getDueDate().toDate(), specialCost);
    }
    
    return specialCostMap;
  }
}
