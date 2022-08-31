package com.example.jwt_demo1.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//tạo lớp ngoại lệ về xác thực
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    private  static  final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        logger.error("lỗi xác thực:{}",authException.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Lỗi:không thể xác thực");
    }
}
