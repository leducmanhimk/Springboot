package com.example.jwt_demo1.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class apiExceptionHandler {

    @ExceptionHandler(value = NotfoundUsernameException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> nullUserException(NotfoundUsernameException exception) {
        return new ResponseEntity<>("không tìm thấy tài khoản", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = IllegalUserException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> IllegallUserException(IllegalUserException exception) {
        return new ResponseEntity<>("người dùng không hợp lệ", HttpStatus.NOT_FOUND);
    }
}
