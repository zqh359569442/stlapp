package com.stlskyeye.stlapp;

import com.stlskyeye.stlapp.thread.ThreadTest;
import org.apache.zookeeper.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;


public class ZookeeperConfig {

    private static ZooKeeper zk;

    static String   parentDir ="/serverName/lock";


    public static void init() throws IOException {

        zk = new ZooKeeper("192.168.197.129:4181,192.168.197.129:3181,192.168.197.129:2181",
                1000 * 60, new Watcher() {
            // 监控所有被触发的事件
            public void process(WatchedEvent event) {
                //logger.info("已经触发了 {} 事件！ ",event.getType());
                System.out.println("已经触发了" + event.getType() + "事件！");
            }
        });
    }


    public static void createData(String childDir1, CreateMode createMode,CountDownLatch cd) throws KeeperException, InterruptedException {
        zk.create(parentDir + "/"+childDir1, null,
                ZooDefs.Ids.OPEN_ACL_UNSAFE, createMode);
        cd.countDown();
    }
    /**
     * 获取指定结点的值
     *
     * @param path 节点路径
     * @throws Exception 链接未初始化、创建失败则抛出异常
     */
    public static byte[] getData(String path) throws Exception {
        if (zk != null) {
            byte[] result = null;
            if (path != null) {
                result = zk.getData(parentDir+"/"+path,true,null);
            }
            return result;
        } else {
            throw new Exception("zkClient没有初始化，请初始化");
        }
    }



    public static void main(String[]args) throws KeeperException, InterruptedException {


        try {
        init();
        CountDownLatch cd = new CountDownLatch(5);
        ThreadTest t1 = new ThreadTest(cd);
        ThreadTest t2 = new ThreadTest(cd);
        ThreadTest t3 = new ThreadTest(cd);
        ThreadTest t4 = new ThreadTest(cd);
        ThreadTest t5 = new ThreadTest(cd);
        t1.setName("sto2_");
        createData(t1.getName(),CreateMode.EPHEMERAL_SEQUENTIAL ,cd);
        t2.setName("sto3_");
            createData(t2.getName(),CreateMode.EPHEMERAL_SEQUENTIAL ,cd);
        t3.setName("sto4_");
            createData(t3.getName(),CreateMode.EPHEMERAL_SEQUENTIAL ,cd);
        t4.setName("sto5_");
            createData(t4.getName(),CreateMode.EPHEMERAL_SEQUENTIAL ,cd);
        t5.setName("sto6_");
            createData(t5.getName(),CreateMode.EPHEMERAL_SEQUENTIAL ,cd);
        cd.await();
        List<String> list= zk.getChildren(parentDir,true);
        String thread = null;
        long  index = 0;
        if(list==null||list.size()<1){
            System.out.println("没有锁");
            return;
        }
            for (String s:list) {

                String[] result = s.split("_");
                if(index == 0){
                    index = Integer.valueOf(result[1]);
                    thread = result[0];
                }else{
                    if(index - Integer.valueOf(result[1])>0) {
                        index = Integer.valueOf(result[1]);
                        thread = result[0];
                    }
                }
            }




        System.out.println("创建完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
