package com.stlskyeye.stlapp.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate redisTemplate;


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        Cookie [] cookies = request.getCookies();
        if(cookies !=null && cookies.length>0) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())){
                    String json = (String) redisTemplate.opsForValue().get(cookie.getValue());
                    if(StringUtils.isEmpty(json)){
                        response.sendRedirect("/page/login");
                        return false;
                    }
                    return true;
                }
            }
        }
        response.sendRedirect("/page/login");
        return false;
    }


    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
