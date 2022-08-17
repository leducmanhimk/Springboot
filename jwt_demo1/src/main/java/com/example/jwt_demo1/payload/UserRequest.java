package com.example.jwt_demo1.payload;

import com.example.jwt_demo1.model.User;
import lombok.Data;

@Data
public class UserRequest {
    User user = new User();


}
