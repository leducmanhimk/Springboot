package com.example.jwt_demo1.service;


import com.example.jwt_demo1.model.CustomUserRepository;
import com.example.jwt_demo1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

//@Service
public class UserService {

    @Autowired
    CustomUserRepository customUserRepository;

    public void saveuser(User user){
        customUserRepository.save(user);
    }
    @Transactional
    public UserDetails loadUserById(Long Id){

//        User user = userRepository.findById(Id).orElseThrow(
//        ()-> new UsernameNotFoundException("User not found with id:"+Id)
//    );
//
        User user= new User();
        return new CustomUserDetails(user);
    }



}
