package com.stlskyeye.stlapp.domain;

/**
 * 菜单对象。
 */
public class Meun {
    private String meunId;      //菜单ID
    private String appId;       //菜单所属应用ID
    private String meunName;    //菜单名称
    private String url;         //菜单连接
    private String description; //描述
    private String parentMeunId;//所属父菜单ID
    private int level;          //等级
    private String meunType;    //菜单类型
    private String isHasSon;    //是否有子菜单
    private String meunStyle;    //菜单样式

    public String getMeunId() {
        return meunId;
    }

    public void setMeunId(String meunId) {
        this.meunId = meunId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMeunName() {
        return meunName;
    }

    public void setMeunName(String meunName) {
        this.meunName = meunName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParentMeunId() {
        return parentMeunId;
    }

    public void setParentMeunId(String parentMeunId) {
        this.parentMeunId = parentMeunId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getMeunType() {
        return meunType;
    }

    public void setMeunType(String meunType) {
        this.meunType = meunType;
    }

    public String getIsHasSon() {
        return isHasSon;
    }

    public void setIsHasSon(String isHasSon) {
        this.isHasSon = isHasSon;
    }


    public String getMeunStyle() {
        return meunStyle;
    }

    public void setMeunStyle(String meunStyle) {
        this.meunStyle = meunStyle;
    }
}
