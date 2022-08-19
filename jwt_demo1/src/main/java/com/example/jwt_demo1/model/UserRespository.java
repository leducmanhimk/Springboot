package com.example.jwt_demo1.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRespository extends JpaRepository<User,Long> {
    User findUserById(Long id);
    User findUserByUsername(String username);

}
