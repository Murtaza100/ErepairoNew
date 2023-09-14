package com.stackroute.FeedbackService.exception;



public class InvalidRatingException extends Exception{
    private String message;

    public InvalidRatingException(String message) {
        super(message);
        this.message = message;
    }


    public String getMessage() {
        return message;
    }

}
