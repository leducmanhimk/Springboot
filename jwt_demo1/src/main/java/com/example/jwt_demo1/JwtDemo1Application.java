package com.example.jwt_demo1;

import com.example.jwt_demo1.Contact.CustomContactImpl;
import com.example.jwt_demo1.service.CustomUserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class JwtDemo1Application {

    @Autowired
    private CustomUserRepositoryImpl customUserRepository;

    @Autowired
    private CustomContactImpl customContactRepository;

    public static void main(String[] args){
        SpringApplication.run(JwtDemo1Application.class, args);
    }


}

