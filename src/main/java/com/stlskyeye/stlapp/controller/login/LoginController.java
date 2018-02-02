package com.stlskyeye.stlapp.controller.login;

import com.stlskyeye.stlapp.domain.PageEnum;
import com.stlskyeye.stlapp.domain.User;
import com.stlskyeye.stlapp.server.login.LoginService;
import com.stlskyeye.stlapp.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    @Autowired
    private LoginService  loginService;

    /**
     * 用户登录系统
     * @param user
     * @return
     */
    @RequestMapping("/login/login")
    public String login(HttpServletRequest request, HttpServletResponse response, User user){
        if(StringUtils.isEmpty(user.getPassword()) &&StringUtils.isEmpty(user.getUsername())){
            return PageEnum.LOGIN_PAGE.getPageUrl();
        }
        String token = loginService.checkUser(user);

        if(token ==null){
            return PageEnum.LOGIN_PAGE.getPageUrl();
        }else{
            CookieUtil.addCookie(response,"token",token);
        }
        CookieUtil.addCookie(response,"userName",user.getUsername());
        //CookieUtil.addCookie(response,"belongName",user.getBelongName());
        //CookieUtil.addCookie(response,"department",user.getDepartment());
        return PageEnum.INDEX_PAGE.getPageUrl();
    }

}
