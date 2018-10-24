package info.wondee.app.financeapp.fixedcosts;

import static info.wondee.app.financeapp.DisplayUtil.*;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

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
import info.wondee.app.financeapp.DisplayUtil.Target;
import info.wondee.app.financeapp.user.FinanceUserRepository;

@Controller
@RequestMapping("/fixedcosts")
public class FixedCostController {

  @Autowired
  private FinanceUserRepository repository;
  
  @GetMapping
  public String getFixedCosts(Model model) {

    List<MonthlyFixedCost> monthly = repository.findMonthlyFixedCosts();
    List<QuaterlyFixedCost> quaterly = repository.findQuaterlyFixedCosts();
    List<YearlyFixedCost> yearly = repository.findYearlyFixedCosts();

    model.addAttribute("monthlyFixedCosts", DisplayUtil.toPresenter(monthly));
    model.addAttribute("quaterlyFixedCosts", DisplayUtil.toPresenter(quaterly));
    model.addAttribute("yearlyFixedCosts", DisplayUtil.toPresenter(yearly));

    model.addAttribute("currentBalance", CostPresenter.displayAmount(calculateCurrentBalance(monthly, quaterly, yearly)));
    
    return "fixedcosts";
  }
  
  private int calculateCurrentBalance(
      List<MonthlyFixedCost> monthly, 
      List<QuaterlyFixedCost> quaterly,
      List<YearlyFixedCost> yearly) {
    
    AtomicInteger sum = new AtomicInteger(0);
    
    YearMonth now = YearMonth.now();
    
    Predicate<FixedCost> appliable= cost -> cost.isActive(now);
    
    monthly.stream().filter(appliable).forEach(cost -> sum.addAndGet(cost.getAmount()));
    quaterly.stream().filter(appliable).forEach(cost -> sum.addAndGet(cost.getAmount() * 4 / 12));
    yearly.stream().filter(appliable).forEach(cost -> sum.addAndGet(cost.getAmount() / 12));
    
    return sum.get();
  }

  @GetMapping("/edit")
  public String editFixedCost(Model model, @RequestParam("type") String type, @RequestParam("id") Optional<Integer> optionalId,
      @RequestParam(value = "target", defaultValue="manage") String target) {
    
    CostType costType = CostType.valueOf(type.toUpperCase());
    
    CostPresenter<? extends Cost> presenter = optionalId
        .map(id -> retrieveExistingPresenter(costType, id))
        .orElse(costType.createInstance());

    presenter.setTargetAsString(target);
    
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

  @GetMapping("/monthly")
  public String postMonthlyFixedCost(Model model, RedirectAttributes redirectAttributes) {
    redirectAttributes.addAttribute("type", "monthly");
    return "redirect:/fixedcosts/edit";
  }
  
  @PostMapping("/monthly")
  public String postMonthlyFixedCost(Model model, 
      @Valid @ModelAttribute("model") MonthlyFixedCostPresenter presenter, 
      BindingResult bindingResult) {
    
    return processSaving(model, presenter, bindingResult, "monthly", 
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
    
    return processSaving(model, presenter, bindingResult, "quaterly", 
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
    
    return processSaving(model, presenter, bindingResult, "yearly", 
        (() -> repository.save(presenter.toPersistentObject(), presenter.getId())));
  }
  
  
  @GetMapping("/delete")
  public String deleteFixedCost(@RequestParam("id") int id, @RequestParam("type") String type,
      @RequestParam(value = "target", defaultValue="manage") String target) {
    
    switch (type) {
    case "monthly":
      repository.deleteMonthlyFixedCost(id); break;
    case "yearly":
      repository.deleteYearlyFixedCost(id); break;
    case "quterly":
      repository.deleteQuaterlyFixedCost(id); break;
    default:
      throw new IllegalArgumentException("type '" + type + "' is not supported for deletion");
    }
    
    return "redirect:/" + Target.getUri(target);
  }

}
