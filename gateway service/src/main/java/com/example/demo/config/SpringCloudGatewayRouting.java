//package com.example.demo.config;
//
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///*
//    Create by Atiye Mousavi
//    Date: 1/16/2023
//    Time: 3:25 PM
//**/
//@Configuration
//public class SpringCloudGatewayRouting {
//
//    @Bean
//    public RouteLocator configureRoute(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("clientService1", r->r.path("/clientService1/**").uri("lb://CLIENT-SERVICE-1")) //static routing
//                .route("clientService2", r->r.path("/clientService2/**").uri("lb://CLIENT-SERVICE-2")) //dynamic routing
//                .route("gatewayService", r->r.path("/user/**").uri("lb://GATEWAY-SERVICE")) //dynamic routing
//                .build();
//    }
//}
//
//
