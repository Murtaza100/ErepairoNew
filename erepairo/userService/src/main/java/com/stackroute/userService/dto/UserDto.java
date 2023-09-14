package com.stackroute.userService.dto;

import com.stackroute.userService.collection.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String userName;
    private String userFirstName;
    private String userLastName;
    private String userPassword;
    private Role role;
}
