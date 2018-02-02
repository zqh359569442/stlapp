package com.stlskyeye.stlapp.server.login;

import com.stlskyeye.stlapp.domain.User;

public interface LoginService {
    /**
     * 校验用户登录
     * @param user
     */
    public String checkUser(User user);

}
