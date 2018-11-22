package info.wondee.app.financeapp.settings;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import info.wondee.app.financeapp.BusinessException;
import info.wondee.app.financeapp.JsonResponse;
import info.wondee.app.financeapp.Message.Severity;
import info.wondee.app.financeapp.user.UserService;

@Controller
@RequestMapping("/settings")
public class SettingsController {

  @Autowired
  private UserService userService;
  
  @PostMapping(value="/user")
  public ResponseEntity<JsonResponse> addNewUser(
      @Valid @RequestBody NewUserPresenter presenter, 
      BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      return new ResponseEntity<JsonResponse>(
          new JsonResponse(Severity.ERROR, "Validierungsfehler!"), HttpStatus.BAD_REQUEST);
    }
    
    userService.createUser(presenter.getName(), presenter.getPassword());
    
    return 
        new ResponseEntity<JsonResponse>(new JsonResponse(
              Severity.INFO, String.format("Nutzer '%s' wurde erfolgreich hinzugefügt", presenter.getName())
          ), HttpStatus.CREATED);
      
    
  }
  
  @PostMapping(value="/password")
  public ResponseEntity<JsonResponse> changePassword(
      @Valid @RequestBody ChangePasswordPresenter presenter, 
      BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      return new ResponseEntity<JsonResponse>(
          new JsonResponse(Severity.ERROR, "Validierungsfehler!"), HttpStatus.BAD_REQUEST);
    }
    
    userService.changePassword(presenter.getOldPassword(), presenter.getNewPassword());
    
    return 
        new ResponseEntity<JsonResponse>(new JsonResponse(
              Severity.INFO, "Passwort wurde erfolgreich geändert"), HttpStatus.CREATED);
      
    
  }
  
  
  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<JsonResponse> handleBusinessError(BusinessException e) {
    return new ResponseEntity<JsonResponse>(new JsonResponse(
        Severity.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
  }
  
}
