package com.example.jwt_demo1;

import com.example.jwt_demo1.Contact.CustomContactRepository;
import com.example.jwt_demo1.service.CustomUserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class JwtDemo1Application {

    @Autowired
    private CustomUserRepositoryImpl customUserRepository;

    @Autowired
    private CustomContactRepository customContactRepository;

    public static void main(String[] args){
        SpringApplication.run(JwtDemo1Application.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//       ListContact();
//    }
//
//    private void addUser() {
//
//        User user = new User();
//
//
//        user.setUsername("Manhle");
//        user.setEmail("manhle@gmail.com");
//        user.setPassword("123");
//
//        customUserRepository.save(user);
//
//
//    }
//    public void ListContact(){
//        List<Contact> listcontact = customContactRepository.finAll();
//        listcontact.forEach(System.out::println);
//    }
}

