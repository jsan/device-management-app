package com.sample.devicemanagement.repository;

import com.sample.devicemanagement.domain.State;
import com.sample.devicemanagement.repository.entity.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {

    Optional<DeviceEntity> findDeviceByDeviceId(String deviceId);

    Optional<DeviceEntity> findDeviceByDeviceBrand(String brand);

    Optional<DeviceEntity> findDeviceByDeviceState(State deviceState);

}
