package com.example.demo.apiGateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    Create by Atiye Mousavi 
    Date: 1/16/2023
    Time: 3:13 PM
**/
@RestController
@RequestMapping("/clientService2")
public class ApiGateWayRestController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/info")
    public ResponseEntity<String> showPaymentInfo() {
        return ResponseEntity.ok("FROM PAYMENT SERVICE, Port# is: " + port);
    }
}