package com.stlskyeye.stlapp.threadtest;

import java.util.concurrent.CountDownLatch;

public class ThreadTest extends Thread {
    private CountDownLatch countDownLatch;
    ThreadTest(){

    }

    ThreadTest(CountDownLatch cd){
        this.countDownLatch=cd;
    }
    @Override
    public void run() {
        super.run();

        for (int i=0;i<2;i++) {
            System.out.println(getName()+":"+i);
        }
        countDownLatch.countDown();
    }
}
