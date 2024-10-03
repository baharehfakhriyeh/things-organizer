package com.fkhr.thingsorganizer.common.exeptionhandling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    //todo: complete exception handling for all remaining types.
    @ExceptionHandler(CustomExeption.class)
    protected ResponseEntity<Object> handleCustomExeption(CustomExeption ex) {
        return buildResponseEntity(ex);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        return buildResponseEntity(new CustomExeption(HttpStatus.NOT_FOUND, ex));
    }

    @ExceptionHandler(HttpServerErrorException.class)
    protected ResponseEntity<Object> handleHttpServerErrorException(HttpServerErrorException ex) {
        return buildResponseEntity(new CustomExeption(HttpStatus.INTERNAL_SERVER_ERROR, ex));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleException(Exception ex) {
        return buildResponseEntity(new CustomExeption(HttpStatus.INTERNAL_SERVER_ERROR, ex));
    }

    private ResponseEntity<Object> buildResponseEntity(CustomExeption error) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonError = objectMapper.createObjectNode();
        jsonError.put("code", error.getCode());
        jsonError.put("error", error.getMessage());

        return new ResponseEntity(jsonError, error.getStatus());
    }
}
