package com.consultancy.project.exceptions;

import com.consultancy.project.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(DatabaseErrorException.class)
    public ResponseEntity<ErrorResponse> handleDatabaseErrorException(DatabaseErrorException ex) {
        ErrorResponse errorResponse = errorResponse(ex.getMessage(), ex.getException(), ex.getStatus());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RecordExistsException.class)
    public ResponseEntity<ErrorResponse> handleRecordExistsException(RecordExistsException ex) {
        ErrorResponse errorResponse = errorResponse(ex.getMessage(), ex.getError(), ex.getStatus());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
        ErrorResponse errorResponse = errorResponse(ex.getMessage(), ex.getReason(), ex.getStatusCode().value());
        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }

    private ErrorResponse errorResponse(String message, String reason, int status){
        return ErrorResponse.builder()
                .message(message)
                .error(reason)
                .status(status)
                .traceId(MDC.get(Constants.TRACE_ID_KEY))
                .timestamp(MDC.get(Constants.REQUEST_TIME) != null
                        ? LocalDateTime.parse(MDC.get(Constants.REQUEST_TIME))
                        : LocalDateTime.now())
                .build();
    }
}
