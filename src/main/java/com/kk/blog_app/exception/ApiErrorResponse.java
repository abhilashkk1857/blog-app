package com.kk.blog_app.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
public class ApiErrorResponse {
    private final HttpStatus status;
    private final String message;
    private final LocalDateTime timestamp;

    public ApiErrorResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

}
