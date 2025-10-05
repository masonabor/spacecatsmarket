package com.edu.web.spacecatsmarket.web.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CosmicWordValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CosmicWordCheck {
    String message() default "Name shoud contains words: (star, galaxy, comet)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
