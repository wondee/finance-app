package info.wondee.app.financeapp.fixedcosts;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

  private <S, T extends S> List<T> filter(List<S> list, Class<T> type) {
    return list.stream()
          .filter(type::isInstance)
          .map(type::cast)
        .collect(Collectors.toList());
  }

}
