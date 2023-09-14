package com.stackroute.userService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorInfo> resourceNotFound(ResourceNotFoundException resourceNotFoundException)
    {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorMessage(resourceNotFoundException.getMessage());
        errorInfo.setHttpStatus(HttpStatus.NOT_FOUND.toString());
        errorInfo.setLocalDateTime(LocalDateTime.now());
        return new ResponseEntity<ErrorInfo>(errorInfo,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleInvalidArgumentException(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return new ResponseEntity<Map<String, String>> (errorMap, HttpStatus.BAD_REQUEST);
    }
}
