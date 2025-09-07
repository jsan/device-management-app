package com.sample.devicemanagement.service;

import com.sample.devicemanagement.domain.State;
import com.sample.devicemanagement.dto.DeviceDto;
import com.sample.devicemanagement.dto.DeviceTableViewDto;
import org.springframework.data.domain.Pageable;

public interface DeviceService {

    DeviceDto createDevice(DeviceDto device);
    DeviceDto getDeviceById(String deviceId);
    DeviceTableViewDto getAllDevices(Pageable paging);
    DeviceTableViewDto getDevicesByBrandAndState(Pageable paging, String brand, State state);
    void deleteDevice(Long id);

}
