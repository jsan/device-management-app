package com.sample.devicemanagement.controller.validation;

import com.sample.devicemanagement.domain.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DeviceStateValidator implements ConstraintValidator<ValidDeviceState, String> {

    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(ValidDeviceState annotation) {
        this.enumClass = annotation.enumClass();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return Arrays.stream(enumClass.getEnumConstants())
                .map(e -> ((State) e).getText())
                .anyMatch(text -> text.equalsIgnoreCase(value));
    }

}
