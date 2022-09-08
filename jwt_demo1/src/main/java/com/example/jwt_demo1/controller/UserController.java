package com.example.jwt_demo1.controller;

import com.example.jwt_demo1.Email.MyEmail;
import com.example.jwt_demo1.Role.Role;
import com.example.jwt_demo1.Role.RoleRestponsitory;
import com.example.jwt_demo1.Thread.ThreadManager;
import com.example.jwt_demo1.User.CustomUserRepository;
import com.example.jwt_demo1.User.User;
import com.example.jwt_demo1.User.UserRespository;
import com.example.jwt_demo1.User.UserRoleNotfoundException;
import com.example.jwt_demo1.payload.UserRespone;
import java8.util.concurrent.CompletableFuture;
import net.bytebuddy.utility.nullability.AlwaysNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserRespository userRespository;

    @Autowired
    CustomUserRepository customUserRepository;

    @Autowired
    RoleRestponsitory roleRestponsitory;

    @Autowired
    JavaMailSender emailSender;

    @Autowired
    ThreadManager thread;

    @Autowired
    Executor executor;

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
            Thread upload1 = new Thread(thread, "sendemail");
            upload1.start();
            if (upload1.getState().equals("wait") == true){
                Thread upload2 = new Thread(thread, "sendemail");
                upload2.start();
            }
            // todo : khi 2 hoan thanh nhiem vu. print value;

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
//        long start = System.currentTimeMillis();
//        User  user = new User();
//        CompletableFuture<User> user1 = customUserRepository.findUser(id);
//        CompletableFuture<User> user2 = customUserRepository.findUser(id + 1);
//        CompletableFuture<User> user3 = customUserRepository.findUser(id + 2);
//
//        CompletableFuture.allOf(user1,user2,user3).join();
//
//        logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
//        logger.info("--> " + user1.get());
//        logger.info("--> " + user2.get());
//        logger.info("--> " + user3.get());
        User user = customUserRepository.finUserbyId(id);
        if (user == null)
            return new UserRespone("không tìm thấy user");
        return new UserRespone("tìm thấy user thuộc id" + id, user);
    }


    @ResponseBody
    @GetMapping("/sendSimpleEmail")
    @PreAuthorize("hasRole('ADMIN')")
    public String sendSimpleEmail() {

        // Create a Simple MailMessage.
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(MyEmail.FRIEND_EMAIL);
        message.setSubject("Test Simple Email");
        message.setText("Hello, Im testing Simple Email");

        // Send Message!
        this.emailSender.send(message);

        return "Email Sent!";
    }
}
