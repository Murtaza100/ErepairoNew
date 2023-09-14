package com.stackroute.userAuthentication.controller;

/**
 * This is Class JwtController.
 *
 * @author by Zaid
 */

import com.stackroute.userAuthentication.entity.JwtRequest;
import com.stackroute.userAuthentication.entity.JwtResponse;
import com.stackroute.userAuthentication.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/")
public class JwtController {

    @Autowired
    private JwtService jwtService;
    

    @PostMapping({"/authenticate"})
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        return jwtService.createJwtToken(jwtRequest);
    }
}