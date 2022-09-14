package com.example.jwt_demo1.controller;


import com.example.jwt_demo1.Thread.ThreadSendEmail;
import com.example.jwt_demo1.jwt.JwtTokenProvider;
import com.example.jwt_demo1.service.CustomUserRepositoryImpl;
import com.example.jwt_demo1.User.User;
import com.example.jwt_demo1.User.UserRespository;
import com.example.jwt_demo1.payload.LoginResponse;
import com.example.jwt_demo1.payload.RandomStuff;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
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
        try {
            User user2;
            user2 = user;
            User user1 = userRespository.findUserByUsername(user.getUsername());
            if (user2.getPassword().equals(user1.getPassword())) {
                String jwt = tokenProvider.gennerateToken(user2);
                return new LoginResponse(jwt);
            }

        } catch (NullPointerException exception) {
            return new LoginResponse("sai Tên tài khoản đăng nhập");
        }
        return new LoginResponse("sai mật khẩu tài khoản");
    }


    @GetMapping("/random")
    @PreAuthorize("hasRole('EDITER')")
    public RandomStuff randomStuff() {
        return new RandomStuff("JWT Hợp lệ mới có thể thấy được message này");
    }
}
