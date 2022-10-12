package com.example.jwt_demo1.service;
import com.example.jwt_demo1.User.User;
import com.example.jwt_demo1.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    UserRepository userRespository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRespository.findUserByUsername(username);

        return  new UserDetailsImpl(user);
    }
}
