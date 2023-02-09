package com.example.demo.JWTAtentication.errorHandler;

import lombok.Data;

/*
    Create by Atiye Mousavi 
    Date: 2/6/2023
    Time: 12:09 PM
**/
@Data
public class BusinessException extends RuntimeException {

    private String errorCode;
    private String errorMessage;
    private int step;

    public BusinessException(String errorCode, String errorMessage) {
        super("ErrorCode : " + errorCode + " errorMessage : " + errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}