package com.example.jwt_demo1.User;

import com.example.jwt_demo1.Role.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;


@Entity

@Table(name = "user")
@AllArgsConstructor
public class User {
    public User(){}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;

    @Transient
    private String rolename;


    @OneToOne
    @JoinColumn(name = "role_id")


    public Role role = new Role(rolename);

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }


    @Override
    public String toString() {
        return "ROLE_" + getRole().getRolename();
    }
}
