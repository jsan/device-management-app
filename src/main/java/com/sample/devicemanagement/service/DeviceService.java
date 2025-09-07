package com.sample.devicemanagement.service;

import com.sample.devicemanagement.dto.DeviceDto;
import com.sample.devicemanagement.dto.DeviceTableViewDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DeviceService {

    DeviceDto createDevice(DeviceDto device);
    DeviceDto getDeviceById(Long deviceId);
    DeviceTableViewDto getAllDevices(Pageable paging);
    List<DeviceDto> getAllDevicesByBrand(String brand);
    List<DeviceDto> getAllDevicesByState(String state);
    DeviceDto updateDevice(DeviceDto deviceDto);
    void deleteDevice(Long id);

}
