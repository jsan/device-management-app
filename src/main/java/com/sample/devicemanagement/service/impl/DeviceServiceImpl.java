package com.sample.devicemanagement.service.impl;

import com.sample.devicemanagement.dto.DeviceDto;
import com.sample.devicemanagement.dto.DeviceTableViewDto;
import com.sample.devicemanagement.repository.DeviceRepository;
import com.sample.devicemanagement.repository.entity.DeviceEntity;
import com.sample.devicemanagement.service.DeviceService;
import com.sample.devicemanagement.service.exception.DeviceAlreadyExistsException;
import com.sample.devicemanagement.service.exception.DeviceNotFoundException;
import com.sample.devicemanagement.service.mapper.DeviceEntityMapper;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private DeviceRepository deviceRepository;
    private DeviceEntityMapper deviceEntityMapper;

    @Override
    public DeviceDto createDevice(DeviceDto deviceDto) {
        DeviceEntity deviceEntity = deviceEntityMapper.toDeviceEntity(deviceDto);

        try {
            DeviceEntity savedDevice = deviceRepository.save(deviceEntity);
            return deviceEntityMapper.toDeviceDto(savedDevice);
        } catch (DataIntegrityViolationException e) {
            log.info("Device with ID '{}' name '{}' and brand '{}' already exists", deviceDto.getDeviceId(), deviceDto.getDeviceName(), deviceDto.getDeviceBrand());
            throw new DeviceAlreadyExistsException(deviceDto.getDeviceId(), deviceDto.getDeviceName(), deviceDto.getDeviceBrand());
        } catch (Exception e) {
            log.error("Unable to save Device data to persistence:", e);
            throw new PersistenceException(e);
        }
    }

    @Override
    public DeviceDto getDeviceById(String deviceId) {
        return deviceRepository.findDeviceByDeviceId(deviceId)
                .map(deviceEntityMapper::toDeviceDto)
                .orElseThrow(() -> new DeviceNotFoundException(deviceId));
    }

    @Override
    public DeviceTableViewDto getAllDevices(Pageable paging) {
        try {
            Page<DeviceEntity> pageResult = deviceRepository.findAll(paging);
            return deviceEntityMapper.toDeviceTableView(pageResult);
        } catch (Exception e) {
            log.error("Unable to read data from persistence:", e);
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<DeviceDto> getAllDevicesByBrand(String brand) {
        return List.of();
    }

    @Override
    public List<DeviceDto> getAllDevicesByState(String state) {
        return List.of();
    }

    @Override
    public DeviceDto updateDevice(DeviceDto deviceDto) {
        return null;
    }

    @Override
    public void deleteDevice(Long id) {

    }
}
