package com.WellSpace.utils.validations;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = MaiorDe18Validator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface MaiorDe18 {

    String message() default "O usuário deve ter pelo menos 18 anos";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {}; //  extras
}
