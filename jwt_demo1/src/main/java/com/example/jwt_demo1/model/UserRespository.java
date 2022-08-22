package com.example.jwt_demo1.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRespository extends JpaRepository<User,Long> {
    User findUserById(Long id);
    User findUserByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username =:username")
    public User getUserByUsername(@Param("username")String username);

}
