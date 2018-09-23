package info.wondee.app.financeapp.fixedcosts;

import java.time.Month;

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
@RequestMapping("/fixedcosts")
public class FixedCostController {

  @Autowired
  private FinanceUserRepository repository;

  @GetMapping
  public String getFixedCosts(Model model) {

    model.addAttribute("monthlyFixedCosts", repository.findMonthlyFixedCosts());
//    model.addAttribute("monthyAmount", Cost.sumList(monthlyCosts));
    
    model.addAttribute("yearlyFixedCosts", repository.findYearlyFixedCosts());

    return "fixedcosts";
  }

  @PostMapping("/monthly")
  public String postMonthlyFixedCost(@ModelAttribute("name") String name, @ModelAttribute("amount") int amount) {
    repository.add(new MonthlyFixedCost(null, name, amount));
    
    return "redirect:/fixedcosts";
  }

  @PostMapping("/yearly")
  public String postYearlyFixedCost(@ModelAttribute("name") String name, @ModelAttribute("amount") int amount, @ModelAttribute("month") int month) {
    repository.add(new YearlyFixedCost(null, name, amount, Month.values()[month]));
    
    return "redirect:/fixedcosts";
  }
  
  @GetMapping("/delete")
  public String deleteFixedCost(@RequestParam("id") int id, @RequestParam("type") String type) {
    switch (type) {
    case "monthly":
      repository.deleteMonthlyFixedCost(id); break;
    case "yearly":
      repository.deleteYearlyFixedCost(id); break;

    default:
      throw new IllegalArgumentException("type '" + type + "' is not supported for deletion");
    }
    
    return "redirect:/fixedcosts";
  }

}
