package com.sample.devicemanagement.service;

import com.sample.devicemanagement.dto.DeviceDto;

import java.util.List;

public interface DeviceService {

    DeviceDto createDevice(DeviceDto device);
    DeviceDto getDeviceById(Long deviceId);
    List<DeviceDto> getAllDevices();
    List<DeviceDto> getAllDevicesByBrand(String brand);
    List<DeviceDto> getAllDevicesByState(String state);
    DeviceDto updateDevice(DeviceDto deviceDto);
    void deleteDevice(Long id);

}
