package com.example.jwt_demo1.controller;


import com.example.jwt_demo1.jwt.AuthTokenFilter;
import com.example.jwt_demo1.jwt.JwtTokenProvider;
import com.example.jwt_demo1.User.CustomUserRepository;
import com.example.jwt_demo1.User.User;
import com.example.jwt_demo1.User.UserRespository;
import com.example.jwt_demo1.payload.LoginResponse;
import com.example.jwt_demo1.payload.RandomStuff;

import com.example.jwt_demo1.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

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
    CustomUserRepository customUserRepository;

    @PostMapping("/login")
    public LoginResponse authenticateUser(@Valid @RequestBody User user) {
        User user1 = userRespository.getUserByUsername(user.getUsername());
        String username = user1.getUsername();
        user1.setUsername(username);
        String error = "không tìm thấy username";
        if (username == null) {
            return new LoginResponse(error);
        } else {
            String jwt = tokenProvider.gennerateToken(user1);
            return new LoginResponse(jwt);
        }
    }

    @RequestMapping("/accessdenied")
    public ModelAndView accessdenied() {
        return new ModelAndView("accessdenied");
    }


    @GetMapping("/random")
    @PreAuthorize("hasRole('EDITER')")
    public RandomStuff randomStuff() {
        return new RandomStuff("JWT Hợp lệ mới có thể thấy được message này");
    }
}
