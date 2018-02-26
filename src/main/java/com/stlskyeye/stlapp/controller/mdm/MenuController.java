package com.stlskyeye.stlapp.controller.mdm;

import com.stlskyeye.stlapp.domain.Meun;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 对页面上的菜单进行操作类
 */
@Controller
@RequestMapping("/mdm")
public class MenuController {

    @RequestMapping("/loadMeun")
    @ResponseBody
    public List<Meun> loadMeun(/*@PathVariable(value = "{level}"  ,required=false )int level*/){
        Meun menu = new Meun();
        List<Meun> a = new ArrayList<Meun>();
        menu.setAppId("mdm");
        menu.setDescription("测试");
        menu.setLevel(1);
        menu.setMeunId("2222");
        menu.setMeunType("1");
        menu.setUrl("/page/index");
        menu.setIsHasSon("no");
        menu.setParentMeunId("1");
        menu.setMeunName("总控制台");
        menu.setMeunStyle("fa-tachometer");

        Meun menu2 = new Meun();
        menu2.setAppId("mdm");
        menu2.setDescription("测试");
        menu2.setLevel(1);
        menu2.setMeunId("3333");
        menu2.setMeunType("1");
        menu2.setUrl("/page/RedisSwitchManage");
        menu2.setIsHasSon("no");
        menu2.setParentMeunId("1");
        menu2.setMeunName("结算redis开关配置");
        menu2.setMeunStyle("fa-list-alt");

        Meun menu3 = new Meun();
        menu3.setAppId("mdm");
        menu3.setDescription("测试");
        menu3.setLevel(1);
        menu3.setMeunId("4444");
        menu3.setMeunType("1");
        menu3.setUrl("/page/login");
        menu3.setIsHasSon("no");
        menu3.setParentMeunId("1");
        menu3.setMeunName("登录");
        menu3.setMeunStyle("fa-list-alt");


        a.add(menu);
        a.add(menu2);
        a.add(menu3);



        //System.out.println(2);
        return a;
    }


}
