package info.wondee.app.financeapp.fixedcosts;

import static info.wondee.app.financeapp.DisplayUtil.processSaving;

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
import info.wondee.app.financeapp.financedata.FinanceData;
import info.wondee.app.financeapp.financedata.FinanceDataRepository;
import info.wondee.app.financeapp.user.UserService;

@Controller
@RequestMapping("/fixedcosts")
public class FixedCostController {

  @Autowired
  private UserService userService;
  
  @Autowired
  private FinanceDataRepository financeDataRepository;
  
  @GetMapping
  public String getFixedCosts(Model model) {

    FinanceData financeData = userService.findFinanceData();
    
    List<MonthlyFixedCost> monthly = financeData.getMonthlyFixedCosts();
    List<QuaterlyFixedCost> quaterly = financeData.getQuaterlyFixedCosts();
    List<YearlyFixedCost> yearly = financeData.getYearlyFixedCosts();

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
    FinanceData financeData = userService.findFinanceData();
    
    switch (costType) {
    case MONTHLY:
      return financeData.getMonthlyFixedCosts();
    case YEARLY:
      return financeData.getYearlyFixedCosts();
    case QUATERLY:
      return financeData.getQuaterlyFixedCosts();
    case SPECIAL:
      return financeData.getSpecialCosts();
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
        (() -> financeDataRepository.save(userService.findFinanceData(), presenter.toPersistentObject(), presenter.getId())));
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
        (() -> financeDataRepository.save(userService.findFinanceData(), presenter.toPersistentObject(), presenter.getId())));
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
        (() -> financeDataRepository.save(userService.findFinanceData(), presenter.toPersistentObject(), presenter.getId())));
  }
  
  
  @GetMapping("/delete")
  public String deleteFixedCost(@RequestParam("id") int id, @RequestParam("type") String type,
      @RequestParam(value = "target", defaultValue="manage") String target) {
    
    FinanceData financeData = userService.findFinanceData();
    
    switch (type) {
    case "monthly":
      financeDataRepository.deleteMonthlyFixedCost(financeData, id); break;
    case "yearly":
      financeDataRepository.deleteYearlyFixedCost(financeData, id); break;
    case "quaterly":
      financeDataRepository.deleteQuaterlyFixedCost(financeData, id); break;
    default:
      throw new IllegalArgumentException("type '" + type + "' is not supported for deletion");
    }
    
    return "redirect:/" + Target.getUri(target);
  }

}
