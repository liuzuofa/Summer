package com.zuofa.summer.application;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zuofa.summer.bean.User;
import com.zuofa.summer.utils.StaticClass;

import java.util.concurrent.TimeUnit;

import cn.bmob.v3.Bmob;
import okhttp3.OkHttpClient;

/**
 * Created by 刘祚发 on 2017/1/29.
 */
public class BaseApplication extends Application {

    User user ;

    @Override
    public void onCreate() {
        super.onCreate();
        //Bmob初始化
        Bmob.initialize(this, StaticClass.Bmob_Application_ID);

        //初始化Okhttp
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置

                .build();

        OkHttpUtils.initClient(okHttpClient);
    }

    public User getUser(){
        return user;
    }
    public void setUser(User user){
        this.user =  user;
    }
}
