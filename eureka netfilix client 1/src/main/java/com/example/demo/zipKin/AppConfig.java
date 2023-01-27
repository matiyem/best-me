package com.example.demo.zipKin;

import brave.sampler.Sampler;
import org.springframework.context.annotation.Bean;

/*
    Create by Atiye Mousavi
    Date: 1/21/2023
    Time: 8:57 AM
**/
public class AppConfig {
    @Bean
    public Sampler samplerOb() {
        return Sampler.ALWAYS_SAMPLE;
    }
}