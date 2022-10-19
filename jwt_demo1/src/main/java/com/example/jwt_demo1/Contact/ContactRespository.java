package com.example.jwt_demo1.Contact;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Lazy
public interface ContactRespository extends JpaRepository<Contact,Long> {
}
