package com.example.jwt_demo1.payload;

import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    private String refeshToken;
    private String tokenType = "Bearer";

    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }
    public LoginResponse(String accessToken,String refeshToken) {
        this.refeshToken = refeshToken;
        this.accessToken = accessToken;
    }
}
