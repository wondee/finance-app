package info.wondee.app.financeapp.specialcosts;

import java.time.Month;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import info.wondee.app.financeapp.user.FinanceUserRepository;

@Controller
@RequestMapping("/specialcosts")
public class SpecialCostController {

  @Autowired
  private FinanceUserRepository repository;
  
  @GetMapping
  public String getSpecialCosts(Model model) {
    
    List<SpecialCost> all = repository.findSpecialCosts();
    
    model.addAttribute("specialCosts", all);
    
    return "specialcosts";
  }
  
  @PostMapping
  public String postSpecialCosts(
            @ModelAttribute("name") String name, 
            @ModelAttribute("amount") int amount, 
            @ModelAttribute("month") int month, 
            @ModelAttribute("year") int year) {
    repository.add(new SpecialCost(null, name, amount, Month.values()[month], year));
    
    return "redirect:/specialcosts";
  }
  
  @GetMapping("/delete")
  public String deleteSpecialCost(@RequestParam("id") int id) {
    repository.deleteSpecialCost(id);
    return "redirect:/specialcosts";
  }
  
}
