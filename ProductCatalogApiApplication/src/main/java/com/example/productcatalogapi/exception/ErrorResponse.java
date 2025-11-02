package com.example.productcatalogapi.exception;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private List<FieldErrorDetail> errors;
    private String path;

    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(int status, String error, String message, List<FieldErrorDetail> errors, String path) {
        this();
        this.status = status;
        this.error = error;
        this.message = message;
        this.errors = errors;
        this.path = path;
    }

    @Data
    public static class FieldErrorDetail {
        private String field;
        private String message;

        public FieldErrorDetail() {
        }

        public FieldErrorDetail(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }
}

