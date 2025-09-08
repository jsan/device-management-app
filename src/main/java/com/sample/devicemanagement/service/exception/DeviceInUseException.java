package com.sample.devicemanagement.service.exception;

public class DeviceInUseException extends RuntimeException {

    public static final String DEVICE_IN_USE = "Device with ID '%s' is on 'in-use' state and cannot be updated.";

    public DeviceInUseException(String deviceId) {
        super(String.format(DEVICE_IN_USE, deviceId));
    }

}
