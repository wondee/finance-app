package info.wondee.app.financeapp.specialcosts;

import java.util.List;

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
  
  @PostMapping
  public String postSpecialCosts(@ModelAttribute SpecialCostPresenter presenter) {
    repository.save(presenter.toPersistentObject(), presenter.getId());
     
    return "redirect:/specialcosts";
  }
  
  @GetMapping("/delete")
  public String deleteSpecialCost(@RequestParam("id") int id) {
    repository.deleteSpecialCost(id);
    return "redirect:/specialcosts";
  }
  
}
