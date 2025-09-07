package com.sample.devicemanagement.service;

import com.sample.devicemanagement.dto.DeviceDto;
import com.sample.devicemanagement.repository.DeviceRepository;
import com.sample.devicemanagement.repository.entity.DeviceEntity;
import com.sample.devicemanagement.service.exception.DeviceAlreadyExistsException;
import com.sample.devicemanagement.service.impl.DeviceServiceImpl;
import com.sample.devicemanagement.service.mapper.DeviceEntityMapper;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
@ExtendWith(MockitoExtension.class)
class DeviceServiceTest {

    private DeviceDto deviceDto;
    private DeviceEntity deviceEntity;
    private DeviceEntity savedDeviceEntity;

    @InjectMocks
    private DeviceServiceImpl deviceService;
    @Mock
    private DeviceRepository deviceRepository;
    @Mock
    private DeviceEntityMapper deviceEntityMapper;

    @BeforeEach
    void setUp() {
        deviceDto = DeviceDto.builder()
                .deviceId("123")
                .deviceName("Smartphone")
                .deviceBrand("Samsung")
                .build();
        deviceEntity = DeviceEntity.builder()
                .deviceId("123")
                .deviceName("Smartphone")
                .deviceBrand("Samsung")
                .build();
        savedDeviceEntity = DeviceEntity.builder()
                .deviceId("123")
                .deviceName("Smartphone")
                .deviceBrand("Samsung")
                .build();
        when(deviceEntityMapper.toDeviceEntity(deviceDto)).thenReturn(deviceEntity);
    }

    @Test
    void createDevice_success_returnsDeviceDto() {
        when(deviceEntityMapper.toDeviceDto(savedDeviceEntity)).thenReturn(deviceDto);
        when(deviceRepository.save(any(DeviceEntity.class))).thenReturn(savedDeviceEntity);

        DeviceDto createdDevice = deviceService.createDevice(deviceDto);

        assertNotNull(createdDevice);
        assertEquals("123", createdDevice.getDeviceId());
        assertEquals("Smartphone", createdDevice.getDeviceName());
        assertEquals("Samsung", createdDevice.getDeviceBrand());
    }

    @Test
    void createDevice_withDuplicateDevice_throwsDeviceAlreadyExistsException() {
        when(deviceRepository.save(any(DeviceEntity.class)))
                .thenThrow(DataIntegrityViolationException.class);

        DeviceAlreadyExistsException thrown = assertThrows(
                DeviceAlreadyExistsException.class,
                () -> deviceService.createDevice(deviceDto)
        );

        String expectedMessage = String.format("Device with ID '%s', name '%s', and brand '%s' already exists.",
                deviceDto.getDeviceId(), deviceDto.getDeviceName(), deviceDto.getDeviceBrand());
        assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    void createDevice_withPersistenceError_throwsPersistenceException() {
        when(deviceRepository.save(any(DeviceEntity.class)))
                .thenThrow(new RuntimeException("Simulated database error"));

        assertThrows(
                PersistenceException.class,
                () -> deviceService.createDevice(deviceDto)
        );
    }

}
