package com.example.jwt_demo1.Thread;

import com.example.jwt_demo1.Email.MyEmail;
import com.example.jwt_demo1.User.User;
import com.example.jwt_demo1.payload.UserRespone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;
import java.io.InputStream;

public class ThreadManager implements Runnable{
    private Thread t;
    private String threadname;

    @Autowired
     JavaMailSender emailSender;

    @Override
    public void run() {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(MyEmail.FRIEND_EMAIL);
        message.setSubject("Test Multi-thread");
        message.setText("hello,test thành công");

          emailSender.send(message);
        System.out.println("đã chạy vào đây");
    }



}
