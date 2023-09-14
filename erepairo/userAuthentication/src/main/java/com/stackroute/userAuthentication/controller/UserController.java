package com.stackroute.userAuthentication.controller;

/**
 * This is Class UserController.
 *
 * @author by Zaid
 */

import com.stackroute.userAuthentication.dto.UserDto;

/**
 * This is Class UserController.
 *
 * @author by Zaid
 */

import com.stackroute.userAuthentication.entity.User;
import com.stackroute.userAuthentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;


@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }

    @PostMapping({"registerNewUser/user"})
    public User registerNewUser(@RequestBody UserDto user) {
    	
        return userService.registerNewUser(user);
        
    }
    
    @PostMapping({"registerNewUser/serviceprovider"})
    public User registerNewServiceProvider(@RequestBody UserDto user) {
    	
        return userService.registerNewServiceProvider(user);
        
    }
    
    @GetMapping({"forAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin(){
        return "This URL is only accessible to the admin";
    }

    @GetMapping({"forUser"})
    @PreAuthorize("hasRole('User')")
    public String forUser(){
        return "This URL is only accessible to the user";
        
       
    }
}