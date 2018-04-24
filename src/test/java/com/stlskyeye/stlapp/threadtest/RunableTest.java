package com.stlskyeye.stlapp.threadtest;

import java.util.concurrent.CountDownLatch;

public class RunableTest implements  Runnable{
    private  CountDownLatch countDownLatch;
    RunableTest(CountDownLatch cd){
        this.countDownLatch=cd;
    }
    @Override
    public void run() {
        for (int i=0;i<20;i++) {
            System.out.println(Thread.currentThread().getName()+":"+i);
        }
        countDownLatch.countDown();
    }
}
