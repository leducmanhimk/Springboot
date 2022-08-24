package com.example.jwt_demo1.controller;

import com.example.jwt_demo1.Role.Role;
import com.example.jwt_demo1.Role.RoleRestponsitory;
import com.example.jwt_demo1.User.CustomUserRepository;
import com.example.jwt_demo1.User.User;
import com.example.jwt_demo1.User.UserRespository;
import com.example.jwt_demo1.payload.UserRespone;
import org.aspectj.weaver.ast.And;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
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

    @Autowired
    RoleRestponsitory roleRestponsitory;

//    @ResponseStatus(code = HttpStatus.OK,reason = "OK")
    @PostMapping("/user")
    public UserRespone createUser(@RequestBody User user) {

         Role role1 = new Role();
         String name =  user.role.getRolename();
         role1 = roleRestponsitory.findRoleByRolename(name);
         Long roleid = role1.getId_role();

         if (roleid < 3){
             User user1 = new User();
             user1.setUsername(user.getUsername());
             user1.setPassword(user.getPassword());
             user1.setEmail(user.getEmail());
             user1.role.setId_role(role1.getId_role());
             user1.role.setRolename(role1.getRolename());
             userRespository.save(user1);
             return new UserRespone(user1);
         }
         else
         {
                return new UserRespone(null);
         }


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
