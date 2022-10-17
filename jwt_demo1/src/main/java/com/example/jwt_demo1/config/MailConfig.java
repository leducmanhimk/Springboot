package com.example.jwt_demo1.config;
import com.example.jwt_demo1.Email.MyEmail;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    //cấu hình host mail
    @Bean
    public JavaMailSender getJavaMailsender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(MyEmail.MY_EMAIL);
        mailSender.setPassword(MyEmail.MY_PASSWORD);
        Properties property = mailSender.getJavaMailProperties();
        property.put("mail.transport.protocol","smtp");
        property.put("mail.smtp.auth","true");
        property.put("mail.smtp.starttls.enable","true");
        property.put("mail.debug","true");
        return mailSender;
    }
}
