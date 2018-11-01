package info.wondee.app.financeapp.overview;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.ImmutableMap;

import info.wondee.app.financeapp.financedata.FinanceData;
import info.wondee.app.financeapp.financedata.FinanceDataRepository;
import info.wondee.app.financeapp.fixedcosts.Cost;
import info.wondee.app.financeapp.fixedcosts.CostPresenter;
import info.wondee.app.financeapp.user.UserService;

@Controller
@RequestMapping({"/", "/overview"})
public class OverviewController {
  
  @Autowired
  private UserService userService;

  @Autowired
  private OverviewService overviewService;
  
  @Autowired
  private FinanceDataRepository repository;
  
  private static final int MAX_ENTRIES = 50;
  
  @GetMapping("/all")
  public HttpEntity<List<OverviewEntry>> getOverview() {
    FinanceData data = userService.findFinanceData();

    List<OverviewEntry> allEntries = overviewService.createOverviewEntries(data, MAX_ENTRIES);
    
    return new HttpEntity<List<OverviewEntry>>(allEntries);
  }
  
  @GetMapping
  public String getOverview(Model model) {
    
    FinanceData data = userService.findFinanceData();
    
    int currentAmount = data.getCurrentAmount();
    
    model.addAttribute("currentamount", currentAmount);
    model.addAttribute("displayCurrentamount", CostPresenter.displayAmount(currentAmount));
    
    return "overview";
    
  }

  @GetMapping(path = "/detail", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public HttpEntity<Map<String, List<? extends CostPresenter<? extends Cost>>>> getOverviewDetail(@RequestParam("index") int index) {
    
    FinanceData data = userService.findFinanceData();
    
    List<OverviewEntry> entries = overviewService.createOverviewEntries(data, MAX_ENTRIES);
    OverviewEntry entry = entries.get(index);
    
    ImmutableMap<String, List<? extends CostPresenter<? extends Cost>>> result = 
        ImmutableMap.of("fixedCosts", entry.getFixedCosts(), "specialCosts", entry.getSpecialCosts());
    
    return new HttpEntity<>(result);
    
  }
  
  
  @PostMapping("/currentamount")
  public String postCurrentAmount(@RequestParam("currentamount") int newAmount) {
    
    FinanceData user = userService.findFinanceData();
    user.setCurrentAmount(newAmount);
    repository.save(user);
    
    return "redirect:/overview";
  }
  

}
