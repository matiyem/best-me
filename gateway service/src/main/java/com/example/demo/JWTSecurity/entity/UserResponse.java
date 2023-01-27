package com.example.demo.JWTSecurity.entity;

/*
    created by Atiye Mousavi
    Date: 1/1/2023
    Time: 8:40 PM
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String token;
    private String message;
}