package com.example.jwt_demo1.Thread;

import com.example.jwt_demo1.User.User;
import com.example.jwt_demo1.payload.UserRespone;
import org.springframework.data.jpa.repository.JpaRepository;

public class ThreadManager implements Runnable{
    private Thread t;
    private String threadname;



    @Override
    public void run() {
        t = new Thread();
        t.start();
        UserRespone userRespone = new UserRespone("đây là luồng khi chạy vào");
        System.out.println("trạng thái của luôngf"+t.getState());
        System.out.println(userRespone);
    }

    public void start(){
        if (t == null){
            t = new Thread();
            t.start();
        }
    }

}
