package com.stlskyeye.stlapp.server.login.impl;

import com.stlskyeye.stlapp.domain.User;
import com.stlskyeye.stlapp.mapper.LoginMapper;
import com.stlskyeye.stlapp.server.login.LoginService;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 校验用户登录
     * @param user
     */
    @Override
    public String checkUser(User user) {
        List<User> userList = loginMapper.searchUser(user);
        String token =null;
        if(userList!=null && userList.size()>0){
            //需要生成TOKEN 存入到redis中
            User u = userList.get(0);
            try {
                token = new String(Base64.encodeBase64(DigestUtils
                        .md5((user.getBelongName() +user.getUsername()).getBytes("UTF-8"))));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            JSONObject json = JSONObject.fromObject(u);
            redisTemplate.opsForValue().set(token,json.toString(),30, TimeUnit.MINUTES);
        }

        return token;
    }
}
