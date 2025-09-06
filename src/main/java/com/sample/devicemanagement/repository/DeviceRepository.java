package com.sample.devicemanagement.repository;

import com.sample.devicemanagement.repository.entity.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {

    Optional<DeviceEntity> findDeviceByBrand(String brand);
    Optional<DeviceEntity> findDeviceByState(String state);

}
