package com.sample.devicemanagement.controller;

import com.sample.devicemanagement.controller.validation.ValidDeviceState;
import com.sample.devicemanagement.domain.State;
import com.sample.devicemanagement.dto.DeviceDto;
import com.sample.devicemanagement.dto.DeviceTableViewDto;
import com.sample.devicemanagement.dto.DeviceUpdateDto;
import com.sample.devicemanagement.repository.entity.DeviceEntity;
import com.sample.devicemanagement.service.DeviceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import static com.sample.devicemanagement.util.DeviceConstants.DEVICE_ID_REGEX;
import static com.sample.devicemanagement.util.DeviceConstants.DEVICE_ID_REGEX_FAIL_MESSAGE;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("api/v1/devices")
public class DeviceController {

    private final DeviceService deviceService;

    @GetMapping("/all")
    public ResponseEntity<DeviceTableViewDto> listAllDevices(@PageableDefault(size = 5)
                                                             @SortDefault(sort = "deviceId", direction = DESC) @ParameterObject Pageable paging) {
        log.info("Listing all devices");
        return ResponseEntity.ok(deviceService.getAllDevices(paging));
    }

    @GetMapping("/{deviceId}")
    public ResponseEntity<DeviceDto> getDeviceById(
            @PathVariable("deviceId") @Pattern(regexp = DEVICE_ID_REGEX, message = DEVICE_ID_REGEX_FAIL_MESSAGE) String deviceId) {
        log.info("Getting device: {}", deviceId);
        return ResponseEntity.ok(deviceService.getDeviceById(deviceId));
    }

    @GetMapping("")
    public ResponseEntity<DeviceTableViewDto> listDevicesByBrandAndState(@PageableDefault(size = 5)
             @SortDefault(sort = "deviceId", direction = DESC) @ParameterObject Pageable paging,
             @RequestParam(value = "brand", required = false) String deviceBrand,
             @RequestParam(value = "state", required = false) @ValidDeviceState(enumClass = State.class) String deviceState) {
        log.info("Listing devices by brand and state");

        return ResponseEntity.ok(deviceService.getDevicesByBrandAndState(paging, deviceBrand, State.fromText(deviceState)));
    }

    @PostMapping("/create")
    public ResponseEntity<DeviceDto> createDevice(@RequestBody @Valid DeviceDto deviceDto) {
        log.info("Creating device: {}", deviceDto);
        return new ResponseEntity<>(deviceService.createDevice(deviceDto), HttpStatus.CREATED);
    }

    @PutMapping("/{deviceId}")
    public ResponseEntity<DeviceDto> updateDevice(@PathVariable("deviceId") String deviceId,
              @RequestBody @Valid DeviceUpdateDto deviceUpdateDto) {
        log.info("Updating device ID {} with values {}", deviceId, deviceUpdateDto);
        return ResponseEntity.ok(deviceService.updateDeviceById(deviceId, deviceUpdateDto));
    }

}
