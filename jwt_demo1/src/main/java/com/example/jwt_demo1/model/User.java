package com.example.jwt_demo1.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue()
    private Long id;

    @Column(nullable = false,unique = true)
    private String username;
    private String password;
    private String email;

}
