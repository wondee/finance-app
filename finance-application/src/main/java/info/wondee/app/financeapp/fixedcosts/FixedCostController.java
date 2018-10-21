package info.wondee.app.financeapp.fixedcosts;

import static info.wondee.app.financeapp.DisplayUtil.*;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    
    return "fixedcostform";
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
    case QUATERLY:
      return repository.findQuaterlyFixedCosts();
    case SPECIAL:
      return repository.findSpecialCosts();
    default:
      throw new IllegalArgumentException();
    }
  }
  
  @GetMapping
  public String getFixedCosts(Model model) {

    model.addAttribute("monthlyFixedCosts", DisplayUtil.toPresenter(repository.findMonthlyFixedCosts()));
    model.addAttribute("quaterlyFixedCosts", DisplayUtil.toPresenter(repository.findQuaterlyFixedCosts()));
    model.addAttribute("yearlyFixedCosts", DisplayUtil.toPresenter(repository.findYearlyFixedCosts()));

    return "fixedcosts";
  }

  @GetMapping("/monthly")
  public String postMonthlyFixedCost(Model model, RedirectAttributes redirectAttributes) {
    redirectAttributes.addAttribute("type", "monthly");
    return "redirect:/fixedcosts/edit";
  }
  
  @PostMapping("/monthly")
  public String postMonthlyFixedCost(Model model, 
      @Valid @ModelAttribute("model") MonthlyFixedCostPresenter presenter, 
      BindingResult bindingResult) {
    
    return processSaving(model, presenter, bindingResult, "monthly", "fixedcosts",
        (() -> repository.save(presenter.toPersistentObject(), presenter.getId())));
  }
  
  @GetMapping("/quaterly")
  public String postQuaterlyFixedCost(Model model, RedirectAttributes redirectAttributes) {
    redirectAttributes.addAttribute("type", "quaterly");
    return "redirect:/fixedcosts/edit";
  }
  
  @PostMapping("/quaterly")
  public String postQuaterlyFixedCost(Model model, 
      @Valid @ModelAttribute("model") QuaterlyFixedCostPresenter presenter, 
      BindingResult bindingResult) {
    
    return processSaving(model, presenter, bindingResult, "quaterly", "fixedcosts",
        (() -> repository.save(presenter.toPersistentObject(), presenter.getId())));
  }
  
  @GetMapping("/yearly")
  public String postYearlyFixedCost(Model model, RedirectAttributes redirectAttributes) {
    redirectAttributes.addAttribute("type", "yearly");
    return "redirect:/fixedcosts/edit";
  }
  
  @PostMapping("/yearly")
  public String postYearlyFixedCost(Model model, 
      @Valid @ModelAttribute("model") YearlyFixedCostPresenter presenter, 
      BindingResult bindingResult) {
    
    return processSaving(model, presenter, bindingResult, "yearly", "fixedcosts",
        (() -> repository.save(presenter.toPersistentObject(), presenter.getId())));
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
