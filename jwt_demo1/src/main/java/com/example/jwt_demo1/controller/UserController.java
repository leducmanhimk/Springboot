package com.example.jwt_demo1.controller;
import com.example.jwt_demo1.Email.MyEmail;
import com.example.jwt_demo1.ExceptionHandler.IllegalUserException;
import com.example.jwt_demo1.ExceptionHandler.NotfoundUsernameException;
import com.example.jwt_demo1.Role.Role;
import com.example.jwt_demo1.Role.RoleRestponsitory;
import com.example.jwt_demo1.Sum.SumAction;
import com.example.jwt_demo1.Thread.MyRunable;
import com.example.jwt_demo1.Thread.ThreadSendEmail;
import com.example.jwt_demo1.service.CustomUserRepositoryImpl;
import com.example.jwt_demo1.User.User;
import com.example.jwt_demo1.User.UserRespository;
import com.example.jwt_demo1.payload.UserRespone;
import java8.util.concurrent.CompletableFuture;
import lombok.SneakyThrows;
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
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


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
            Thread upload1 = new Thread(thread, "sendmail");
            Thread upload2 = new Thread(thread, "sendmail2");
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

    @SneakyThrows
    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('EDITER') or hasRole('ADMIN') or hasRole('USER')")
    public UserRespone findUserbyId(@PathVariable Long id) {
        User user = new User();
        CompletableFuture<User> user1 = CompletableFuture.supplyAsync(() ->
                {
                    try {
                        Thread.sleep(30000);
                        logger.info("đã hoàn thành luồng 1");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return customUserRepository.finUserbyId(id);
                }
        );
        CompletableFuture<User> user2 = CompletableFuture.supplyAsync(() ->
                {
                    try {
                        Thread.sleep(20000);
                        logger.info("đã hoàn thành luồng 2");

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return customUserRepository.finUserbyId(id + 3);
                }
        );
        CompletableFuture<User> user3 = CompletableFuture.supplyAsync(() ->
                {

                    try {
                        Thread.sleep(30000);
                        logger.info("đã hoàn thành luồng 3");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return customUserRepository.finUserbyId(id + 5);
                }
        );
        CompletableFuture.allOf(user1, user2, user3).join();
        logger.info("đã hoàn thành cả 3 luồng: ");
        logger.info("--> " + user1.get());
        logger.info("--> " + user2.get());
        logger.info("--> " + user3.get());

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

    @GetMapping("/simulatorcombine")
    public ResponseEntity<?> simulatorcombine() {
        CompletableFuture<String> future1 = CompletableFuture
                .supplyAsync(() -> "Future1")
                .thenApply((s) -> {
                    logger.info("bắt đầu future1");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return s + "!";
                });
        CompletableFuture<String> future2 = CompletableFuture
                .supplyAsync(() -> "Future2")
                .thenApply((s) -> {
                    logger.info("bắt đầu future2");
                    try {
                        Thread.sleep(20000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return s + "!";
                });

        future1.thenCombine(future2, (s1, s2) -> s1 + " + " + s2)
                .thenAccept(logger::info);
        return ResponseEntity.ok(HttpStatus.OK);

    }

    private static final int NTHREDS = 10;

    @GetMapping("/simulatorExcutor")
    public ResponseEntity<?> TestThreadPool() {
        ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
        for (int i = 0; i < 500; i++) {
            Runnable worker = new MyRunable(10000L + i);
            executor.execute(worker);
        }
        // This will make the executor accept no new threads
        // and finish all existing threads in the queue
        executor.shutdown();
        // Wait until all threads are finish
//        executor.awaitTermination();
        System.out.println("Finished all threads");

        return ResponseEntity.ok(HttpStatus.OK);
    }

    //synchronized
    @GetMapping("/simulatorSyn")
    public void SimulatorSyn() {
        final User user = new User();
        Thread t1 = new Thread(() -> {
            logger.info("luồng 1 bắt đầu thực hiện");
            customUserRepository.RutTien(20000);
        });
        t1.start();
        Thread t2 = new Thread(() -> {
            logger.info("luồng 2 bắt đầu thực hiện");
            customUserRepository.nopTien(30000);
        });
        t2.start();
    }

    //fork / join
    @GetMapping("/simulatorforkjoin")
    public void SimulatorForkJoin(){
        Random random = new Random();

        List<Long> data = random
                .longs(0,1,5)
                .boxed()
                .collect(Collectors.toList());

        ForkJoinPool pool = new ForkJoinPool();
        SumAction task = new SumAction(data);
        System.out.println("Sum:" + pool.invoke(task));
    }
}

