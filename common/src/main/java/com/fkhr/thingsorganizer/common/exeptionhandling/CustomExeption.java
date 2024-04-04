package com.fkhr.thingsorganizer.common.exeptionhandling;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class CustomExeption extends RuntimeException{
    private HttpStatus status;
    private String message;
    private Integer code;
    private Throwable cause;
    private LocalDateTime timestamp;

    public CustomExeption(String message, HttpStatus status, Integer code, Throwable cause){
        super();
        this.message = message;
        if(code != null) {
            this.code = code;
        }
        else {
            this.code = status.value();
        }
        this.status = status;
        this.cause = cause;
        timestamp = LocalDateTime.now();
    }

    public CustomExeption(String message, HttpStatus status, Throwable cause){
        super();
        this.message = message;
        this.code = status.value();
        this.status = status;
        this.cause = cause;
        timestamp = LocalDateTime.now();
    }

    public CustomExeption(CustomError customError, HttpStatus status, Throwable cause){
        super();
        this.message = customError.message();
        this.code = customError.code();
        this.status = status;
        this.cause = cause;
        timestamp = LocalDateTime.now();
    }

    public CustomExeption(CustomError customError, HttpStatus status){
        super();
        this.message = customError.message();
        this.code = customError.code();
        this.status = status;
        timestamp = LocalDateTime.now();
    }

    public CustomExeption(CustomError customError){
        super();
        this.message = customError.message();
        this.code = customError.code();
        this.status = HttpStatus.OK;
        timestamp = LocalDateTime.now();
    }
    public CustomExeption(HttpStatus status, Throwable cause){
        super();
        this.message = status.name();
        this.code = status.value();
        this.status = status;
        this.cause = cause;
        timestamp = LocalDateTime.now();
    }

    public CustomExeption(String message, HttpStatus status){
        super();
        this.message = message;
        this.code = status.value();
        this.status = status;
        timestamp = LocalDateTime.now();
    }

    public CustomExeption(String message){
        super();
        this.message = message;
        this.code = status.value();
        timestamp = LocalDateTime.now();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
