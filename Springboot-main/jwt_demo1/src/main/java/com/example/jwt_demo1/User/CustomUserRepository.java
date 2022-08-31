package com.example.jwt_demo1.User;

import com.example.jwt_demo1.User.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class CustomUserRepository {

    @PersistenceContext
    private EntityManager entityManager;


    //thêm mới một bản ghi
    @Transactional(rollbackOn = {Exception.class,Throwable.class})
    public void save(User user) {
        entityManager.persist(user);
    }

    public User finUserbyId(Long id){
        return entityManager.find(User.class,id);
    }
    public User findUserbyEmail(String email){
        return entityManager.find(User.class,email);
    }

    public User findUserbyName(String username){
        return entityManager.find(User.class,username);
    }
    public List<User> getAlluser(){

            String jpql = "SELECT u FROM User u";
            TypedQuery<User> query = entityManager.createQuery(jpql,User.class);

            return query.getResultList();

    }


}
