package com.sample.devicemanagement.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;
import java.util.List;

@Value
@Builder
@Jacksonized
public class DeviceTableViewDto {

    Pagination pagination;
    List<DeviceDataEntry> data;

    @Value
    @Builder
    @Jacksonized
    public static class DeviceDataEntry {

        String deviceId;
        String deviceName;
        String deviceBrand;
        String deviceState;
        Instant createdAt;
    }

}
