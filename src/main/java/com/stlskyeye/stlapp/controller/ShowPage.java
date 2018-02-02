package com.stlskyeye.stlapp.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
@EnableAutoConfiguration
@ComponentScan
public class ShowPage {

    @RequestMapping(value="/page/{page}",method = RequestMethod.GET)
    public String  renderingPage(@PathVariable("page") String page){
        System.out.println(page);
        return page;
    }
}
