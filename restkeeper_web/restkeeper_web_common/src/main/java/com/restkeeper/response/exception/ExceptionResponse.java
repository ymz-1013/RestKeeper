package com.restkeeper.response.exception;

import lombok.Data;

@Data
public class ExceptionResponse{
    private String msg;
    public ExceptionResponse(String msg){
        this.msg = msg;
    }
}
