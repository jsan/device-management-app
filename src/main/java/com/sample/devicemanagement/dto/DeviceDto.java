package com.sample.devicemanagement.dto;

import com.sample.devicemanagement.controller.validation.ValidDeviceState;
import com.sample.devicemanagement.domain.State;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class DeviceDto {

    @Pattern(regexp = "^[A-Z0-9]+$", message = "Device ID must contain only uppercase letters and numbers")
    @Size(min = 1, max = 6)
    @NotNull(message = "Device ID is mandatory, can't be empty")
    String deviceId;

    @NotNull(message = "Name is mandatory, can't be empty")
    String deviceName;

    @NotNull(message = "Brand is mandatory, can't be empty")
    String deviceBrand;

    @ValidDeviceState(enumClass = State.class)
    @NotNull(message = "State is mandatory, can't be empty")
    String deviceState;

}
