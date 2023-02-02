package com.example.demo.config;

import com.example.demo.JWTAtentication.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

	@Autowired
	private JwtAuthenticationFilter filter;

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("authService", r -> r.path("/auth/**").filters(f -> f.filter(filter)).uri("lb://AUTH-SERVICE"))
				.route("clientService1", r -> r.path("/clientService1/**").filters(f -> f.filter(filter)).uri("lb://CLIENT-SERVICE-1"))
				.route("clientService2", r -> r.path("/clientService2/**").filters(f -> f.filter(filter)).uri("lb://CLIENT-SERVICE-2"))
				.build();
	}
}
