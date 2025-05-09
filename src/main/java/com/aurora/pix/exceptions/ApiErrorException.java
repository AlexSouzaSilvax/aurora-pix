package com.aurora.pix.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus.Series;

import lombok.Getter;

@Getter
public class ApiErrorException extends RuntimeException {

    private final int statusCode;
    private final Series series;
    private final Object result;

    public ApiErrorException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
        this.series = HttpStatus.valueOf(statusCode).series();
        this.result = null;
    }

    public ApiErrorException(String message, int statusCode, Object result) {
        super(message);
        this.statusCode = statusCode;
        this.result = result;
        this.series = HttpStatus.valueOf(statusCode).series();
    }

    public ApiErrorException(String message) {
        super(message);
        this.statusCode = 500;
        this.series = HttpStatus.valueOf(this.statusCode).series();
        this.result = null;
    }

}
