package com.stlskyeye.stlapp.core;

import com.fasterxml.jackson.databind.util.ClassUtil;
import com.stlskyeye.stlapp.core.quarzt.Quarzt;
import com.stlskyeye.stlapp.utils.PackageUtils;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


@Configuration
public class QuarztConfig {
    @Value("${quarzt.scan.package}")
    private String jobPackage ;
    /**
     * attention:
     * Details：配置定时任务*/

    public HashMap<Quarzt,String> getQuarztAnnotion () throws ClassNotFoundException{
        Set<String> classNames = PackageUtils.getClassName(jobPackage,false);
        HashMap<Quarzt,String> qMap =  new HashMap<Quarzt,String>();
        if (classNames != null) {
            for (String className : classNames) {
                if(!StringUtils.isEmpty(className)){

                    Quarzt q  =Class.forName(className).getAnnotation(Quarzt.class);
                    if(q!=null){
                        qMap.put(q,className);
                    }
                }else{
                    throw new ClassNotFoundException();
                }
            }
        }
        return qMap;
    }


    @Bean(name = "jobDetail")
    public MethodInvokingJobDetailFactoryBean detailFactoryBean() throws ClassNotFoundException {// ScheduleTask为需要执行的任务


        HashMap<Quarzt,String>qMap =  getQuarztAnnotion ();
        for (Quarzt q:qMap.keySet()) {
            for(int i=0;i<q.threadNum();i++) {
                MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
                jobDetail.setConcurrent(true);// 是否并发执行
                jobDetail.setName(q.name()+"-"+i);// 设置任务的名字
                jobDetail.setGroup("overTimeNoticeJobGroup");// 设置任务的分组，这些属性都可以存储在数据库中，在多任务的时候使用
                jobDetail.setTargetObject(Class.forName(qMap.get(q)));
                jobDetail.setTargetMethod("execute");
            }
        }
        return null;
    }

    /**
     * attention:
     * Details：配置定时任务的触发器，也就是什么时候触发执行定时任务*/

    @Bean(name = "jobTrigger")
    public CronTriggerFactoryBean cronJobTrigger() {
        //List<Class<?>> returnClassList = ClassUt
       // Quarzt.class.getAnnotation(Quarzt.class);
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();

        CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
        tigger.setJobDetail(jobDetail.getObject());
        tigger.setCronExpression("* 0/5 * * * ?");// 初始时的cron表达式  ，没5分钟执行一次
        tigger.setName("overTimeNoticeTrigger");// trigger的name
        return tigger;

    }

    /**
     * attention:
     * Details：定义quartz调度工厂*/

    @Bean(name = "scheduler")
    public SchedulerFactoryBean schedulerFactory(Trigger cronJobTrigger) {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        // 用于quartz集群,QuartzScheduler 启动时更新己存在的Job
        bean.setOverwriteExistingJobs(true);
        // 延时启动，应用启动1秒后
        bean.setStartupDelay(1);
        // 注册触发器
        bean.setTriggers(cronJobTrigger);
        return bean;
    }

}
