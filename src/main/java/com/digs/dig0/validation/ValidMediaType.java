package com.digs.dig0.validation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.springframework.http.MediaType;

@Documented
@Constraint(validatedBy = { MultiPartFileValidator.class, MultiPartPartValidator.class })
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMediaType {

    String value() default MediaType.ALL_VALUE;

    String message() default "{javax.validation.constraints.ValidMediaType.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}