package com.restkeeper.response.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Object exception(Exception e){
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage());
        return exceptionResponse;
    }
}
