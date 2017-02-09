package com.zuofa.summer.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by 刘祚发 on 2017/1/26.
 */
public class User extends BmobUser {


    private boolean sex;
    private Integer studentId;
    private String nick;//昵称
    private String brief_introduction;//简介
    private String profile;//头像地址

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getBrief_introduction() {
        return brief_introduction;
    }

    public void setBrief_introduction(String brief_introduction) {
        this.brief_introduction = brief_introduction;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }
}
