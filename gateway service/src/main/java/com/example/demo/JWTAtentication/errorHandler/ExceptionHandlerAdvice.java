package com.example.demo.JWTAtentication.errorHandler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @Autowired
    private Environment env;

    @ExceptionHandler({Exception.class})
    public ResponseEntity<GeneralErrorResponse> handleException(Exception ex) {
        GeneralErrorResponse response = new GeneralErrorResponse();
        if (ex instanceof BusinessException) {
            String errorCode = ((BusinessException) ex).getErrorCode();
            String errorMessage = ((BusinessException) ex).getErrorMessage();
            response.setErrorCode(errorCode);
            response.setErrorMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else {
            response.setErrorCode("101");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

