package com.stackroute.ServiceProviderService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ServiceProviderDTO {

    private String userFirstName;
    private String email;
    private String userPassword;
}
