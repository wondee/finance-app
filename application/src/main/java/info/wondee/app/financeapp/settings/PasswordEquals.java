package info.wondee.app.financeapp.settings;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = PasswordEqualsValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordEquals {

  String message() default "{info.wondee.app.financeapp.settings.PasswordEquals.message}";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };
  
}
