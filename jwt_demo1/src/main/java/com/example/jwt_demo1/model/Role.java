package com.example.jwt_demo1.model;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id_role;

    private String rolename;

    public Long getId_role() {
        return id_role;

    }

    public String getRolename() {
        return rolename;
    }

    public void setId_role(Long id_role) {
        this.id_role = id_role;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}
