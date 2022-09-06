package com.example.jwt_demo1.controller;

import com.example.jwt_demo1.Role.Role;
import com.example.jwt_demo1.Role.RoleRestponsitory;
import com.example.jwt_demo1.User.CustomUserRepository;
import com.example.jwt_demo1.User.User;
import com.example.jwt_demo1.User.UserRespository;
import com.example.jwt_demo1.User.UserRoleNotfoundException;
import com.example.jwt_demo1.payload.UserRespone;
import net.bytebuddy.utility.nullability.AlwaysNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            String name = user.role.getRolename();
           Role role1 = roleRestponsitory.findRoleByRolename(name);
            User user1 = new User();
            user1.setUsername(user.getUsername());
            user1.setPassword(user.getPassword());
            user1.setEmail(user.getEmail());
            user1.role.setId_role(role1.getId_role());
            user1.role.setRolename(role1.getRolename());
            userRespository.save(user1);
            return ResponseEntity.ok(new UserRespone("thêm người dùng thành công", user1));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(new UserRespone("lỗi!"));
        }

    }

    @PutMapping("/user/{id}")
    @PreAuthorize("hasRole('EDITER')")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            User user1 = new User();
            user1.setId(id);
            user1.setUsername(user.getUsername());
            user1.setPassword(user.getPassword());
            user1.setEmail(user.getEmail());
            userRespository.save(user1);
            return new ResponseEntity<User>(user1, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/users")
    @PreAuthorize("hasRole('EDITER') or hasRole('ADMIN') or hasRole('USER')")
    public List<User> listAllUsers() {
        return customUserRepository.getAlluser();
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('EDITER') or hasRole('ADMIN') or hasRole('USER')")
    public UserRespone findUserbyId(@PathVariable Long id) {
        User user = customUserRepository.finUserbyId(id);
        if (user ==  null)
            return new UserRespone("không tìm thấy user");
        return new UserRespone("tìm thấy user thuộc id" + id, user);
    }


}
