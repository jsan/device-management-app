package com.sample.devicemanagement.controller.validation;

import com.sample.devicemanagement.domain.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

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

        List<String> allowedValues = Arrays.stream(enumClass.getEnumConstants())
                .map(e -> ((State) e).getText())
                .toList();

        boolean valid = allowedValues.stream()
                .anyMatch(text -> text.equalsIgnoreCase(value));

        if (!valid) {
            context.disableDefaultConstraintViolation();
            String message = "Device state is not supported. Supported values: " + String.join(", ", allowedValues);
            context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
        }

        return valid;
    }

}
