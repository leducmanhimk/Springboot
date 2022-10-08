package com.example.jwt_demo1.Thread;

public class MyRunable implements Runnable {
    private final long countUntil;


    public MyRunable(long countUntil) {
        this.countUntil = countUntil;
    }

    @Override
    public void run() {
        long sum = 0;
        for (long i = 1; i < countUntil; i++) {
            sum += i;
        }
        System.out.println(sum);
    }

}
