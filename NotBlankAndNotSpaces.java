package com.banketa.banketa.Custom;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotBlankAndNotSpacesValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlankAndNotSpaces {
    String message() default "Field must not be empty or only spaces";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
