package ru.myspar.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ValidationErrorResponse {
    private String status;
    private String reason;
    private Map<String, String> errors;
    private String timestamp;

    public ValidationErrorResponse(String status, String reason, Map<String, String> errors) {
        this.status = status;
        this.reason = reason;
        this.errors = errors;
        this.timestamp = ErrorResponse.TIME_FORMATTER.format(java.time.LocalDateTime.now());
    }
}

