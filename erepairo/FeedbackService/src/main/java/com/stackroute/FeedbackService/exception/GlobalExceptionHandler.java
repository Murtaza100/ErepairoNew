package com.stackroute.FeedbackService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserIdNotFoundException.class)
    public ResponseEntity<ErrorInfo> userIdNotFound(UserIdNotFoundException idNotFoundException)
    {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorMessage(idNotFoundException.getMsg());
        errorInfo.setHttpStatus(HttpStatus.NOT_FOUND.toString());
        errorInfo.setLocalDateTime(LocalDateTime.now());

        return new ResponseEntity<ErrorInfo>(errorInfo,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookingIdNotFoundException.class)
    public ResponseEntity<ErrorInfo> bookingIdNotFound(BookingIdNotFoundException idNotFoundException)
    {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorMessage(idNotFoundException.getMessage());
        errorInfo.setHttpStatus(HttpStatus.NOT_FOUND.toString());
        errorInfo.setLocalDateTime(LocalDateTime.now());

        return new ResponseEntity<ErrorInfo>(errorInfo,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(FeedbackIdNotFoundException.class)
    public ResponseEntity<ErrorInfo> feedbackIdNotFound(FeedbackIdNotFoundException idNotFoundException)
    {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorMessage(idNotFoundException.getMessage());
        errorInfo.setHttpStatus(HttpStatus.NOT_FOUND.toString());
        errorInfo.setLocalDateTime(LocalDateTime.now());

        return new ResponseEntity<ErrorInfo>(errorInfo,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ServiceProviderIdNotFoundException.class)
    public ResponseEntity<ErrorInfo> serviceprovideridNotFound(ServiceProviderIdNotFoundException idNotFoundException)
    {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorMessage(idNotFoundException.getMessage());
        errorInfo.setHttpStatus(HttpStatus.NOT_FOUND.toString());
        errorInfo.setLocalDateTime(LocalDateTime.now());

        return new ResponseEntity<ErrorInfo>(errorInfo,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidRatingException.class)
    public ResponseEntity<ErrorInfo> invalidRatingException(InvalidRatingException invalidRatingException)
    {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorMessage(invalidRatingException.getMessage());
        errorInfo.setHttpStatus("Rating should not be more than 5");
        errorInfo.setLocalDateTime(LocalDateTime.now());

        return new ResponseEntity<ErrorInfo>(errorInfo,HttpStatus.NOT_FOUND);
    }
}
