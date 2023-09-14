package com.stackroute.ServiceProviderService.exception;

public class EmailNotFoundException extends RuntimeException{

    private String msg;

    public EmailNotFoundException(String msg) {
        super();
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }



}
