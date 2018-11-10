package info.wondee.app.financeapp.fixedcosts;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import info.wondee.app.financeapp.DisplayUtil;

public class DateRangeValidator implements ConstraintValidator<DateRange, FixedCostPresenter<?>> {

  @Override
  public boolean isValid(FixedCostPresenter<?> value, ConstraintValidatorContext context) {
    
    if (value.getFromYearMonth() == null || value.getToYearMonth() == null) {
      return true;
    }
    
    return DisplayUtil.parse(value.getFromYearMonth())
        .isBefore(DisplayUtil.parse(value.getToYearMonth()));
  }

}
