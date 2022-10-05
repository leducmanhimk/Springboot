package com.example.jwt_demo1.ExceptionHandler;
import com.example.jwt_demo1.payload.ResponseMessageFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class apiExceptionHandler {


    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> IlleagalArgument(){
        return new ResponseEntity<>("lỗi phân tích cú pháp",HttpStatus.NOT_FOUND);
    }

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

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ResponseMessageFile> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessageFile("kích thước của tệp quá lớn!"));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public ResponseEntity<Object> handleMethodNotSupported(HttpServletRequest request) {
        return new ResponseEntity<>( "HTTP request method not supported for this operation.",HttpStatus.METHOD_NOT_ALLOWED);
    }
}
