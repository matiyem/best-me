package com.example.demo.JWTSecurity.config;

/*
    created by Atiye Mousavi
    Date: 1/1/2023
    Time: 8:08 PM
*/


import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }
}