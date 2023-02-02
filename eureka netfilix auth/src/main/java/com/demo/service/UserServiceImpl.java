package com.demo.service;

/*
    created by Atiye Mousavi
    Date: 1/1/2023
    Time: 8:09 PM
*/


import com.demo.entity.User;
import com.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptEncoder;

    @Override
    public Integer saveUser(User user) {

        //Encode password before saving to DB
        user.setPassword(bCryptEncoder.encode(user.getPassword()));
        return userRepo.save(user).getId();
    }

    //find user by username
    @Override
    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }
}