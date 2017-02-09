package com.zuofa.summer.ui;
/*
 *  项目名：  Summer 
 *  包名：    com.zuofa.summer.ui
 *  文件名:   RegisterActivity
 *  创建者:   Summers
 *  创建时间: 2017/2/58:21
 *  描述：    TODO
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zuofa.summer.R;
import com.zuofa.summer.bean.User;


import java.io.UnsupportedEncodingException;

import okhttp3.Call;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText register_name;
    private EditText register_password;
    private EditText register_password1;
    private EditText student_id;
    private RadioGroup mRadioGroup;
    private Button register_submit;
    private String sex = "男";

    public RegisterActivity() throws UnsupportedEncodingException {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

    }

    private void initView() {
        register_name = (EditText) findViewById(R.id.register_name);
        register_password = (EditText) findViewById(R.id.register_password);
        register_password1 = (EditText) findViewById(R.id.register_password1);
        student_id = (EditText) findViewById(R.id.student_id);
        mRadioGroup = (RadioGroup) findViewById(R.id.mRadioGroup);
        register_submit = (Button) findViewById(R.id.register_submit);
        register_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_submit:
                //获取输入框的值
                String name = register_name.getText().toString().trim();
                String password = register_password.getText().toString().trim();
                String password1 = register_password1.getText().toString().trim();
                String studentId = student_id.getText().toString().trim();

                if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(password) &
                        !TextUtils.isEmpty(password1) & !TextUtils.isEmpty(studentId)) {
                    if (password.equals(password1)) {
                        //性别判断
                        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {

                                if (checkedId == R.id.rb_boy) {
                                    Log.e("summer", "男");
                                    sex = "男";
                                } else if (checkedId == R.id.rb_girl) {
                                    Log.e("summer", "女");
                                    sex = "女";
                                }
                            }
                        });
                        User user = new User();
                        user.setName(name);
                        user.setPassword(password);
                        user.setSex(sex);
                        user.setStudentid(studentId);
                        //Log.e("summer",user.toString());
                        /*try {
                            Response response =OkHttpUtils
                                    .get()
                                    .url("http://192.168.2.101:8080/summer/RegisterServlet")
                                    .addParams("json", new Gson().toJson(user))
                                    .build()
                                    .execute();
                            if (Integer.parseInt(response.body().string())>0) {
                                Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e) {
                            Log.e("summer","出错");
                            e.printStackTrace();
                        }*/

                        OkHttpUtils
                                .post()
                                .url("http://192.168.2.101:8080/summer/RegisterServlet")
                                .addParams("json", new Gson().toJson(user))
                                .build()
                                .execute(new StringCallback() {
                                    @Override
                                    public void onResponse(String response, int id) {
                                        Log.e("summer", response);
                                        try {
                                            if (Integer.parseInt(response) > 0) {
                                                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                                finish();
                                            } else {
                                                Toast.makeText(RegisterActivity.this, "注册失败,此用户已经存在", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (NumberFormatException e) {
                                            e.printStackTrace();
                                        }

                                    }

                                    @Override
                                    public void onError(Call call, Exception e, int id) {
                                        Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    } else {
                        Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
