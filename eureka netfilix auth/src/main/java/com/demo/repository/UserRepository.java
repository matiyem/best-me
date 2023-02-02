package com.demo.repository;

/*
    created by Atiye Mousavi
    Date: 1/1/2023
    Time: 8:07 PM
*/


import com.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
}