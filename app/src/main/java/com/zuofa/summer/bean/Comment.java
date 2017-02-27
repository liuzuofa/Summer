package com.zuofa.summer.bean;

/**
 * Created by liuzu on 2017/2/21.
 */

public class Comment {
    private String name;
    private String profile;
    private String nick;
    private String date;
    private String content;

    public Comment(){

    }

    public Comment(String name, String profile, String nick, String date, String content) {
        this.name = name;
        this.profile = profile;
        this.nick = nick;
        this.date = date;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
