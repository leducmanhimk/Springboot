package com.example.jwt_demo1.controller;


import com.example.jwt_demo1.jwt.JwtAuth;
import com.example.jwt_demo1.jwt.JwtTokenProvider;
import com.example.jwt_demo1.model.Contact;
import com.example.jwt_demo1.model.CustomUserRepository;
import com.example.jwt_demo1.model.User;
import com.example.jwt_demo1.model.UserRespository;
import com.example.jwt_demo1.payload.LoginRequest;
import com.example.jwt_demo1.payload.LoginResponse;
import com.example.jwt_demo1.payload.RandomStuff;
import com.example.jwt_demo1.service.ContactService;
import com.example.jwt_demo1.service.CustomUserDetails;
import com.sun.xml.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class jwtController {
    @Autowired
    AuthenticationManager authenticationManager;
//
//    @Autowired
//    JwtAuth jwtAuth;
//    @Autowired
//    ContactService contactService;
//    @RequestMapping("/home")
//    public String viewHomePage(Model model){
//        List<Contact> listcontact = contactService.listAll();
//        model.addAttribute("listcontat",listcontact);
//
////        return model;
//        return null;
//    }

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    UserRespository userRespository;

    @Autowired
    CustomUserRepository customUserRepository;

    @PostMapping("/login")
    public LoginResponse authenticateUser(@Valid @RequestBody User user) {
//
//        //Xác thực từ username và password.
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        user.getUsername(),
//                        user.getPassword()
//                )
//        );

        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
//        SecurityContextHolder.getContext().setAuthentication(authentication);

                User user1 = new User();
                user1.setUsername(user.getUsername());
                user1.setEmail(user.getEmail());
                String jwt = tokenProvider.gennerateToken(user1 );
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
