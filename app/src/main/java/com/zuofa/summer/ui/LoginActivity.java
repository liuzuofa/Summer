package com.zuofa.summer.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.zuofa.summer.MainActivity;
import com.zuofa.summer.R;
import com.zuofa.summer.bean.User;
import com.zuofa.summer.utils.L;
import com.zuofa.summer.utils.ShareUtils;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{


    private EditText et_name;
    private EditText et_password;
    private CheckBox keep_password;
    private Button login;
    private Button register;

    private String mUserName;
    private String mPassWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_password = (EditText) findViewById(R.id.et_password);
        keep_password = (CheckBox) findViewById(R.id.keep_password);
        login = (Button) findViewById(R.id.btn_login);
        login.setOnClickListener(this);
        register = (Button) findViewById(R.id.btn_register);
        register.setOnClickListener(this);
        boolean isKeep = ShareUtils.getBoolean(this, "keeppass", false);
        keep_password.setChecked(isKeep);

        if(isKeep){
            String name = ShareUtils.getString(this, "name", "");
            String password = ShareUtils.getString(this, "password", "");
            et_name.setText(name);
            et_password.setText(password);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_register:
                register();
                break;
        }
    }

    private void login() {
        mUserName = et_name.getText().toString().trim();
        mPassWord = et_password.getText().toString().trim();
        if (!TextUtils.isEmpty(mUserName) ){
            if (!TextUtils.isEmpty(mPassWord)) {
                User user = new User();
                user.setUsername(mUserName);
                user.setPassword(mPassWord);
                user.login(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null) {
                            //Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("user", user);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }else {
                            L.e(e.toString());
                        }
                    }
                });
            }else {
                Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this,"用户名不能为空",Toast.LENGTH_SHORT).show();
        }


    }

    private void register() {
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
    }

    //假设我现在输入用户名和密码，但是我不点击登录，而是直接退出了
    @Override
    protected void onDestroy() {
        super.onDestroy();

        //保存状态
        ShareUtils.putBoolean(this, "keeppass", keep_password.isChecked());

        //是否记住密码
        if (keep_password.isChecked()) {
            //记住用户名和密码
            ShareUtils.putString(this, "name", et_name.getText().toString().trim());
            ShareUtils.putString(this, "password", et_password.getText().toString().trim());
        } else {
            ShareUtils.deleShare(this, "name");
            ShareUtils.deleShare(this, "password");
        }
    }


}