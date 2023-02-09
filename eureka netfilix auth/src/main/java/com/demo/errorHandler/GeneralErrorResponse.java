package com.demo.errorHandler;

import lombok.Data;

@Data
public class GeneralErrorResponse {

    private String errorCode = "0";
    private String errorMessage = "";

    @Override
    public String toString() {
        return "GeneralErrorResponse{" +
                "errorCode=" + errorCode +
                '}';
    }
}
