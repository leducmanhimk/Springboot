package com.example.jwt_demo1.controller;


import com.example.jwt_demo1.Thread.ThreadSendEmail;
import com.example.jwt_demo1.jwt.JwtTokenProvider;
import com.example.jwt_demo1.service.CustomUserRepositoryImpl;
import com.example.jwt_demo1.User.User;
import com.example.jwt_demo1.User.UserRespository;
import com.example.jwt_demo1.payload.LoginResponse;
import com.example.jwt_demo1.payload.RandomStuff;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class jwtController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    UserRespository userRespository;

    @Autowired
    CustomUserRepositoryImpl customUserRepository;


    @PostMapping("/login")
    public LoginResponse authenticateUser(@Valid @RequestBody User user) {
        User user1 = userRespository.getUserByUsername(user.getUsername());
        String username = user1.getUsername();
        user1.setUsername(username);
        String error = "không tìm thấy username";
        if (username.equals("")) {
            return new LoginResponse(error);
        } else {
            String jwt = tokenProvider.gennerateToken(user1);
            return new LoginResponse(jwt);
        }
    }


    @GetMapping("/random")
    @PreAuthorize("hasRole('EDITER')")
    public RandomStuff randomStuff() {
        return new RandomStuff("JWT Hợp lệ mới có thể thấy được message này");
    }
}
