package com.example.jwt_demo1.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ContactRepository {

    @PersistenceContext
    private EntityManager entityManager;


    //thêm mới một bản ghi
    @Transactional
    public void save(Contact contact) {
        entityManager.persist(contact);
    }

    //thực hiện cập nhật
    @Transactional
    public Contact update(Contact contact){
       return entityManager.merge(contact);
    }

    //thực hiện truy vấn và trả về câu lệnh
    @Transactional
    List<Contact> finAll(){
        String jpql = "SELECT c FROM Contact c";
        TypedQuery<Contact> query = entityManager.createQuery(jpql,Contact.class);

        return query.getResultList();
    }
    //xóa một bản ghi
    @Transactional
    public void delete(Integer id){
        Contact contact = entityManager.find(Contact.class,id);
        entityManager.remove(contact);

    }

    private Contact findByid(Integer id){
        return entityManager.find(Contact.class,id);
    }
}
