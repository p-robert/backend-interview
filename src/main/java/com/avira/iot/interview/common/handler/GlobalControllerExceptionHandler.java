package com.avira.iot.interview.common.handler;

import com.avira.iot.interview.common.exception.DataAlreadyExistsException;
import com.avira.iot.interview.common.exception.DataNotFoundException;
import com.avira.iot.interview.common.util.ApiErrorResponse;
import com.avira.iot.interview.common.util.ApiErrorsResponse;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ApiErrorsResponse> handleValidationExceptions(
            ServerHttpRequest request,
            WebExchangeBindException e) {
        val errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(error -> {
                    val sb = new StringBuilder();
                    sb.append(error.getField());
                    sb.append(": ");
                    sb.append(error.getDefaultMessage());
                    return sb.toString();
                }).collect(Collectors.joining(", ", "[", "]"));

        val apiErrorResponse = new ApiErrorResponse(
                request.getId(),
                BAD_REQUEST.value(),
                BAD_REQUEST,
                "Validation error",
                errorMessage
        );
        return createErrorResponseInfo(apiErrorResponse, e);
    }

    @ExceptionHandler(ServerWebInputException.class)
    public ResponseEntity<ApiErrorsResponse> handleHttpStatusCodeException(
            ServerHttpRequest request,
            ServerWebInputException e
    ) {
        val apiErrorResponse = new ApiErrorResponse(request.getId(), BAD_REQUEST.value(),
                BAD_REQUEST, "Bad request", e.getMessage());
        return createErrorResponseInfo(apiErrorResponse, e);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorsResponse> handleException(
            ServerHttpRequest request,
            Exception e
    ) {
        val apiErrorResponse = new ApiErrorResponse(request.getId(),
                0,
                INTERNAL_SERVER_ERROR,
                "Unknown error",
                e.getMessage()
        );

        return createErrorResponseInfo(apiErrorResponse, e);
    }

    @ExceptionHandler(DataNotFoundException.class)
    protected ResponseEntity<ApiErrorsResponse> handleDataNotFoundException(
            ServerHttpRequest request,
            DataNotFoundException e
    ) {
        val apiErrorResponse = new ApiErrorResponse(request.getId(),
                NOT_FOUND.value(),
                NOT_FOUND,
                "Data not found",
                e.getMessage()
        );
        return createErrorResponseInfo(apiErrorResponse, e);
    }

    @ExceptionHandler(DataAlreadyExistsException.class)
    protected ResponseEntity<ApiErrorsResponse> handleDataNotAlreadyExistsException(
            ServerHttpRequest request,
            DataAlreadyExistsException e
    ) {
        val apiErrorResponse = new ApiErrorResponse(request.getId(),
                CONFLICT.value(),
                CONFLICT,
                "Data already exists",
                e.getMessage()
        );
        return createErrorResponseInfo(apiErrorResponse, e);
    }

    private ResponseEntity<ApiErrorsResponse> createErrorResponseInfo(
            ApiErrorResponse apiErrorResponse,
            Exception e
    ) {
        val status = apiErrorResponse.getStatus();
        val detail = apiErrorResponse.getDetail();
        if (apiErrorResponse.getStatus().isError()) {
            log.error(
                    "Request with id {} returning HTTP status: {}, message: {}",
                    apiErrorResponse.getId(),
                    status,
                    detail,
                    e
            );
        } else {
            log.info(
                    "Request with id {} returning HTTP status: {}, message: {}",
                    apiErrorResponse.getId(),
                    status,
                    detail,
                    e
            );
        }
        return new ResponseEntity<>(new ApiErrorsResponse(List.of(apiErrorResponse)), apiErrorResponse.getStatus());
    }
}
