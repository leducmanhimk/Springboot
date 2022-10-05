package com.example.jwt_demo1.Thread;
import com.example.jwt_demo1.Email.MyEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ThreadSendEmail implements Runnable{
    public  ThreadSendEmail(){

    }
    private Thread t;
    private String threadname;



    @Autowired
     JavaMailSender emailSender;

    @Override
    public synchronized void run() {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(MyEmail.FRIEND_EMAIL);
        message.setSubject("Test Multi-thread");
        message.setText("hello,test thành công");

          emailSender.send(message);
        System.out.println("đã chạy vào đây");
        notify();
    }

}
