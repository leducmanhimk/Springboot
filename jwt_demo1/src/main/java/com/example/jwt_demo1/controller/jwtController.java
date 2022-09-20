package com.example.jwt_demo1.controller;


import com.example.jwt_demo1.ExceptionHandler.NotfoundUsernameException;
import com.example.jwt_demo1.jwt.JwtTokenProvider;
import com.example.jwt_demo1.User.User;
import com.example.jwt_demo1.User.UserRespository;
import com.example.jwt_demo1.payload.LoginResponse;
import com.example.jwt_demo1.payload.RandomStuff;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;


import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api_authen")
public class jwtController {


    private final JwtTokenProvider tokenProvider;
    private final UserRespository userRespository;



    @Autowired
    public  jwtController(UserRespository userRespository, JwtTokenProvider tokenProvider) {
        this.userRespository = userRespository;
        this.tokenProvider = tokenProvider;
    }


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
            throw new NotfoundUsernameException();
        }
        return new LoginResponse("sai mật khẩu tài khoản");
    }


    @GetMapping("/random")
    @PreAuthorize("hasRole('EDITER')")
    public RandomStuff randomStuff() {
        return new RandomStuff("JWT Hợp lệ mới có thể thấy được message này");
    }
}

