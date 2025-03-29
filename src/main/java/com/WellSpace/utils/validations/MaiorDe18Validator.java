package com.WellSpace.utils.validations;

import com.WellSpace.utils.validations.MaiorDe18;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class MaiorDe18Validator implements ConstraintValidator<MaiorDe18, LocalDate> {

    @Override
    public void initialize(MaiorDe18 constraintAnnotation) {

    }

    @Override
    public boolean isValid(LocalDate dataNascimento, ConstraintValidatorContext context) {
        if (dataNascimento == null) {
            return true;
        }
        LocalDate dataAtual = LocalDate.now();
        int idade = Period.between(dataNascimento, dataAtual).getYears();
        return idade >= 18;
    }
}
