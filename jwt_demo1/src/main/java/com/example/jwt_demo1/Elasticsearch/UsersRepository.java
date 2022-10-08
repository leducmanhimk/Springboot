package com.example.jwt_demo1.Elasticsearch;

import com.example.jwt_demo1.User.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
public interface UsersRepository extends ElasticsearchRepository<User,String> {
    Page<User> findUserByUsername(String name,Pageable pageable);
}
