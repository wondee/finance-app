package info.wondee.app.financeapp.fixedcosts;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import info.wondee.app.financeapp.DisplayUtil;
import info.wondee.app.financeapp.user.FinanceUserRepository;

@Controller
@RequestMapping("/fixedcosts")
public class FixedCostController {

  @Autowired
  private FinanceUserRepository repository;

  @GetMapping("/edit")
  public String editFixedCost(Model model, @RequestParam("type") String type, @RequestParam("id") Optional<Integer> optionalId) {
    
    CostType costType = CostType.valueOf(type.toUpperCase());
    
    CostPresenter<? extends Cost> presenter = optionalId
        .map(id -> retrieveExistingPresenter(costType, id))
        .orElse(costType.createInstance());
    
    model.addAttribute("type", type);
    model.addAttribute("model", presenter);
    
    return "fixedcostform.html";
  }


  private CostPresenter<? extends Cost> retrieveExistingPresenter(CostType costType, Integer id) {
    
    List<? extends Cost> costs = findCostsByType(costType);
    CostPresenter<? extends Cost> presenter = costType.toPresenter(costs.get(id));
    presenter.setId(id);
    return presenter;
  }

  private List<? extends Cost>  findCostsByType(CostType costType) {
    switch (costType) {
    case MONTHLY:
      return repository.findMonthlyFixedCosts();
    case YEARLY:
      return repository.findYearlyFixedCosts();
    case SPECIAL:
      return repository.findSpecialCosts();
    default:
      throw new IllegalArgumentException();
    }
  }
  
  @GetMapping
  public String getFixedCosts(Model model) {

    model.addAttribute("monthlyFixedCosts", DisplayUtil.toPresenter(repository.findMonthlyFixedCosts()));
    // TODO print total
//    model.addAttribute("monthyAmount", Cost.sumList(monthlyCosts));
    
    model.addAttribute("yearlyFixedCosts", DisplayUtil.toPresenter(repository.findYearlyFixedCosts()));

    return "fixedcosts";
  }

  @PostMapping("/monthly")
  public String postMonthlyFixedCost(@ModelAttribute MonthlyFixedCostPresenter presenter) {
    repository.save(presenter.toPersistentObject(), presenter.getId());
    
    return "redirect:/fixedcosts";
  }

  @PostMapping("/yearly")
  public String postYearlyFixedCost(@ModelAttribute YearlyFixedCostPresenter presenter) {
    repository.save(presenter.toPersistentObject(), presenter.getId());
    
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
