package com.amach.coreServices.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<String> handleRuntimeException(
            final RuntimeException e) {
        e.printStackTrace();
        return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    }
}
