package info.wondee.app.financeapp.specialcosts;

import static info.wondee.app.financeapp.DisplayUtil.processSaving;

import java.util.List;

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
import info.wondee.app.financeapp.financedata.FinanceDataRepository;
import info.wondee.app.financeapp.user.UserService;

@Deprecated
@Controller
@RequestMapping({"/specialcosts", "/fixedcosts/special"})
public class SpecialCostController {

  @Autowired
  private UserService service;
  
  @Autowired
  private FinanceDataRepository repository;
  
  @GetMapping
  public String getSpecialCosts(Model model) {
    
    List<SpecialCost> all = service.findFinanceData().getSpecialCosts();
    
    model.addAttribute("specialCosts", DisplayUtil.toPresenter(all));
    
    return "specialcosts";
  }
  
  @GetMapping("/add")
  public String addSpecialCosts(Model model,
      @RequestParam(value = "target", defaultValue="special") String target,
      @RequestParam("month") int month, 
      @RequestParam("year") int year) {
    
    SpecialCostPresenter presenter = new SpecialCostPresenter();
    
    presenter.setDueYearMonth(month, year);

    presenter.setTargetAsString(target);
    
    model.addAttribute("type", presenter.getType());
    model.addAttribute("model", presenter);
    
    return "fixedcostform";
  }
  
  @PostMapping
  public String postSpecialCosts(@Valid @ModelAttribute("model") SpecialCostPresenter presenter, 
      BindingResult bindingResult, RedirectAttributes attr) {
    
    return processSaving(attr, presenter, bindingResult, "special", 
        (() -> repository.save(service.findFinanceData(), presenter.toPersistentObject(), presenter.getId())));
  }
  
  @GetMapping("/delete")
  public String deleteSpecialCost(
      @RequestParam(value = "target", defaultValue="special") String target, 
      @RequestParam("id") int id) {
    
    repository.deleteSpecialCost(service.findFinanceData(), id);
    return "redirect:/" + Target.getUri(target);
  }
  
}
