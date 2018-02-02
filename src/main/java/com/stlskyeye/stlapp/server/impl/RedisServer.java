package com.stlskyeye.stlapp.server.impl;

import com.stlskyeye.stlapp.domain.Redis;
import com.stlskyeye.stlapp.mapper.RedisMapper;
import com.stlskyeye.stlapp.server.IRedisServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class RedisServer implements IRedisServer{
    @Autowired
    private RedisMapper redisMapper;
    @Autowired
    private StringRedisTemplate redislogTemplate;

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 新增
     * @return List<Redis>
     */
    public List<Redis> searchRedisInfoAll(){
        List<Redis> result =  redisMapper.searchRedisInfoAll();
        return  result;
    }

    /**
     * 对redis开关信息做增删改操作
     * @param oper
     * @param redis
     * @return
     */
    public String operRedisInfo(String oper, Redis redis) {

        if("add".equals(oper)){//新增操作
            redis.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            redis.setCreateTime(new Timestamp(new Date().getTime()));
            redisMapper.insertRedisInfo(redis);
        }else if("del".equals(oper)){
            redisMapper.delRedisInfo(redis.getId());

        }else if("edit".equals(oper)){
            redis.setModifyTime(new Timestamp(new Date().getTime()));
            redisMapper.updateRedisInfo(redis);
            //如果有修改开关值，则需要修改redis数据
            if(!StringUtils.isEmpty(redis.getSwitchStatus())){
                if("No".equals(redis.getSwitchStatus().trim())){
                    redislogTemplate.opsForValue().set("StlClearKeyJob_"+redis.getSwitchName()+"_SWITCH","ON");
                }else if("Yes".equals(redis.getSwitchStatus().trim())){
                    redislogTemplate.opsForValue().set("StlClearKeyJob_"+redis.getSwitchName()+"_SWITCH","OFF");
                }
            }
        }else{
            //非法操作。。后期可以根据用户IP记录入库
        }
        return null;
    }
}
