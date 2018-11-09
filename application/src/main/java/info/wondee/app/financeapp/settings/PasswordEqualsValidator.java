package info.wondee.app.financeapp.settings;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordEqualsValidator implements ConstraintValidator<PasswordEquals, UserPresenter>{

  @Override
  public boolean isValid(UserPresenter value, ConstraintValidatorContext context) {

    String password = value.getPassword();
    
    if (password == null) {
      return false;
    }
    
    return password.equals(value.getPasswordRepeat());
  }

}
