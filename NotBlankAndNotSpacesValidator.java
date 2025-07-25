package com.banketa.banketa.Custom;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotBlankAndNotSpacesValidator implements ConstraintValidator<NotBlankAndNotSpaces, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !value.trim().isEmpty();
    }
}