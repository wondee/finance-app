package info.wondee.app.financeapp.fixedcosts;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = DateRangeValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DateRange {

  String message() default "{info.wondee.app.financeapp.fixedcosts.DateRange.message}";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };
}
