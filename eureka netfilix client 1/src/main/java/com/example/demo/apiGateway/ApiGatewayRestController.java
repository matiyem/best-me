package com.example.demo.apiGateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    Create by Atiye Mousavi 
    Date: 1/16/2023
    Time: 3:16 PM
**/
@RestController
@RequestMapping("/clientService1")
public class ApiGatewayRestController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/info")
    public ResponseEntity<String> getDataFromConfigServer() {
        return ResponseEntity.ok("FROM ORDER SERVICE, Port# is: " + port);
    }
}