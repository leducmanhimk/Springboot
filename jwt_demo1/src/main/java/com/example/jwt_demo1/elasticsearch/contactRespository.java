package com.example.jwt_demo1.elasticsearch;

import com.example.jwt_demo1.Contact.Contact;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface contactRespository extends ElasticsearchRepository<Contact,Long> {
    
}
