package com.sample.devicemanagement.service.exception;

public class DeviceAlreadyExistsException extends RuntimeException {

    public static final String DEVICE_ALREADY_EXISTS = "Device with ID '%s', name '%s', and brand '%s' already exists.";

    public DeviceAlreadyExistsException(String id, String name, String brand) {
        super(String.format(DEVICE_ALREADY_EXISTS, id, name, brand));
    }

}
