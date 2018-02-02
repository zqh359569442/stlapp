package com.stlskyeye.stlapp.server;

import com.stlskyeye.stlapp.domain.Redis;

import java.util.List;

public interface IRedisServer {

    /**
     * 查询redis集群信息
     */
    public List<Redis> searchRedisInfoAll();

    /**
     * 对redis开关信息进行维护
     * @param oper
     * @param redis
     * @return
     */
    public String operRedisInfo(String oper,Redis redis);



}
