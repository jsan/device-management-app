package com.sample.devicemanagement.repository;

import com.sample.devicemanagement.domain.State;
import com.sample.devicemanagement.repository.entity.DeviceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {

    Optional<DeviceEntity> findDeviceByDeviceId(String deviceId);

    @Query("SELECT d FROM DeviceEntity d WHERE (:brand IS NULL OR d.deviceBrand = :brand) AND (:state IS NULL OR d.deviceState = :state)")
    Page<DeviceEntity> findByBrandAndState(
            @Param("brand") String brand,
            @Param("state") State state,
            Pageable pageable);

}
