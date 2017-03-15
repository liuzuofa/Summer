package com.zuofa.summer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zuofa.summer.application.BaseApplication;
import com.zuofa.summer.bean.User;
import com.zuofa.summer.fragment.HomeFragment;
import com.zuofa.summer.fragment.DynamicFragment;
import com.zuofa.summer.fragment.NewsFragment;
import com.zuofa.summer.fragment.UserFragment;
import com.zuofa.summer.ui.SendBlogActivity;
import com.zuofa.summer.utils.L;
import com.zuofa.summer.utils.StaticClass;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;
import okhttp3.Call;


public class MainActivity extends AppCompatActivity implements
        BottomNavigationBar.OnTabSelectedListener {

    private String TAG = "summer";
    private static final String token1 = "Qb3fG5+RsJO4gH/wpIaaxcJeP3+qCyuvEqWS0ZAu7I5oVzEogK0yY59uMeievD5PS/MoRzBunfbQvVv288ySOw==";
    private static final String token2 = "ZvOFnVq/e1SkcJgekE75PmzZzlYrI8C/l8ypzInwGZGPMZ/Jv5TlmZpyhKYE+Yv5bdm6VPxM+MBoicsejIVc2g==";
    //private TextView mTextView;
    private BottomNavigationBar mNavigationBar;
    private MenuItem menuItem;

    //定义主界面的四个Fragment
    private NewsFragment mNewsFragment;
    private HomeFragment mHomeFragment;
    private DynamicFragment mDynamicFragment;
    private UserFragment mUserFragment;
    private Fragment currentFragment;

    private User user;
    private User rongCloudUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }


    private void initView() {
        BaseApplication application = (BaseApplication) getApplication();
        user = application.getUser();
        //mTextView = (TextView) findViewById(R.id.text_view);
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

        //融云用户连接
        connectRongServer(user.getToken());
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String s) {
                OkHttpUtils
                        .post()
                        .addParams("method","getUser")
                        .addParams("name",s)
                        .url(StaticClass.URL+"UserServlet")//
                        .build()//
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.e("UserId is ",response);
                                rongCloudUser = new Gson().fromJson(response,User.class);
                            }
                        });
                if (rongCloudUser != null){
                    return new UserInfo(rongCloudUser.getName(),rongCloudUser.getNick(),Uri.parse(StaticClass.PROFILE_URL+rongCloudUser.getProfile()));
                }
                Log.e("MainActivity", "UserId is ：" + s);
                return null;
            }
        }, true);
    }


    private void initData() {
        if (mHomeFragment == null) {
            mHomeFragment = HomeFragment.newInstance("HomeFragment");
        }
        if (!mHomeFragment.isAdded()) {
            // 提交事务
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout, mHomeFragment).commit();
            // 记录当前Fragment
            currentFragment = mHomeFragment;
        }
    }

    /**
     * 连接融云服务器
     *
     * @param token
     */
    private void connectRongServer(String token) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {

            @Override
            public void onSuccess(String userId) {
                Toast.makeText(MainActivity.this, "connet server success",
                        Toast.LENGTH_SHORT).show();
                Log.e(TAG, "connect success userid is :" + userId);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e(TAG,
                        "connect failure errorCode is :" + errorCode.getValue());
            }

            @Override
            public void onTokenIncorrect() {
                Log.e(TAG, "token is error , please check token and appkey ");
            }
        });
    }

    @Override
    public void onTabSelected(int position) {
        switch (position) {
            case 0:
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment.newInstance("HomeFragment");
                }
                addOrShowFragment(mHomeFragment);
                menuItem.setVisible(true);
                break;
            case 1:
                if (mDynamicFragment == null) {
                    mDynamicFragment = DynamicFragment.newInstance("DynamicFragment");
                }
                addOrShowFragment(mDynamicFragment);
                menuItem.setVisible(false);
                break;
            case 2:
                if (mNewsFragment == null) {
                    mNewsFragment = NewsFragment.newInstance("NewsFragment");
                }
                addOrShowFragment(mNewsFragment);
                menuItem.setVisible(false);
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
        getMenuInflater().inflate(R.menu.toolbar, menu);
        menuItem = menu.findItem(R.id.send_blog);
        menuItem.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.send_blog:
                Toast.makeText(MainActivity.this, "点击了发微博按钮", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, SendBlogActivity.class));
                break;
        }
        return true;
    }
}
