package com.stackroute.BookingService.Exception;

public class DateTimeException extends RuntimeException {
    private String msg;

    public DateTimeException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }
}

