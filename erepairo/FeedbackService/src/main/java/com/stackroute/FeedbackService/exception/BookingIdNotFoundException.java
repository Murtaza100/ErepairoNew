package com.stackroute.FeedbackService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class BookingIdNotFoundException extends Exception {
    private String message;

    public BookingIdNotFoundException(String message) {
        super(message);
        this.message = message;
    }


    public String getMessage() {
        return message;
    }
}
