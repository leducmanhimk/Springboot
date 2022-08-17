package com.example.jwt_demo1.controller;

import com.example.jwt_demo1.model.CustomUserRepository;
import com.example.jwt_demo1.model.User;
import com.example.jwt_demo1.model.UserRespository;
import com.example.jwt_demo1.payload.UserRequest;
import com.example.jwt_demo1.payload.UserRespone;
import com.example.jwt_demo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRespository userRespository;

    @Autowired
    CustomUserRepository customUserRepository;


    @PostMapping("/user")
    public UserRespone createUser( @RequestBody User user) {

           User user1 = new User();
           user1.setUsername(user.getUsername());
           user1.setPassword(user.getPassword());
           user1.setEmail(user.getEmail());


            userRespository.save(user1);
           return new UserRespone(user1);

    }
    @PutMapping("/user/{id}")
    public void updateUser (@PathVariable Long id ,  @RequestBody User user)
    {
        User user1 = new User();
        user1.setId(id);
        user1.setUsername(user.getUsername());
        user1.setPassword(user.getPassword());
        user1.setEmail(user.getEmail());

        userRespository.save(user1);
    }
    @GetMapping("/users")
    public List<User> listAllUsers(){
        return customUserRepository.getAlluser();
    }

    @GetMapping("/user/{id}")
    public User findUserbyId(@PathVariable Long id,@RequestBody User user){
       return customUserRepository.finUserbyId(id);
    }

}
