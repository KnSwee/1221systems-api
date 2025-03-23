package ru.myspar.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    public ErrorResponse handleNotFoundException(final NotFoundException ex) {
        log.error("404 NOT_FOUND {}", ex.getMessage());
        return new ErrorResponse(HttpStatus.NOT_FOUND.toString(),
                "Element not found in Database",
                ex.getMessage());
    }

    @ExceptionHandler
    public ErrorResponse handleBadRequestException(final BadRequestException ex) {
        log.error("400 BAD_REQUEST {}", ex.getMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST.toString(),
                "Bad request",
                ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(final MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        ValidationErrorResponse response = new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.toString(),
                "Validation failed",
                errors
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}

