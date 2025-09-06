package com.sample.devicemanagement.service.exception;

public class DeviceAlreadyExistsException extends RuntimeException {

    public static final String DEVICE_ALREADY_EXISTS = "Device for ID %s name %s and brand %s already exists";

    public DeviceAlreadyExistsException(Integer id, String name, String brand) {
        super(String.format(DEVICE_ALREADY_EXISTS, id, name, brand));
    }

}
