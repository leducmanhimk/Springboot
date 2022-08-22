package com.example.jwt_demo1.service;


import com.example.jwt_demo1.model.CustomUserRepository;
import com.example.jwt_demo1.model.User;
import com.example.jwt_demo1.model.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

//@Service
public class UserService {
    @Autowired
    UserRespository userRespository;

    @Autowired
    CustomUserRepository customUserRepository;

    public void saveuser(User user){
        customUserRepository.save(user);
    }
    @Transactional
    public UserDetails loadUserById(Long Id){

        User user = userRespository.findById(Id).orElseThrow(
        ()-> new UsernameNotFoundException("User not found with id:"+Id)
    );
//

        return new CustomUserDetails(user);
    }



}
