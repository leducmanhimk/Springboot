package com.example.jwt_demo1.User;

import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;


public interface UserRepository extends ElasticsearchRepository<User,Long> {
    User findUserById(Long id);
    User findUserByUsername(String username);
    @Query("SELECT u FROM User u WHERE u.username =:username")
    public User getUserByUsername(@Param("username")String username);

    Page<User> findByAuthorsName(String name, Pageable pageable);

    Page<User> findByAuthorsNameUsingCustomQuery(String name, Pageable pageable);

}
