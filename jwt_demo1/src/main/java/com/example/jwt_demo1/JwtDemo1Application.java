package com.example.jwt_demo1;

import com.example.jwt_demo1.Contact.CustomContactImpl;
import com.example.jwt_demo1.File.FilesStorageService;
import com.example.jwt_demo1.service.CustomUserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;


@SpringBootApplication
public class JwtDemo1Application implements CommandLineRunner {

    @Resource
    FilesStorageService storageService;

    public static void main(String[] args){

        SpringApplication.run(JwtDemo1Application.class, args);
        SpringApplication application = new SpringApplication();
        application.setBannerMode(Banner.Mode.LOG);
    }

    @Override
    public void run(String... arg) throws Exception {
        storageService.deleteAll();
        storageService.init();
    }
}

