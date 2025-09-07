package com.sample.devicemanagement.dto;

import com.sample.devicemanagement.controller.validation.ValidDeviceState;
import com.sample.devicemanagement.domain.State;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;

import static com.sample.devicemanagement.util.DeviceConstants.DEVICE_ID_REGEX;
import static com.sample.devicemanagement.util.DeviceConstants.DEVICE_ID_REGEX_FAIL_MESSAGE;

@Value
@Builder
@Jacksonized
public class DeviceDto {

    @Pattern(regexp = DEVICE_ID_REGEX, message = DEVICE_ID_REGEX_FAIL_MESSAGE)
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

    Instant createdAt;

}
