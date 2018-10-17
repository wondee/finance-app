package info.wondee.app.financeapp.overview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import info.wondee.app.financeapp.user.FinanceUser;
import info.wondee.app.financeapp.user.FinanceUserRepository;

@Controller
@RequestMapping({"/", "/overview"})
public class OverviewController {
  
  @Autowired
  private FinanceUserRepository repository;

  @Autowired
  private OverviewService service;
  
  
  @GetMapping
  public String getOverview(Model model) {
    
    FinanceUser user = repository.findCurrentUser();
    
    int currentAmount = user.getCurrentAmount();
    
    model.addAttribute("entries", service.createOverviewEntries(user, currentAmount, 50));
    model.addAttribute("currentamount", currentAmount);
    
    return "overview";
    
  }
  
  @PostMapping("/currentamount")
  public String postCurrentAmount(@RequestParam("currentamount") int newAmount) {
    
    FinanceUser user = repository.findCurrentUser();
    user.setCurrentAmount(newAmount);
    repository.save(user);
    
    return "redirect:/overview";
  }
  

}
