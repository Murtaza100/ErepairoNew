package com.stackroute.apigateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/gateway")
public class Controller {

    @GetMapping("/message")
    public String getMessage() {
        return "Hai, from gateway";
    }

    @GetMapping("/info")
    public String getInfo() {
        return "hai, this is information";
    }
}

