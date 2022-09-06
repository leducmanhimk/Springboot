package com.example.jwt_demo1.Thread;

import com.example.jwt_demo1.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

public class ThreadManager implements Runnable{
    private Thread t;
    private String threadname;



    @Override
    public void run() {
        System.out.println("xin chào");
    }

    public void start(){
        System.out.println("xin chào");
    }
}
