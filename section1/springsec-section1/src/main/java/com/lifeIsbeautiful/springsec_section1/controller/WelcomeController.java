package com.lifeIsbeautiful.springsec_section1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping("/welcome")
    public String sayWelcome()
    {
        return  "Welcome to spring boot application without spring security";
    }
}
