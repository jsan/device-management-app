package com.sample.devicemanagement.controller;

import com.sample.devicemanagement.dto.DeviceDto;
import com.sample.devicemanagement.service.DeviceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/devices")
public class DeviceController {

    private final DeviceService deviceService;

    @PostMapping("/create")
    public ResponseEntity<DeviceDto> createDevice(@RequestBody @Valid DeviceDto deviceDto) {
        log.info("Creating device: {}", deviceDto);
        return new ResponseEntity<>(deviceService.createDevice(deviceDto), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DeviceDto>> listAllDevices(
            @PageableDefault(size = 5) @SortDefault(sort = "deviceId", direction = DESC) Pageable paging) {
        log.info("Listing all devices");
        List<DeviceDto> deviceList = deviceService.getAllDevices();

        return new ResponseEntity<>(deviceList, HttpStatus.OK);
    }

}
