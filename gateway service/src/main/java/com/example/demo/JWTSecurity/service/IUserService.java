package com.example.demo.JWTSecurity.service;

/*
    created by Atiye Mousavi
    Date: 1/1/2023
    Time: 8:09 PM
*/


import com.example.demo.JWTSecurity.entity.User;

import java.util.Optional;

public interface IUserService {

    Integer saveUser(User user);

    Optional<User> findByUsername(String username);
}