package com.example.jwt_demo1.service;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
public interface UserDetailsService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
