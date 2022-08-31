package com.example.jwt_demo1.service;

import com.example.jwt_demo1.Role.Role;
import com.example.jwt_demo1.Contact.Contact;
import com.example.jwt_demo1.Contact.ContactRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ContactService {
    @Autowired
    private ContactRespository contactRepository;

    public List<Contact> listAll(){
        return contactRepository.findAll();
    }

    public void save(Contact contact){
        contactRepository.save(contact);
    }

    public Contact getId(Long id){
        return contactRepository.findById(id).get();
    }

    public void Delete(Long id){
        contactRepository.deleteById(id);
    }

    public static interface RoleRepository extends JpaRepository<Role,Long> {
        Role findByRolename(String rolename);
    }
}
