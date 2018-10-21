package info.wondee.app.financeapp.fixedcosts;

import java.time.YearMonth;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<DateRange, FixedCostPresenter<?>> {

  @Override
  public boolean isValid(FixedCostPresenter<?> value, ConstraintValidatorContext context) {
    
    if (value.getFromYear() == null || value.getToYear() == null) {
      return true;
    }
    
    return YearMonth.of(value.getFromYear(), value.getFromMonth())
        .isBefore(YearMonth.of(value.getToYear(), value.getToMonth()));
  }

}
