package com.example.jwt_demo1.service;

import com.example.jwt_demo1.Contact.Contact;
import com.example.jwt_demo1.User.User;
import com.example.jwt_demo1.User.UserRespository;
import java8.util.concurrent.CompletableFuture;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomUserRepositoryImpl {
    public CustomUserRepositoryImpl() {

    }

    private static final Logger logger = LoggerFactory.getLogger(CustomUserRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    UserRespository userRespository;

    @Autowired
    JavaMailSender emailSender;

    //thêm mới một bản ghi
    @Async
    @Transactional(rollbackOn = {Exception.class, Throwable.class})
    public void save(User user) {
        entityManager.persist(user);
        logger.info("thêm một user mới");
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
    public CompletableFuture<String> findUser(Long id) {
        User user = CompletableFuture.supplyAsync(() ->

                entityManager.find(User.class, id)).join();

        return CompletableFuture.completedFuture(user.getUsername());
    }

    @Transactional
    public void delete(Long id){
        User user = entityManager.find(User.class,id);
        entityManager.remove(user);
    }

}