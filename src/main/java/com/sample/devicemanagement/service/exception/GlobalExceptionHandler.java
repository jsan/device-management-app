package com.sample.devicemanagement.service.exception;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static java.lang.String.format;
import static java.net.URI.create;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.ProblemDetail.forStatusAndDetail;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{
    private static final String DOCUMENTATION_REFERENCE = "Documentation reference on error details: {}";
    private static final String ALL_ERRORS_PAGE = "https://address.to.error.documentation.page/docs/default/device-management-app/errorCodes#%s";
    private static final String DEVICE_SERVICE_ERROR = "device-service-error";
    private static final String PROBLEM_TYPE_BASE_URL = "urn:problem-type:";

    @ExceptionHandler(DeviceAlreadyExistsException.class)
    ProblemDetail handleDeviceAlreadyExistsException(@NotNull DeviceAlreadyExistsException ex) {
            log.error(DOCUMENTATION_REFERENCE, create(format(ALL_ERRORS_PAGE, DEVICE_SERVICE_ERROR)));
            ProblemDetail problemDetail = forStatusAndDetail(INTERNAL_SERVER_ERROR, ex.getMessage());
            problemDetail.setType(create(PROBLEM_TYPE_BASE_URL+DEVICE_SERVICE_ERROR));
            problemDetail.setTitle("Device Service Error");
            return problemDetail;
    }

}
