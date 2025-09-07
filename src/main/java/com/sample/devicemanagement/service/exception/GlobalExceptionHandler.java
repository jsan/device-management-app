package com.sample.devicemanagement.service.exception;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.List;

import static java.lang.String.format;
import static java.net.URI.create;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
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

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        List<ParamsViolationDetails> validationResponse = errors.stream()
                .map(err -> ParamsViolationDetails.builder()
                        .reason(err.getDefaultMessage())
                        .fieldName(err.getField()).build())
                .toList();
        return ResponseEntity.status(BAD_REQUEST).body(getValidationErrorsProblemDetail(validationResponse));
    }

    protected static ProblemDetail getValidationErrorsProblemDetail(List<ParamsViolationDetails> validationResponse) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(BAD_REQUEST, "Request validation failed");
        problemDetail.setType(URI.create("urn:problem-type:validation-error"));
        problemDetail.setTitle("Field Validation Exception");
        problemDetail.setProperty("invalidParams", validationResponse);
        return problemDetail;
    }

}
