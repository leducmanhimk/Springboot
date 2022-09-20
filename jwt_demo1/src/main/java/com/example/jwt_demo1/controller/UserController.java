package com.example.jwt_demo1.controller;

import com.example.jwt_demo1.Email.MyEmail;
import com.example.jwt_demo1.ExceptionHandler.IllegalUserException;
import com.example.jwt_demo1.ExceptionHandler.NotfoundUsernameException;
import com.example.jwt_demo1.Role.Role;
import com.example.jwt_demo1.Role.RoleRestponsitory;
import com.example.jwt_demo1.Thread.ThreadSendEmail;
import com.example.jwt_demo1.service.CustomUserRepositoryImpl;
import com.example.jwt_demo1.User.User;
import com.example.jwt_demo1.User.UserRespository;
import com.example.jwt_demo1.payload.UserRespone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/api")

public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    private final UserRespository userRespository;
    private final CustomUserRepositoryImpl customUserRepository;
    private final RoleRestponsitory roleRestponsitory;
    private final JavaMailSender emailSender;
    private final ThreadSendEmail thread;

    @Autowired
    public UserController(CustomUserRepositoryImpl customUserRepository
            , ThreadSendEmail threadSendEmail, JavaMailSender emailSender, RoleRestponsitory roleRestponsitory,
                          UserRespository userRespository) {
        this.customUserRepository = customUserRepository;
        this.thread = threadSendEmail;
        this.emailSender = emailSender;
        this.roleRestponsitory = roleRestponsitory;
        this.userRespository = userRespository;
    }

    @PostMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            Thread upload1 = new Thread(thread, "sendemail");
            Thread upload2 = new Thread(thread, "sendemail2");
            String name = user.role.getRolename();
            Role role1 = roleRestponsitory.findRoleByRolename(name);
            User user1 = new User();
            user1.setUsername(user.getUsername());
            user1.setPassword(user.getPassword());
            user1.setEmail(user.getEmail());
            user1.role.setId_role(role1.getId_role());
            user1.role.setRolename(role1.getRolename());
            userRespository.save(user1);
            upload1.start();
            logger.info("Trạng thái của luồng " + upload1.getName() + " " + upload1.getState());
            upload1.join();
            logger.info("Trạng thái của luồng " + upload1.getName() + " " + upload1.getState());
            logger.info("Trạng thái của luồng " + upload2.getName() + " " + upload2.getState());
            if (!upload1.isAlive()) {
                logger.info("hoàn thành luồng 1 ---> " + upload1.getName());
                logger.info("bắt đầu gọi luồng 2");
                upload2.start();
            }
            upload2.join();
            System.out.println("Trạng thái của luồng " + upload2.getState());
            // todo : khi 2 hoan thanh nhiem vu. print value;
            if (!upload2.isAlive()) {
                logger.info("luồng 2 đã hoàn thành");
            }

            return ResponseEntity.ok(new UserRespone("thêm người dùng thành công", user1));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(new UserRespone("lỗi!"));
        }

    }

    @PutMapping("/user/{id}")
    @PreAuthorize("hasRole('EDITER')")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            User user1 = new User();
            user1.setId(id);
            user1.setUsername(user.getUsername());
            user1.setPassword(user.getPassword());
            user1.setEmail(user.getEmail());
            userRespository.save(user1);
            return new ResponseEntity<>(user1, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("không thể sửa người dùng", HttpStatus.NOT_FOUND);
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
        User user;

        user = customUserRepository.finUserbyId(id);

        if (user == null)
            throw new NotfoundUsernameException();
        return new UserRespone("tìm thấy user thuộc id " + id, user);
    }

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

    @DeleteMapping("/deleteuser/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete_an_User(@PathVariable Long id) {
        try {
            customUserRepository.delete(id);
        } catch (NullPointerException ex) {
            throw new NotfoundUsernameException();
        } catch (IllegalArgumentException ex) {
            throw new IllegalUserException();
        }
        return new ResponseEntity<>("xóa đối tượng thành công", HttpStatus.OK);
    }
}
