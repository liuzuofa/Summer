package com.zuofa.summer.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zuofa.summer.R;
import com.zuofa.summer.adapter.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private LayoutInflater mInflater;
    private List<String> mTitleList = new ArrayList<>();
    private View view, view1,view2;//页卡视图
    private List<View> mViewList =new ArrayList<>();//页卡视图合集


    public static HomeFragment newInstance(String argument) {
        Bundle bundle = new Bundle();
        bundle.putString("argument", argument);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_activity, container, false);
        initView();
        return view;
    }


    private void initView() {
        mViewPager = (ViewPager)view.findViewById(R.id.view_page);
        mTabLayout = (TabLayout)view.findViewById(R.id.tabs);

        mInflater = LayoutInflater.from(this.getContext());
        view1 = mInflater.inflate(R.layout.fragment_test,null);
        view2 = mInflater.inflate(R.layout.fragment_test,null);

        //添加页卡视图
        mViewList.add(view1);
        mViewList.add(view2);

        //添加页卡标题
        mTitleList.add("即将开始");
        mTitleList.add("正在进行");

        //添加页卡标题
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));

        MyPagerAdapter mNewsAdapter = new MyPagerAdapter(mViewList,mTitleList);
        mViewPager.setAdapter(mNewsAdapter);//给ViewPage设置适配器
        mTabLayout.setupWithViewPager(mViewPager);
    }


}
