package com.example.jwt_demo1.service;

import com.example.jwt_demo1.User.User;
import com.example.jwt_demo1.User.UserRespository;
import java8.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CustomUserRepositoryImpl {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    UserRespository userRespository;

    @Autowired
    JavaMailSender emailSender;



    public void start() {
        System.out.println("xin chào");
    }

    //thêm mới một bản ghi
    @Async
    @Transactional(rollbackOn = {Exception.class, Throwable.class})
    public void save(User user) {
        entityManager.persist(user);
    }

    public User finUserbyId(Long id) {
        return entityManager.find(User.class, id);
    }

    public User findUserbyEmail(String email) {
        return entityManager.find(User.class, email);
    }

    public User findUserbyName(String username) {
        return entityManager.find(User.class, username);
    }


    public List<User> getAlluser() {

        String jpql = "SELECT u FROM User u";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);

        return Collections.unmodifiableList(query.getResultList());
    }

    @Async
    public CompletableFuture<List<User>> SaveUsers(MultipartFile file) throws Exception {
        long start = System.currentTimeMillis();
        List<User> users = parseCSVFile(file);
        logger.info("lưu danh sách users{}", users.size(), "" + Thread.currentThread().getName());
        for (User user : users) {
            user = userRespository.save(user);
        }
        long end = System.currentTimeMillis();
        logger.info("tổng thời gian {}", (end - start));
        return CompletableFuture.completedFuture(users);
    }

    private List<User> parseCSVFile(final MultipartFile file) throws Exception {
        final List<User> users = new ArrayList<>();
        try {
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    final String[] data = line.split(",");
                    final User user = new User();
                    user.setUsername(data[0]);
                    user.setEmail(data[1]);
                    user.setPassword(data[2]);
                    user.getRole().setRolename(data[3]);
                    users.add(user);
                }
                return users;
            }
        } catch (final IOException e) {
            throw new Exception("Failed to parse CSV file {}", e);
        }
    }

    @Async
    public CompletableFuture<User> findUser(Long id) throws InterruptedException {
        logger.info("tìm kiếm user " + id);
        User user = entityManager.find(User.class, id);
        Thread.sleep(1000L);
        return CompletableFuture.completedFuture(user);
    }
}
