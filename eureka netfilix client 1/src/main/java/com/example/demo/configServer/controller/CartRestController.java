package com.example.demo.configServer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    Create by Atiye Mousavi 
    Date: 1/9/2023
    Time: 4:08 PM
**/
@RestController
@RequestMapping("/clientService1")
public class CartRestController {

    @GetMapping("/getData")
    public String getCartData() {
        return "Returning data from CART-SERVICE";
    }
}