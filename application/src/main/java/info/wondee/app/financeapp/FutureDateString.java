package info.wondee.app.financeapp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = FutureDateStringValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FutureDateString {

  String message() default "{info.wondee.app.financeapp.FurtureDateString.message}";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };
}
