package com.example.jwt_demo1.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRestponsitory extends JpaRepository<Role,Long> {
    Role findRoleByRolename(String name);
}
