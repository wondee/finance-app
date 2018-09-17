package info.wondee.app.financeapp.fixedcosts;

import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/fixedcosts")
public class FixedCostController {

  @Autowired
  private FixedCostRepository repository;

  @GetMapping
  public String getFixedCosts(Model model) {
    List<FixedCost> all = repository.findAllFixedCosts();

    List<MonthlyFixedCost> monthlyCosts = filter(all, MonthlyFixedCost.class);
    model.addAttribute("monthlyFixedCosts", monthlyCosts);
    model.addAttribute("monthyAmount", FixedCost.sumList(monthlyCosts));
    
    model.addAttribute("yearlyFixedCosts", filter(all, YearlyFixedCost.class));

    return "fixedCosts";
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
  public String deleteFixedCost(@RequestParam("id") int id) {
    repository.delete(id);
    
    return "redirect:/fixedcosts";
  }
  
  private <S, T extends S> List<T> filter(List<S> list, Class<T> type) {
    return list.stream()
          .filter(type::isInstance)
          .map(type::cast)
        .collect(Collectors.toList());
  }

}
