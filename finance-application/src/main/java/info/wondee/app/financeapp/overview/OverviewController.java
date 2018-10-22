package info.wondee.app.financeapp.overview;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    
    model.addAttribute("entries", service.createOverviewEntries(user, MAX_ENTRIES));
    model.addAttribute("currentamount", CostPresenter.displayAmount(currentAmount));
    
    return "overview";
    
  }

  @GetMapping(path = "/detail", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public HttpEntity<OverviewEntry> getOverviewDetail(@RequestParam("index") int index) {
    
    FinanceUser user = repository.findCurrentUser();
    
    List<OverviewEntry> entries = service.createOverviewEntries(user, MAX_ENTRIES);
    
    return new HttpEntity<>(entries.get(index));
    
  }
  
  
  @PostMapping("/currentamount")
  public String postCurrentAmount(@RequestParam("currentamount") int newAmount) {
    
    FinanceUser user = repository.findCurrentUser();
    user.setCurrentAmount(newAmount);
    repository.save(user);
    
    return "redirect:/overview";
  }
  

}
