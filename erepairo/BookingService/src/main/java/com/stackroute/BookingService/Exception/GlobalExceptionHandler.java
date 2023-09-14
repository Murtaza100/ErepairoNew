package com.stackroute.BookingService.Exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler({IdNotFoundException.class})
    public ResponseEntity<ErrorInfo> idNotFound(IdNotFoundException idNotFoundException) {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorMessage(idNotFoundException.getMsg());
        errorInfo.setHttpStatus(HttpStatus.NOT_FOUND.toString());
        errorInfo.setLocalDateTime(LocalDateTime.now());
        return new ResponseEntity(errorInfo, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({DateTimeException.class})
    public ResponseEntity<ErrorInfo> DateTime(DateTimeException dateTimeException) {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorMessage(dateTimeException.getMsg());
        errorInfo.setHttpStatus(HttpStatus.NOT_FOUND.toString());
        errorInfo.setLocalDateTime(LocalDateTime.now());
        return new ResponseEntity(errorInfo, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler({DuplicateValueFound.class})
    public ResponseEntity<ErrorInfo> DateTime(DuplicateValueFound dateTimeException) {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorMessage(dateTimeException.getMsg());
        errorInfo.setHttpStatus(HttpStatus.NOT_FOUND.toString());
        errorInfo.setLocalDateTime(LocalDateTime.now());
        return new ResponseEntity(errorInfo, HttpStatus.NOT_FOUND);
    }

}

