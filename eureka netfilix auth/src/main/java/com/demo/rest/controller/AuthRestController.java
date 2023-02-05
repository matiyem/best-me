package com.demo.rest.controller;

import com.demo.entity.RegisterUserResponse;
import com.demo.entity.User;
import com.demo.service.IUserService;
import com.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
@CrossOrigin()
public class AuthRestController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private IUserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptEncoder;

    @PostMapping("/auth/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        Optional<User> gotUser = userService.findByUsername(user.getUsername());
        if (!(gotUser.isEmpty())) {
            if (bCryptEncoder.matches(user.getPassword(), gotUser.get().getPassword())) {
                String token = jwtUtil.generateToken(user.getUsername());
                user.setToken(token);
            }
            else {
                user.setMessage("user or password not valid");
            }
        }


        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<RegisterUserResponse> register(@RequestBody User user) {
        Optional<User> gotUser = userService.findByUsername(user.getUsername());
        RegisterUserResponse response = new RegisterUserResponse();
        if (gotUser.isEmpty()) {
            userService.saveUser(user);
            response.setResponseId(1);
            response.setMessage("user save successfully");
        } else {
            response.setResponseId(2);
            response.setMessage("user is duplicated");

        }
        return new ResponseEntity<RegisterUserResponse>(response, HttpStatus.OK);
    }

    @PostMapping("/auth/abas")
    public ResponseEntity<String> register1(@RequestBody String userName) {
        // Persist user to some persistent storage
        System.out.println("Info saved...");

        return new ResponseEntity<String>("Registered", HttpStatus.OK);
    }

}
