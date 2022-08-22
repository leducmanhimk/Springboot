package com.example.jwt_demo1.controller;

import com.example.jwt_demo1.model.CustomUserRepository;
import com.example.jwt_demo1.model.User;
import com.example.jwt_demo1.model.UserRespository;
import com.example.jwt_demo1.payload.UserRespone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;



@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRespository userRespository;

    @Autowired
    CustomUserRepository customUserRepository;

//    @ResponseStatus(code = HttpStatus.OK,reason = "OK")
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
    public ResponseEntity<User> updateUser (@PathVariable Long id ,  @RequestBody User user)
    {


        try {
            User user1 = new User();
            user1.setId(id);
            user1.setUsername(user.getUsername());
            user1.setPassword(user.getPassword());
            user1.setEmail(user.getEmail());

            userRespository.save(user1);
            return new ResponseEntity<User>(user1,HttpStatus.OK);
        }
        catch (NoSuchElementException e){
            return  new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/users")
    public List<User> listAllUsers(){
        return customUserRepository.getAlluser();
    }

    @GetMapping("/user/{id}")
    public User findUserbyId(@PathVariable Long id){
       return customUserRepository.finUserbyId(id);
    }

}
