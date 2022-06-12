package com.example.warehouse.validation.anotation;

import com.example.warehouse.anotations.Unique;
import com.example.warehouse.enums.FieldType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueValidator implements ConstraintValidator<Unique, String> {

    private FieldType type;

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.type = constraintAnnotation.fieldType();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return switch (type) {
            case USERNAME -> checkUsername(value);
            case PHONE -> checkPhoneNumber(value);
            case EMAIL -> checkEmail();
            case DEFAULT -> true;
        };
    }

    private boolean checkEmail() {
        return false;
    }

    private boolean checkPhoneNumber(String value) {
        return false;
    }

    private boolean checkUsername(String value) {
        return false;
    }
}
