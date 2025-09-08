package com.sample.devicemanagement.dto;

import com.sample.devicemanagement.controller.validation.ValidDeviceState;
import com.sample.devicemanagement.domain.State;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class DeviceUpdateDto {

    String deviceName;
    String deviceBrand;
    @ValidDeviceState(enumClass = State.class)
    String deviceState;

}
