package com.co.cetus.pruebatecnica.exception;

import org.springframework.http.HttpStatus;

public class AuthenticationException extends RuntimeException {

    private final String message;
    private final HttpStatus httpStatus;

    public AuthenticationException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
