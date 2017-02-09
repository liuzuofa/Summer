package com.zuofa.summer.application;

import android.app.Application;

import com.zuofa.summer.utils.StaticClass;

import cn.bmob.v3.Bmob;

/**
 * Created by 刘祚发 on 2017/1/29.
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //Bmob初始化
        Bmob.initialize(this, StaticClass.Bmob_Application_ID);
    }
}
