package com.example.jwt_demo1.payload;

import com.example.jwt_demo1.model.User;
import lombok.Data;

@Data
public class UserRespone {

    private String messenger = "thêm đối tượng thành công";
    User user = new User();
    public UserRespone(User information){
        this.user = information;
    }
}
