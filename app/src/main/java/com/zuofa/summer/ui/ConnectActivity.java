package com.zuofa.summer.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zuofa.summer.R;
import com.zuofa.summer.utils.StaticClass;

import okhttp3.Call;

/**
 * Created by 刘祚发 on 2017/1/30.
 */
public class ConnectActivity extends BaseActivity {

    private TextView connect_theme;
    private TextView connect_content;
    private Button connect_submit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        initView();
    }

    private void initView() {
        connect_theme = (TextView) findViewById(R.id.connect_theme);
        connect_content = (TextView) findViewById(R.id.connect_content);
        connect_submit = (Button) findViewById(R.id.connect_submit);
        connect_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String theme = connect_theme.getText().toString().trim();
                String content = connect_content.getText().toString().trim();
                OkHttpUtils
                        .post()
                        .url(StaticClass.URL+"ConnectServlet")
                        .addParams("theme", theme)
                        .addParams("content",content)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(ConnectActivity.this,"提交失败",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                if (Integer.parseInt(response.trim()) >0) {
                                    Toast.makeText(ConnectActivity.this,"提交成功",Toast.LENGTH_SHORT).show();
                                    finish();
                                }else {
                                    Toast.makeText(ConnectActivity.this,"提交失败",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }


}
