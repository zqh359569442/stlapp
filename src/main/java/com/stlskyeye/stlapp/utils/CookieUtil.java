package com.stlskyeye.stlapp.utils;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    /**
     * 添加cookie
     * @param response
     * @param name
     * @param value
     */
    @RequestMapping("/addCookie")
    public static void addCookie(HttpServletResponse response, String name, String value){
        Cookie cookie = new Cookie(name.trim(), value.trim());
        cookie.setMaxAge(30 * 60);// 设置为30min
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 通过cookie 的key 返回 value
     * @param request
     * @param key
     * @return
     */
    public static String getCookieByKey(HttpServletRequest request,String key){
        Cookie[] cookies =  request.getCookies();

        if (cookies ==null ||cookies.length < 1|| key ==null) {
            return null;
        }
        for (Cookie cookie: cookies) {
            if(key.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return  null;
    }
}
