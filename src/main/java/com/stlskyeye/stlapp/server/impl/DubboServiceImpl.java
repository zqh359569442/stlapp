package com.stlskyeye.stlapp.server.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.stlskyeye.stlapp.server.IDubboService;

@Service(version="1.0.0")
public class DubboServiceImpl implements IDubboService{

    public String test(){
        return  "good man";
    }

}
