package com.example.jwt_demo1.payload;

import com.example.jwt_demo1.User.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRespone {
    private String messenger = "thêm đối tượng thành công";
    User user = new User();
    public UserRespone(User information){
        this.user = information;
    }
    public  UserRespone(String messenger){
        this.messenger = messenger;
    }
}
