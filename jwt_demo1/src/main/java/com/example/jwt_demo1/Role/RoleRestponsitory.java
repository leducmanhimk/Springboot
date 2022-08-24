package com.example.jwt_demo1.Role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRestponsitory extends JpaRepository<Role,Long> {
    Role findRoleByRolename(String name);
}
