package com.stlskyeye.stlapp.mapper;

import com.stlskyeye.stlapp.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface LoginMapper {
    /**
     * 通过用户名密码查询用户
     * @param user
     * @return
     */
    public List<User> searchUser(User user);
}
