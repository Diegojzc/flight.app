package com.tokioschool.flight.app.core.validator;

import com.mysql.cj.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class EnumImpl implements ConstraintValidator<EnumValid,String> {
    private List<String> entries;
    private boolean required;

    @Override
    public void initialize(EnumValid constraintAnnotation) {
        Enum<?>[] enumConstants= constraintAnnotation.target().getEnumConstants();
        entries= Arrays.stream(enumConstants).map(Enum::toString).toList();
        required=constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        String trimmed = StringUtils.safeTrim(s);

        return !required && trimmed==null  || trimmed != null && entries.contains(trimmed);

    }
}
