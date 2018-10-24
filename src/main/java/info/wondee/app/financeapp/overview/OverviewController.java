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

import info.wondee.app.financeapp.fixedcosts.Cost;
import info.wondee.app.financeapp.fixedcosts.CostPresenter;
import info.wondee.app.financeapp.user.FinanceUser;
import info.wondee.app.financeapp.user.FinanceUserRepository;

@Controller
@RequestMapping({"/", "/overview"})
public class OverviewController {
  
  @Autowired
  private FinanceUserRepository repository;

  @Autowired
  private OverviewService service;
  
  private static final int MAX_ENTRIES = 50;
  
  @GetMapping("/all")
  public HttpEntity<List<OverviewEntry>> getOverview() {
    FinanceUser user = repository.findCurrentUser();

    List<OverviewEntry> allEntries = service.createOverviewEntries(user, MAX_ENTRIES);
    
    
    return new HttpEntity<List<OverviewEntry>>(allEntries);
  }
  
  
  @GetMapping
  public String getOverview(Model model) {
    
    FinanceUser user = repository.findCurrentUser();
    
    int currentAmount = user.getCurrentAmount();
    
    model.addAttribute("currentamount", currentAmount);
    model.addAttribute("displayCurrentamount", CostPresenter.displayAmount(currentAmount));
    
    return "overview";
    
  }

  @GetMapping(path = "/detail", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public HttpEntity<Map<String, List<? extends CostPresenter<? extends Cost>>>> getOverviewDetail(@RequestParam("index") int index) {
    
    FinanceUser user = repository.findCurrentUser();
    
    List<OverviewEntry> entries = service.createOverviewEntries(user, MAX_ENTRIES);
    OverviewEntry entry = entries.get(index);
    
    ImmutableMap<String, List<? extends CostPresenter<? extends Cost>>> result = 
        ImmutableMap.of("fixedCosts", entry.getFixedCosts(), "specialCosts", entry.getSpecialCosts());
    
    return new HttpEntity<>(result);
    
  }
  
  
  @PostMapping("/currentamount")
  public String postCurrentAmount(@RequestParam("currentamount") int newAmount) {
    
    FinanceUser user = repository.findCurrentUser();
    user.setCurrentAmount(newAmount);
    repository.save(user);
    
    return "redirect:/overview";
  }
  

}
