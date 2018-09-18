package info.wondee.app.financeapp.specialcosts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/specialcosts")
public class SpecialCostController {

  @Autowired
  private SpecialCostRepository repository;
  
  @GetMapping
  public String getSpecialCosts(Model model) {
    
    List<SpecialCost> all = repository.findAllSpecialCosts();
    
    model.addAttribute("specialCosts", all);
    
    return "specialcosts";
  }
  
}
