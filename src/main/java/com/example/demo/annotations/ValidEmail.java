package com.example.demo.annotations;

import com.example.demo.validations.EmailValiator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValiator.class)
@Documented
public @interface ValidEmail {
    String message() default "Invali Email";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
