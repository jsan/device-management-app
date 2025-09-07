package com.sample.devicemanagement.service;

import com.sample.devicemanagement.domain.State;
import com.sample.devicemanagement.dto.DeviceDto;
import com.sample.devicemanagement.dto.DeviceTableViewDto;
import com.sample.devicemanagement.repository.DeviceRepository;
import com.sample.devicemanagement.repository.entity.DeviceEntity;
import com.sample.devicemanagement.service.exception.DeviceAlreadyExistsException;
import com.sample.devicemanagement.service.exception.DeviceNotFoundException;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
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
    }

    @Test
    void createDevice_success_returnsDeviceDto() {
        when(deviceEntityMapper.toDeviceDto(savedDeviceEntity)).thenReturn(deviceDto);
        when(deviceRepository.save(any(DeviceEntity.class))).thenReturn(savedDeviceEntity);
        when(deviceEntityMapper.toDeviceEntity(deviceDto)).thenReturn(deviceEntity);

        DeviceDto createdDevice = deviceService.createDevice(deviceDto);

        assertNotNull(createdDevice);
        assertEquals("123", createdDevice.getDeviceId());
        assertEquals("Smartphone", createdDevice.getDeviceName());
        assertEquals("Samsung", createdDevice.getDeviceBrand());
    }

    @Test
    void createDevice_withDuplicateDevice_throwsDeviceAlreadyExistsException() {
        when(deviceRepository.save(any(DeviceEntity.class))).thenThrow(DataIntegrityViolationException.class);
        when(deviceEntityMapper.toDeviceEntity(deviceDto)).thenReturn(deviceEntity);

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

    @Test
    void getDeviceById_shouldReturnDeviceDto_whenDeviceExists() {
        when(deviceRepository.findDeviceByDeviceId("123")).thenReturn(Optional.of(deviceEntity));
        when(deviceEntityMapper.toDeviceDto(deviceEntity)).thenReturn(deviceDto);

        DeviceDto result = deviceService.getDeviceById("123");

        assertNotNull(result);
        assertEquals("123", result.getDeviceId());
        verify(deviceRepository).findDeviceByDeviceId("123");
        verify(deviceEntityMapper).toDeviceDto(deviceEntity);
    }

    @Test
    void getDeviceById_shouldThrowDeviceNotFoundException_whenDeviceDoesNotExist() {
        when(deviceRepository.findDeviceByDeviceId("XYZ999")).thenReturn(Optional.empty());

        DeviceNotFoundException ex = assertThrows(DeviceNotFoundException.class,
                () -> deviceService.getDeviceById("XYZ999"));

        assertEquals("Device with ID 'XYZ999' not found.", ex.getMessage());
    }

    @Test
    void getAllDevices_shouldReturnMappedDto_whenRepositoryReturnsPage() {
        Pageable pageable = mock(Pageable.class);
        Page<DeviceEntity> page = new PageImpl<>(List.of(deviceEntity));
        DeviceTableViewDto tableViewDto = DeviceTableViewDto.builder().build();

        when(deviceRepository.findAll(pageable)).thenReturn(page);
        when(deviceEntityMapper.toDeviceTableView(page)).thenReturn(tableViewDto);

        DeviceTableViewDto result = deviceService.getAllDevices(pageable);

        assertNotNull(result);
        assertSame(tableViewDto, result);
        verify(deviceRepository).findAll(pageable);
        verify(deviceEntityMapper).toDeviceTableView(page);
    }

    @Test
    void getAllDevices_shouldThrowPersistenceException_whenRepositoryFails() {
        Pageable pageable = mock(Pageable.class);
        when(deviceRepository.findAll(pageable)).thenThrow(new RuntimeException("DB down"));

        PersistenceException ex = assertThrows(PersistenceException.class,
                () -> deviceService.getAllDevices(pageable));

        assertTrue(ex.getCause() instanceof RuntimeException);
        assertEquals("DB down", ex.getCause().getMessage());
    }

    @Test
    void getDevicesByBrandAndState_shouldReturnMappedDto_whenRepositoryReturnsPage() {
        Pageable pageable = mock(Pageable.class);
        String brand = "BrandX";
        State state = State.AVAILABLE;

        Page<DeviceEntity> page = new PageImpl<>(List.of(deviceEntity));
        DeviceTableViewDto tableViewDto = DeviceTableViewDto.builder().build();

        when(deviceRepository.findByBrandAndState(brand, state, pageable)).thenReturn(page);
        when(deviceEntityMapper.toDeviceTableView(page)).thenReturn(tableViewDto);

        DeviceTableViewDto result = deviceService.getDevicesByBrandAndState(pageable, brand, state);

        assertNotNull(result);
        assertSame(tableViewDto, result);
        verify(deviceRepository).findByBrandAndState(brand, state, pageable);
        verify(deviceEntityMapper).toDeviceTableView(page);
    }

    @Test
    void getDevicesByBrandAndState_shouldThrowPersistenceException_whenRepositoryFails() {
        Pageable pageable = mock(Pageable.class);
        String brand = "BrandX";
        State state = State.AVAILABLE;

        when(deviceRepository.findByBrandAndState(brand, state, pageable))
                .thenThrow(new RuntimeException("DB down"));

        PersistenceException ex = assertThrows(PersistenceException.class,
                () -> deviceService.getDevicesByBrandAndState(pageable, brand, state));

        assertTrue(ex.getCause() instanceof RuntimeException);
        assertEquals("DB down", ex.getCause().getMessage());
    }

}

