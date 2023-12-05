package br.com.cleonildo.productmanager.exceptions.controller;

import br.com.cleonildo.productmanager.exceptions.NotFoundException;
import br.com.cleonildo.productmanager.exceptions.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ErrorExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> entityNotFound(NotFoundException notFoundException) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        ErrorResponse err = ErrorResponse.builder()
                .status(status.value())
                .statusErrorMessage(status.getReasonPhrase())
                .message(notFoundException.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(status).body(err);
    }
}
