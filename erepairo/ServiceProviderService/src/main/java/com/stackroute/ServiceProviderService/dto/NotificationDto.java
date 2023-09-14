package com.stackroute.ServiceProviderService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class NotificationDto {

        private String email;
        private String mailSubject;
        private String mailBody;

}
