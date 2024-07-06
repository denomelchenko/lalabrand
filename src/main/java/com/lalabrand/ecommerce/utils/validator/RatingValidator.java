package com.lalabrand.ecommerce.utils.validator;

import com.lalabrand.ecommerce.utils.annotation.Rating;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RatingValidator implements ConstraintValidator<Rating, Float> {

    @Override
    public void initialize(Rating constraintAnnotation) {
    }

    @Override
    public boolean isValid(Float rating, ConstraintValidatorContext context) {
        return rating >= 0 && rating <= 5
                && rating.toString().matches("^[0-5]+(\\.[0-9])?$");
    }
}