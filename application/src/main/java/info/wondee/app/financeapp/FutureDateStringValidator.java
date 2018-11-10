package info.wondee.app.financeapp;

import java.time.YearMonth;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FutureDateStringValidator implements ConstraintValidator<FutureDateString, String>{

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    
    // weird... actually value should not be passed in here if null...
    if (value == null) return true;
    
    return !YearMonth.now().isAfter(DisplayUtil.parse(value));
  }

}
