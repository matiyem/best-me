package com.demo.service;

/*
    created by Atiye Mousavi
    Date: 1/1/2023
    Time: 8:09 PM
*/


import com.demo.entity.User;

import java.util.Optional;

public interface IUserService {

    Integer saveUser(User user);

    Optional<User> findByUsername(String username);
}