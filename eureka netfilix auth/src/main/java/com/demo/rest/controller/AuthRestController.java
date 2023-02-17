package com.demo.rest.controller;

import cn.apiclub.captcha.Captcha;
import com.demo.entity.RegisterUserResponse;
import com.demo.entity.User;
import com.demo.repository.UserRepository;
import com.demo.service.IUserService;
import com.demo.util.CaptchaUtil;
import com.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RestController
public class AuthRestController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IUserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptEncoder;

    @PostMapping("/auth/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        if (user.getCaptcha().equals(user.getHiddenCaptcha())) {

            Optional<User> gotUser = userService.findByUsername(user.getUsername());
            if (!(gotUser.isEmpty())) {
                if (bCryptEncoder.matches(user.getPassword(), gotUser.get().getPassword())) {
                    String token = jwtUtil.generateToken(user.getUsername());
                    user.setToken(token);
                    user.setId(gotUser.get().getId());
                    user.setPassword("");
                    System.out.println(token);
                } else {
                    user.setMessage("user or password not valid.");
                }
            }
        }else{
            user.setMessage("captcha not valid.");

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

    @PostMapping("/service/names")
    public ResponseEntity<?> getNamesOfUsers(@RequestBody List<Long> idList) {
        return ResponseEntity.ok(userRepository.findByIdList(idList));
    }

    @GetMapping("auth/loadCaptcha")
    public ResponseEntity<User> registerUser() {
        User user = new User();
        getCaptcha(user);
        return ResponseEntity.ok(user);
    }

    private void getCaptcha(User user) {
        Captcha captcha = CaptchaUtil.createCaptcha(250, 70);
        user.setHiddenCaptcha(captcha.getAnswer());
        user.setCaptcha(""); // value entered by the User
        user.setRealCaptcha(CaptchaUtil.encodeCaptcha(captcha));

    }

}
