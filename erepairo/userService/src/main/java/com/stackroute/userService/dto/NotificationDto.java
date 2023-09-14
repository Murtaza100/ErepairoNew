package com.stackroute.userService.dto;

import lombok.Data;

@Data
public class NotificationDto {
    private String email;
    private String mailSubject;
    private String mailBody;
}
