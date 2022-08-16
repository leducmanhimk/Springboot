package com.example.jwt_demo1.controller;


import com.example.jwt_demo1.jwt.JwtTokenProvider;
import com.example.jwt_demo1.payload.LoginRequest;
import com.example.jwt_demo1.payload.LoginResponse;
import com.example.jwt_demo1.payload.RandomStuff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class HomeController {
//    @Autowired
//    AuthenticationManager authenticationManager;
//
//    @Autowired
//    JwtAuth jwtAuth;


    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public LoginResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
//
//       // Xác thực từ username và password.
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginRequest.getUsername(),
//                        loginRequest.getPassword()
//                )
//        );
//
//        // Nếu không xảy ra exception tức là thông tin hợp lệ
//        // Set thông tin authentication vào Security Context
//        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Trả về jwt cho người dùng.
        String jwt = tokenProvider.gennerateToken();
        System.out.println(jwt);
        return new LoginResponse(jwt);
    }
    @RequestMapping("/accessdenied")
    public ModelAndView accessdenied() {
        return new ModelAndView("accessdenied");
    }
    @GetMapping("/random")
    public RandomStuff randomStuff(){
        return new RandomStuff("JWT Hợp lệ mới có thể thấy được message này");
    }
}
