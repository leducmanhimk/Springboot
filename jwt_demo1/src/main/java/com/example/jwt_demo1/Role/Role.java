package com.example.jwt_demo1.Role;

import lombok.AllArgsConstructor;
import lombok.Data;


import javax.persistence.*;
@AllArgsConstructor
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
    public Role(){

    }
    public Role(String rolename){
        this.rolename = rolename;
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

    @Override
    public String toString() {
        return "ROLE_"+rolename;
    }
}
