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

import info.wondee.app.financeapp.DisplayUtil;
import info.wondee.app.financeapp.user.FinanceUserRepository;

@Controller
@RequestMapping({"/specialcosts", "/fixedcosts/special"})
public class SpecialCostController {

  @Autowired
  private FinanceUserRepository repository;
  
  @GetMapping
  public String getSpecialCosts(Model model) {
    
    List<SpecialCost> all = repository.findSpecialCosts();
    
    model.addAttribute("specialCosts", DisplayUtil.toPresenter(all));
    
    return "specialcosts";
  }
  
  @GetMapping("/add")
  public String addSpecialCosts(Model model, @RequestParam("month") int month, @RequestParam("year") int year) {
    
    SpecialCostPresenter presenter = new SpecialCostPresenter();
    
    presenter.setDueMonth(month);
    presenter.setDueYear(year);
    
    model.addAttribute("type", presenter.getType());
    model.addAttribute("model", presenter);
    
    return "fixedcostform";
  }
  
  @PostMapping
  public String postSpecialCosts(Model model, 
      @Valid @ModelAttribute("model") SpecialCostPresenter presenter, 
      BindingResult bindingResult) {
    
    return processSaving(model, presenter, bindingResult, "special", "specialcosts",
        (() -> repository.save(presenter.toPersistentObject(), presenter.getId())));
  }
  
  @GetMapping("/delete")
  public String deleteSpecialCost(@RequestParam("id") int id) {
    repository.deleteSpecialCost(id);
    return "redirect:/specialcosts";
  }
  
}
