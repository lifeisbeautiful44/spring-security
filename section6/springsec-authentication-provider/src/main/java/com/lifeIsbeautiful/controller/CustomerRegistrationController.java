package com.lifeIsbeautiful.controller;

import com.lifeIsbeautiful.controller.config.CustomerUserDetailServiceImpl;
import com.lifeIsbeautiful.controller.dto.request.CustomerRegistrationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("")
public class CustomerRegistrationController {

    private final CustomerUserDetailServiceImpl userDetailsService;

    public CustomerRegistrationController(CustomerUserDetailServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping(value = "register",produces = MediaType.ALL_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerUser(@RequestBody CustomerRegistrationDTO customerRegistrationDTO){
       String message =  userDetailsService.registerUser(customerRegistrationDTO);
       return  ResponseEntity.status(HttpStatus.CREATED).body(message);
    }
}
