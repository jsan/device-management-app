package com.sample.devicemanagement.repository.entity;

import com.sample.devicemanagement.domain.State;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "devices",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_device_id", columnNames = "deviceId")},
        indexes = {
                @Index(name = "idx_device_brand", columnList = "deviceBrand"),
                @Index(name = "idx_device_state", columnList = "deviceState")
        })
public class DeviceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1, max = 6)
    @Column(nullable = false)
    private String deviceId;

    @Column(nullable = false)
    private String deviceName;

    @Column(nullable = false)
    private String deviceBrand;

    @Enumerated(EnumType.STRING)
    private State deviceState;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

}