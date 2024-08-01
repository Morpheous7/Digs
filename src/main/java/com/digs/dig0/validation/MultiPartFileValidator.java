package com.digs.dig0.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

class MultiPartFileValidator implements ConstraintValidator<ValidMediaType, MultipartFile> {

    private String allowed;

    public void initialize(ValidMediaType constraintAnnotation) {
        allowed = constraintAnnotation.value();
    }

    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        return value == null || allowed.equals(MediaType.ALL_VALUE) || allowed.equals(value.getContentType());
    }
}