package com.zuofa.summer.ui;
/*
 *  项目名：  Summer 
 *  包名：    com.zuofa.summer.ui
 *  文件名:   InformationAcvtivity
 *  创建者:   Summers
 *  创建时间: 2017/2/915:52
 *  描述：    TODO
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zuofa.summer.R;
import com.zuofa.summer.application.BaseApplication;
import com.zuofa.summer.bean.User;
import com.zuofa.summer.utils.StaticClass;
import com.zuofa.summer.view.CustomDialog;

import okhttp3.Call;

public class InformationAcvtivity extends BaseActivity implements View.OnClickListener {

    private ImageView change_profile;
    private TextView change_nick;
    private TextView change_username;
    private LinearLayout change_QRcode;
    private TextView change_introduction;

    private CustomDialog customDialog;
    private EditText dialog_et;
    private TextView btn_change_confirm;
    private TextView btn_change_cancel;

    private User user;
    private String status;
    private boolean isUpdate = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        initView();
    }

    private void initView() {

        BaseApplication application = (BaseApplication) getApplication();
        user = application.getUser();
        Log.e("summer", user.toString());
        change_profile = (ImageView) findViewById(R.id.change_profile);
        Glide.with(this).load(StaticClass.PROFILE_URL+user.getProfile()).into(change_profile);
        change_profile.setOnClickListener(this);

        change_nick = (TextView) findViewById(R.id.change_nick);
        change_nick.setText(user.getNick());
        change_nick.setOnClickListener(this);

        change_username = (TextView) findViewById(R.id.change_username);
        change_username.setText(user.getName());

        change_QRcode = (LinearLayout) findViewById(R.id.change_QRcode);
        change_QRcode.setOnClickListener(this);

        change_introduction = (TextView) findViewById(R.id.change_introduction);
        change_introduction.setText(user.getIntroduction());
        change_introduction.setOnClickListener(this);

        customDialog = new CustomDialog(this, 200, 200,
                R.layout.dialog_change_info, R.style.ActionSheetDialogStyle, Gravity.CENTER, R.style.ChangeInfoDialogAnimation);
        customDialog.setCancelable(false);
        btn_change_confirm = (TextView) customDialog.findViewById(R.id.btn_change_confirm);
        btn_change_confirm.setOnClickListener(this);
        dialog_et = (EditText) customDialog.findViewById(R.id.dialog_et);
        btn_change_cancel = (TextView) customDialog.findViewById(R.id.btn_change_cancel);
        btn_change_cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_nick:
                status = "nick";
                dialog_et.setText("");
                customDialog.show();
                break;
            case R.id.change_QRcode:
                startActivity(new Intent(InformationAcvtivity.this,RQcodeActivity.class));
                break;
            case R.id.change_introduction:
                status = "introduction";
                dialog_et.setText("");
                customDialog.show();
                break;
            case R.id.btn_change_confirm:
                Log.e("summer", status);
                changeUserInfo(status);
                customDialog.dismiss();
                break;
            case R.id.btn_change_cancel:
                customDialog.dismiss();
                break;
        }

    }

    private void changeUserInfo(String params) {
        String s = dialog_et.getText().toString().trim();
       /* String ss ="";
        try {
            ss = URLEncoder.encode(s, "UTF-8");
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        Log.e("summer", s);

        OkHttpUtils
                .post()
                .url(StaticClass.URL+"ChangeUserInfoServlet")
                .addParams("params", params)
                .addParams("value", s)
                .addParams("name", user.getName())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(InformationAcvtivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (Integer.parseInt(response.trim()) > 0) {
                            Toast.makeText(InformationAcvtivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            isUpdate = true;
                        } else {
                            Toast.makeText(InformationAcvtivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        if (isUpdate) {
            if ("nick".equals(params)) {
                change_nick.setText(s);
            } else if ("introduction".equals(params)) {
                change_introduction.setText(s);
            }
        }
    }
}
