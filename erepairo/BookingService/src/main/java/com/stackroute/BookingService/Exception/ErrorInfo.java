package com.stackroute.BookingService.Exception;

import java.time.LocalDateTime;

public class ErrorInfo {
    private String errorMessage;
    private String httpStatus;
    private LocalDateTime localDateTime;

    public ErrorInfo() {
    }

    public ErrorInfo(String errorMessage, String httpStatus, LocalDateTime localDateTime) {
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
        this.localDateTime = localDateTime;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getHttpStatus() {
        return this.httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    public LocalDateTime getLocalDateTime() {
        return this.localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}

