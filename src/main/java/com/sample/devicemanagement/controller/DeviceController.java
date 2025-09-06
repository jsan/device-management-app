package com.sample.devicemanagement.controller;

import com.sample.devicemanagement.dto.DeviceDto;
import com.sample.devicemanagement.service.DeviceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("devices")
public class DeviceController {

    private final DeviceService deviceService;

    @PostMapping("create")
    public ResponseEntity<DeviceDto> saveUser(@RequestBody @Valid DeviceDto device)
    {
        log.info("Saving device: {}", device);
        return new ResponseEntity<>(deviceService.createDevice(device), HttpStatus.CREATED);
    }

}
