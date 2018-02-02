package com.stlskyeye.stlapp.controller;

import com.stlskyeye.stlapp.domain.Redis;
import com.stlskyeye.stlapp.server.impl.RedisServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RedisOperController {

    @Autowired
    private RedisServer redisServer;

    /**
     * redis开发信息查询
     * @return
     */
    @RequestMapping(value = "redis/search", method = RequestMethod.GET)
    @ResponseBody
    public List<Redis> searchRedis(){
        List<Redis> result = redisServer.searchRedisInfoAll();
        return result;
    }

    /**
     * redis开发信息增 删 改操作
     * @return
     */
    @RequestMapping(value = "redis/edit", method = RequestMethod.POST)
    public void oper(@RequestParam String oper, Redis redis){
        if(redis ==null){
            return;
        }
        redisServer.operRedisInfo(oper,redis);
    }
}
