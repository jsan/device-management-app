package com.sample.devicemanagement.controller.validation;

import com.sample.devicemanagement.domain.State;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = DeviceStateValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface ValidDeviceState {

    String message() default "Device state is not supported";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<State> enumClass();

}
