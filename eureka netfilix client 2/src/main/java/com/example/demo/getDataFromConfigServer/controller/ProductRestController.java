package com.example.demo.getDataFromConfigServer.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    Create by Atiye Mousavi 
    Date: 1/11/2023
    Time: 1:08 PM
**/
@RestController
@RequestMapping("/product")
//baraye akharin taghirat in config server
@RefreshScope
public class ProductRestController {

//    @Value("${app.title}")
    private String title;

    @GetMapping("/data")
    public ResponseEntity<String> showProductMsg() {
        return new ResponseEntity<String>("Value of title from Config Server: "+title, HttpStatus.OK);
    }
}