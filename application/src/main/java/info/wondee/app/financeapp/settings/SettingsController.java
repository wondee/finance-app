package info.wondee.app.financeapp.settings;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import info.wondee.app.financeapp.BusinessException;
import info.wondee.app.financeapp.user.UserService;

@Controller
@RequestMapping("/settings")
public class SettingsController {

  @Autowired
  private UserService userService;
  
  @GetMapping
  public String initSettings(Model model) {
    
    model.addAttribute("newUserModel", new UserPresenter());
    
    return "settings";
  }
  
  
  @PostMapping("/user")
  public String editFixedCost(Model model, 
      @Valid @ModelAttribute("newUserModel") UserPresenter presenter, 
      BindingResult bindingResult, 
      RedirectAttributes redirectAttributes) {

    if (bindingResult.hasErrors()) {
      model.addAttribute("newUserModel", presenter);
      return "settings";
    }
    
    try {
      userService.createUser(presenter.getName(), presenter.getPassword());
      redirectAttributes.addFlashAttribute("addUserSuccessMsg", String.format("Nutzer '%s' wurde erfolgreich hinzugefügt", presenter.getName()));
      
      return "redirect:/settings";
    } catch (BusinessException e) {
      bindingResult.addError(new ObjectError("businessError", e.getMessage()));
      model.addAttribute("newUserModel", presenter);
      
      return "settings";
    }
    
    
    
  }
  
}
