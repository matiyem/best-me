package com.example.demo.histrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/*
    Create by Atiye Mousavi
    Date: 1/16/2023
    Time: 8:26 AM
**/
@RestController
@RequestMapping("/payment")
public class PaymentRestController {

    @GetMapping("/pay")
    @HystrixCommand(fallbackMethod = "doDummyPay",
            commandProperties = {
                    @HystrixProperty(
                            name= "circuitBreaker.requestVolumeThreshold",
                            value="6"),
                    @HystrixProperty(
                            name= "circuitBreaker.sleepWindowInMilliseconds",
                            value="10000"),
                    @HystrixProperty(
                            name= "circuitBreaker.enabled",
                            value = "false")
            } )
    public String doPayment() {
        System.out.println("---Start of  PAYMENT-METHOD---");
              if(new Random().nextInt(10) <=10) {
            throw new RuntimeException("DUMMY Example");
        }
        System.out.println("---End of PAYMENT-METHOD---");
        return "SUCCESS";
    }

    public String doDummyPay() {
        System.out.println("---FROM FALLBACK METHOD---");
        return "SERVICE IS DOWN, PLEASE TRY AFTER SOMETIME !!!";
    }
}