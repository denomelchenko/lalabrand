package com.lalabrand.ecommerce.utils.validator;

import com.lalabrand.ecommerce.utils.annotation.Rating;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class RatingValidator implements ConstraintValidator<Rating, BigDecimal> {

    @Override
    public void initialize(Rating constraintAnnotation) {
    }

    @Override
    public boolean isValid(BigDecimal rating, ConstraintValidatorContext context) {
        float floatValue = rating.floatValue();
        return !(floatValue < 1) && !(floatValue > 5)
                && !rating.toString().matches("^[0-9]+(\\.[0-9])?$");
    }
}