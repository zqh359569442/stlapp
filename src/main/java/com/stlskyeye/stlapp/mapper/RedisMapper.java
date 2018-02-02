package com.stlskyeye.stlapp.mapper;

import com.stlskyeye.stlapp.domain.Redis;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RedisMapper {

    public void insertRedisInfo(Redis redis);


    public void updateRedisInfo(Redis redis);


    public void delRedisInfo(String id);

    public List<Redis> searchRedisInfoAll();
}
