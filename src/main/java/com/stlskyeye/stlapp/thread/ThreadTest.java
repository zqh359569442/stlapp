package com.stlskyeye.stlapp.thread;

import com.stlskyeye.stlapp.ZookeeperConfig;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;

import java.util.concurrent.CountDownLatch;

public class ThreadTest extends Thread {
    private CountDownLatch countDownLatch;
    private boolean isRun =false;
    public ThreadTest(){}

    public ThreadTest(CountDownLatch cd){
        this.countDownLatch=cd;
    }
    @Override
    public void run() {
        super.run();

        try {
            new ZookeeperConfig().createData(Thread.currentThread().getName()+"_",CreateMode.EPHEMERAL_SEQUENTIAL ,countDownLatch);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(true){

        }else{
            try {
                this.wait(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName());

    }

    public boolean isRun() {
        return isRun;
    }

    public void setRun(boolean run) {
        isRun = run;
    }
}
