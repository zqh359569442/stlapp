package com.stlskyeye.stlapp.threadtest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Test {

    @org.junit.Test
    public static void test(){



    }

    public static void main(String []args){
        test();
    }



    public static void testCountDownLatch(){
        CountDownLatch cd = new CountDownLatch(4);
        ThreadTest t1 = new ThreadTest(cd);
        ThreadTest t2 =new ThreadTest(cd);

        t1.setName("Tsto");
        t2.setName("Tyto");
        RunableTest r1 = new RunableTest(cd);
        RunableTest r2 = new RunableTest(cd);
        Thread t3 = new Thread(r1,"Rsto");
        Thread t4= new Thread(r2,"Ryto");
        t3.start();
        t4.start();
        t1.start();
        t2.start();
        try {
            cd.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("zzzzz");
    }

    //手痒

    public void sho(){
        String s = "opt,max,tpo,hi,tpo,hide,ih,pot,oppt,opto";
        String [] a = s.split(",");
        List<String> list=new ArrayList<String>();
        for (int i=0;i<a.length;i++){
            StringBuffer group = new StringBuffer();
            if(a[i]==null){
                continue;
            }else{
                group.append(a[i]);
            }
            for(int j=i+1;j<a.length;j++){

                if(a[j] == null){
                    continue;
                }
                if(a[i].length() == a[j].length()){
                    char[] aa = a[i].toCharArray();
                    StringBuffer qq = new StringBuffer(a[j]) ;
                    int result = 0;
                    for (char m :aa){
                        boolean index = true;
                        if (qq.toString().indexOf(m) != -1) {
                            qq.replace(qq.toString().indexOf(m),qq.toString().indexOf(m)+1,"?");
                            result++;
                        }
                    }
                    if(result ==aa.length){
                        group.append(","+a[j]);
                        a[j]=null;
                    }
                }else{
                    continue;
                }
            }
            list.add(group.toString());
        }
        for (String ll: list) {
            System.out.println(ll);
        }
    }
}
