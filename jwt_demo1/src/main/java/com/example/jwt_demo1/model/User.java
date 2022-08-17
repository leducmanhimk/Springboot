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

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(nullable = false,unique = true)
    private String username;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    private String password;
    private String email;

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + username + ", email=" + email
                + ", password=" + password + "]";
    }

}
