package com.example.jwt_demo1.service;

import com.example.jwt_demo1.model.Contact;
import com.example.jwt_demo1.model.ContactRespository;
import com.example.jwt_demo1.model.CustomContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}
