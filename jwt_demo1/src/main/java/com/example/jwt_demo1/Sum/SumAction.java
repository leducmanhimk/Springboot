package com.example.jwt_demo1.Sum;

import com.example.jwt_demo1.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.RecursiveTask;

public class SumAction extends RecursiveTask<Long> {
    private static final int SEQUENTIAL_THRESHOLD = 5;
    private List<Long> data;
    private static final Logger logger = LoggerFactory.getLogger(SumAction.class);
    public SumAction(List<Long> data){
        this.data = data;
    }

    @Override
    protected Long compute() {
        //trường hợp cơ bản
        if (data.size() <= SEQUENTIAL_THRESHOLD){
            long sum = computeSumDirectly();
           logger.info("Tổng của " + data.toString() + sum);
            return sum;
        }
        else { // trường hợp đệ quy
            int mid = data.size() /2;
            SumAction fistsubtask = new SumAction(data.subList(0,mid));
            SumAction secoundtask = new SumAction(data.subList(mid,data.size()));

            fistsubtask.fork(); //hàng đợi công việc đầu tiên
            return secoundtask.compute()   + fistsubtask.join(); // đợi kết quả của công việc đầu tiên

        }

    }
    private long computeSumDirectly() {
        long sum = 0;
        for (Long l: data) {
            sum += l;
        }
        return sum;
    }
}
