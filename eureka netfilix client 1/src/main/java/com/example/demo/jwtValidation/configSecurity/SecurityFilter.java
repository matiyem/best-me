package com.example.demo.jwtValidation.configSecurity;

import com.example.demo.jwtValidation.exception.JwtTokenMalformedException;
import com.example.demo.jwtValidation.exception.JwtTokenMissingException;
import com.example.demo.jwtValidation.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
    Create by Atiye Mousavi
    Date: 2/21/2023
    Time: 9:30 AM
**/

@Configuration
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil util;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token != null) {
            try {
                util.validateToken(token);
            } catch (JwtTokenMalformedException | JwtTokenMissingException e) {
                // e.printStackTrace();
                response.setStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION.value());
                throw new AuthenticationException("token not valid");
            }
        }
        filterChain.doFilter(request, response);
    }
}
