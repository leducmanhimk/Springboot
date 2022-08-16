package com.example.jwt_demo1;

import com.example.jwt_demo1.model.Contact;
import com.example.jwt_demo1.model.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class JwtDemo1Application implements CommandLineRunner {
    @Autowired
    private ContactRepository repo;

    public static void main(String[] args) {
        SpringApplication.run(JwtDemo1Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        updateContact();
    }

    private void updateContact() {
        Contact existContact = new Contact();

        existContact.setId(1);
        existContact.setName("Peter Smith");
        existContact.setEmail("petersmith@gmail.com");
        existContact.setAddress("New York, USA");
        existContact.setPhone("123456-2111");

        Contact updatedContact = repo.update(existContact);

    }
}

