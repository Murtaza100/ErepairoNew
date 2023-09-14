package com.stackroute.userService.exception;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ErrorInfo {
    private String errorMessage;
    private String httpStatus;
    private LocalDateTime localDateTime;
}
