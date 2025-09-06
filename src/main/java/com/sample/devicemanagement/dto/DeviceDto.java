package com.sample.devicemanagement.dto;

import com.sample.devicemanagement.domain.State;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class DeviceDto {

    Integer deviceId;
    String name;
    String brand;
    State state;

}
