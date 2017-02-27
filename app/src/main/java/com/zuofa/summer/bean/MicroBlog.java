package com.zuofa.summer.bean;
/*
 *  项目名：  Summer 
 *  包名：    com.zuofa.summer.bean
 *  文件名:   MicroBlog
 *  创建者:   Summers
 *  创建时间: 2017/2/120:10
 *  描述：    TODO
 */

import java.io.Serializable;
import java.util.List;

public class MicroBlog implements Serializable {

    private int id;
    private String name;
    private String nick;
    private String profile;
    private String weibo_content;
    //private List<String> weibo_photo;
    private String weibo_photo;
    private String weibo_date;
    private int admire_count;
    private int comment_count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getWeibo_content() {
        return weibo_content;
    }

    public void setWeibo_content(String weibo_info) {
        this.weibo_content = weibo_info;
    }

    public String getWeibo_photo() {
        return weibo_photo;
    }
    /*public List<String> getWeibo_photo() {
        return weibo_photo;
    }*/

    public void setWeibo_photo(String weibo_photo) {
        this.weibo_photo = weibo_photo;
    }
    /*public void setWeibo_photo(List<String> weibo_photo) {
        this.weibo_photo = weibo_photo;
    }*/

    public String getWeibo_date() {
        return weibo_date;
    }

    public void setWeibo_date(String weibo_date) {
        this.weibo_date = weibo_date;
    }

    public int getAdmire_count() {
        return admire_count;
    }

    public void setAdmire_count(int admire_count) {
        this.admire_count = admire_count;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    @Override
    public String toString() {
        return "MicroBlog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nick='" + nick + '\'' +
                ", profile='" + profile + '\'' +
                ", weibo_content='" + weibo_content + '\'' +
                ", weibo_photo='" + weibo_photo + '\'' +
                ", weibo_date='" + weibo_date + '\'' +
                ", admire_count=" + admire_count +
                ", comment_count=" + comment_count +
                '}';
    }
}
