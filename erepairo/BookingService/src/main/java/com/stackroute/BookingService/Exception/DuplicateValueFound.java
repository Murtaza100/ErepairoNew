package com.stackroute.BookingService.Exception;

public class DuplicateValueFound extends RuntimeException {
    private String msg;

    public DuplicateValueFound(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }
}