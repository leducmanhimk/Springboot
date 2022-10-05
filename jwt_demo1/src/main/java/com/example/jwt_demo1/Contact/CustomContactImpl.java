package com.example.jwt_demo1.Contact;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
public class CustomContactImpl {

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
    public List<Contact> finAll(){
        String jpql = "SELECT c FROM Contact c";
        TypedQuery<Contact> query = entityManager.createQuery(jpql,Contact.class);
        return Collections.unmodifiableList(query.getResultList());
    }
    //xóa một bản ghi
    @Transactional
    public void delete(Integer id){
        Contact contact = entityManager.find(Contact.class,id);
        entityManager.remove(contact);
    }

    public Contact findByid(Integer id){
        return entityManager.find(Contact.class,id);
    }
}
