package com.lalabrand.ecommerce.utils.annotation;

import com.lalabrand.ecommerce.utils.validator.RatingValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RatingValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Rating {
    String message() default "Rating is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
