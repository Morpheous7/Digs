package com.digs.dig0.validation;


import jakarta.servlet.http.Part;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.http.MediaType;

class MultiPartPartValidator implements ConstraintValidator<ValidMediaType, Part> {
    private String allowed;

    /**
     * Implements the validation logic.
     * The state of {@code value} must not be altered.
     * <p>
     * This method can be accessed concurrently, thread-safety must be ensured
     * by the implementation.
     *
     * @param value   object to validate
     * @param context context in which the constraint is evaluated
     * @return {@code false} if {@code value} does not pass the constraint
     */
    @Override
    public boolean isValid(Part value, ConstraintValidatorContext context) {
        return value == null  || allowed.equals(MediaType.ALL_VALUE)  || allowed.equals(value.getContentType());
    }

    @Override
    public void initialize(ValidMediaType constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}