package com.stlskyeye.stlapp.domain;

public enum PageEnum {
    LOGIN_PAGE("login","登录页面"),
    INDEX_PAGE("index","首页");

    PageEnum(String pageUrl,String pageName){
        this.pageUrl = pageUrl;
        this.pageName = pageName;
    }


    private String pageUrl;
    private String pageName;

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }
}
