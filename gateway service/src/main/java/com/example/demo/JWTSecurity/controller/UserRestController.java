package com.example.demo.JWTSecurity.controller;

/*
    created by Atiye Mousavi
    Date: 1/1/2023
    Time: 8:42 PM
*/


import com.example.demo.JWTSecurity.entity.User;
import com.example.demo.JWTSecurity.entity.UserRequest;
import com.example.demo.JWTSecurity.entity.UserResponse;
import com.example.demo.JWTSecurity.service.IUserService;
import com.example.demo.JWTSecurity.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserRestController {
    @Autowired
    private IUserService userService;
    @Autowired
    private JWTUtil util;
//    @Autowired
//    private AuthenticationManager authenticationManager;

    @GetMapping("/saveUser")
    public ResponseEntity<String> saveUser() {

//        Integer id = userService.saveUser(user);
//        String message = "User with id '" + id + "' saved succssfully!";
        //return new ResponseEntity<String>(message, HttpStatus.OK);
        return ResponseEntity.ok("message");
    }

    @PostMapping("/loginUser")
    public ResponseEntity<UserResponse> login(@RequestBody UserRequest request) {

        //Validate username/password with DB(required in case of Stateless Authentication)
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        String token = util.generateToken(request.getUsername());
        return ResponseEntity.ok(new UserResponse(token, "Token generated successfully!"));
    }

    @PostMapping("/getData")
    public ResponseEntity<String> testAfterLogin(Principal p) {
        return ResponseEntity.ok("You are accessing data after a valid Login. You are :" + p.getName());
    }
}