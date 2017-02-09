package com.zuofa.summer.ui;
/*
 *  项目名：  Summer 
 *  包名：    com.zuofa.summer.ui
 *  文件名:   ViewUserActivity
 *  创建者:   Summers
 *  创建时间: 2017/2/815:52
 *  描述：    TODO
 */

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zuofa.summer.R;
import com.zuofa.summer.adapter.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewUserActivity extends BaseActivity {

    private TextView tv_weibo,tv_care,tv_fans;
    private ViewPager mViewPager;
    private List<View> mViewList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewuser);
        inintView();
    }

    private void inintView() {
        tv_weibo = (TextView)findViewById(R.id.tv_weibo);
        tv_care = (TextView) findViewById(R.id.tv_care);
        tv_fans = (TextView) findViewById(R.id.tv_fans);

        mViewList=new ArrayList<>();//页卡视图合集
        mViewList.add(LayoutInflater.from(this).inflate(R.layout.my_weibo_viewpage,null));
        mViewList.add(LayoutInflater.from(this).inflate(R.layout.my_care_viewpage,null));
        mViewList.add(LayoutInflater.from(this).inflate(R.layout.my_fans_viewpage,null));
        mViewPager = (ViewPager) findViewById(R.id.user_view_page);

        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(mViewList);
        mViewPager.setAdapter(myPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        tv_weibo.setTextColor(0xff3366cc);
                        tv_care.setTextColor(0xff000000);
                        tv_fans.setTextColor(0xff000000);
                        break;
                    case 1:
                        tv_weibo.setTextColor(0xff000000);
                        tv_care.setTextColor(0xff3366cc);
                        tv_fans.setTextColor(0xff000000);
                        break;
                    case 2:
                        tv_weibo.setTextColor(0xff000000);
                        tv_care.setTextColor(0xff000000);
                        tv_fans.setTextColor(0xff3366cc);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
