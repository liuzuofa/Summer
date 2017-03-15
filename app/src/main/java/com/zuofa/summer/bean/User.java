package com.zuofa.summer.bean;


import java.io.Serializable;

/**
 * Created by 刘祚发 on 2017/1/26.
 */
public class User implements Serializable {

    private int id;
    private String name;
    private String password;
    private String nick;
    private String sex;
    private String profile;
    private String studentid;
    private String introduction;
    private String token;

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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getNick() {
        return nick;
    }
    public void setNick(String nick) {
        this.nick = nick;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getProfile() {
        return profile;
    }
    public void setProfile(String profile) {
        this.profile = profile;
    }
    public String getStudentid() {
        return studentid;
    }
    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", nick='" + nick + '\'' +
                ", sex='" + sex + '\'' +
                ", profile='" + profile + '\'' +
                ", studentid='" + studentid + '\'' +
                ", introduction='" + introduction + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
