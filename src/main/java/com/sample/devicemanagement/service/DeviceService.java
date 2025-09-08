package com.sample.devicemanagement.service;

import com.sample.devicemanagement.domain.State;
import com.sample.devicemanagement.dto.DeviceDto;
import com.sample.devicemanagement.dto.DeviceTableViewDto;
import com.sample.devicemanagement.dto.DeviceUpdateDto;
import org.springframework.data.domain.Pageable;

public interface DeviceService {

    DeviceDto createDevice(DeviceDto device);
    DeviceDto getDeviceById(String deviceId);
    DeviceDto updateDeviceById(String deviceId, DeviceUpdateDto device);
    DeviceTableViewDto getAllDevices(Pageable paging);
    DeviceTableViewDto getDevicesByBrandAndState(Pageable paging, String brand, State state);
    void deleteDevice(String deviceId);

}
