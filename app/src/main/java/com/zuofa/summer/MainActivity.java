package com.zuofa.summer;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.zuofa.summer.fragment.HomeFragment;
import com.zuofa.summer.fragment.DynamicFragment;
import com.zuofa.summer.fragment.NewsFragment;
import com.zuofa.summer.fragment.UserFragment;
import com.zuofa.summer.ui.SendBlogActivity;


public class MainActivity extends AppCompatActivity implements
        BottomNavigationBar.OnTabSelectedListener {

    private TextView mTextView;
    private BottomNavigationBar mNavigationBar;
    private MenuItem menuItem;

    //定义主界面的四个Fragment
    private NewsFragment mNewsFragment;
    private HomeFragment mHomeFragment;
    private DynamicFragment mDynamicFragment;
    private UserFragment mUserFragment;
    private Fragment currentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }


    private void initView() {
        mTextView = (TextView) findViewById(R.id.text_view);
        mNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigationbar);
        mNavigationBar.addItem(new BottomNavigationItem(R.drawable.home, "校博"))
                .addItem(new BottomNavigationItem(R.drawable.activity, "消息"))
                .addItem(new BottomNavigationItem(R.drawable.dynamic, "信息"))
                .addItem(new BottomNavigationItem(R.drawable.user, "我"))
                .setFirstSelectedPosition(0)//设置默认选择的item
                .setMode(BottomNavigationBar.MODE_FIXED)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                .initialise();//初始化
        mNavigationBar.setTabSelectedListener(this);
    }

    private void initData() {
        if (mNewsFragment == null) {
            mNewsFragment = NewsFragment.newInstance("NewsFragment");
        }
        if (!mNewsFragment.isAdded()) {
            // 提交事务
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout, mNewsFragment).commit();
            // 记录当前Fragment
            currentFragment = mNewsFragment;
        }
    }

    @Override
    public void onTabSelected(int position) {
        switch (position) {
            case 0:
                if (mNewsFragment == null) {
                    mNewsFragment = NewsFragment.newInstance("NewsFragment");
                }
                addOrShowFragment(mNewsFragment);
                menuItem.setVisible(false);
                break;
            case 1:
                if (mDynamicFragment == null) {
                    mDynamicFragment = DynamicFragment.newInstance("DynamicFragment");
                }
                addOrShowFragment(mDynamicFragment);
                menuItem.setVisible(false);
                break;
            case 2:
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment.newInstance("HomeFragment");
                }
                addOrShowFragment(mHomeFragment);
                menuItem.setVisible(true);
                break;
            case 3:
                if (mUserFragment == null) {
                    mUserFragment = UserFragment.newInstance("UserFragment");
                }
                addOrShowFragment(mUserFragment);
                menuItem.setVisible(false);
                break;
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    /**
     * 添加或者显示 fragment
     *
     * @param fragment
     */
    private void addOrShowFragment(Fragment fragment) {
        if (currentFragment == fragment)
            return;
        if (!fragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().hide(currentFragment)
                    .add(R.id.frame_layout, fragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .hide(currentFragment).show(fragment).commit();
        }
        currentFragment = fragment;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        menuItem =menu.findItem(R.id.send_blog);
        menuItem.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.send_blog:
                Toast.makeText(MainActivity.this,"点击了发微博按钮",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,SendBlogActivity.class));
                break;
        }
        return true;
    }
}
