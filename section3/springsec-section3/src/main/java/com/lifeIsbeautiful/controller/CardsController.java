package com.lifeIsbeautiful.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardsController {

    @GetMapping("/myCards")
    public String getCardDetails()
    {
        return
                "Here Are the card details from the db";
    }
}
