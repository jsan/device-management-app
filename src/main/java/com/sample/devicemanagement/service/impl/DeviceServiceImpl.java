package com.sample.devicemanagement.service.impl;

import com.sample.devicemanagement.domain.State;
import com.sample.devicemanagement.dto.DeviceDto;
import com.sample.devicemanagement.dto.DeviceTableViewDto;
import com.sample.devicemanagement.dto.DeviceUpdateDto;
import com.sample.devicemanagement.repository.DeviceRepository;
import com.sample.devicemanagement.repository.entity.DeviceEntity;
import com.sample.devicemanagement.service.DeviceService;
import com.sample.devicemanagement.service.exception.DeviceAlreadyExistsException;
import com.sample.devicemanagement.service.exception.DeviceInUseException;
import com.sample.devicemanagement.service.exception.DeviceNotFoundException;
import com.sample.devicemanagement.service.mapper.DeviceEntityMapper;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sample.devicemanagement.domain.State.IN_USE;

@Slf4j
@Service
@AllArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private DeviceRepository deviceRepository;
    private DeviceEntityMapper deviceEntityMapper;

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
    public DeviceDto getDeviceById(String deviceId) {
        return deviceRepository.findDeviceByDeviceId(deviceId)
                .map(deviceEntityMapper::toDeviceDto)
                .orElseThrow(() -> new DeviceNotFoundException(deviceId));
    }

    @Override
    public DeviceTableViewDto getDevicesByBrandAndState(Pageable paging, String deviceBrand, State deviceState) {
        try {
            Page<DeviceEntity> pageResult = deviceRepository.findByBrandAndState(deviceBrand, deviceState, paging);
            return deviceEntityMapper.toDeviceTableView(pageResult);
        } catch (Exception e) {
            log.error("Unable to read data from persistence:", e);
            throw new PersistenceException(e);
        }
    }

    @Transactional
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

    @Transactional
    @Override
    public DeviceDto updateDeviceById(String deviceId, DeviceUpdateDto deviceUpdateDto) {
        DeviceEntity deviceEntity = deviceRepository.findDeviceByDeviceId(deviceId)
                .map(entity -> {
                    if (isUpdateNotAllowed(deviceUpdateDto, entity)) {
                        throw new DeviceInUseException(deviceId);
                    }
                    return deviceEntityMapper.mergeDeviceEntity(deviceUpdateDto, entity);
                }).orElseThrow(() -> new DeviceNotFoundException(deviceId));

        return deviceEntityMapper.toDeviceDto(deviceRepository.save(deviceEntity));
    }

    @Transactional
    @Override
    public void deleteDevice(Long id) {

    }

    private static boolean isUpdateNotAllowed(DeviceUpdateDto deviceUpdateDto, DeviceEntity entity) {
        return (entity.getDeviceState() == IN_USE &&
                (deviceUpdateDto.getDeviceBrand() != null && !deviceUpdateDto.getDeviceBrand().isBlank() ||
                        deviceUpdateDto.getDeviceName() != null && !deviceUpdateDto.getDeviceName().isBlank()));
    }
}
