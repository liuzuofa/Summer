package com.zuofa.summer.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zuofa.summer.R;
import com.zuofa.summer.adapter.MyListViewAdapter;
import com.zuofa.summer.application.BaseApplication;
import com.zuofa.summer.bean.Comment;
import com.zuofa.summer.bean.MicroBlog;
import com.zuofa.summer.bean.User;
import com.zuofa.summer.utils.L;
import com.zuofa.summer.utils.PicassoUtils;
import com.zuofa.summer.utils.StaticClass;
import com.zuofa.summer.utils.SystemUtils;
import com.zuofa.summer.utils.UtilTools;
import com.zuofa.summer.view.CustomDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

/**
 * Created by liuzu on 2017/2/21.
 */

public class CommentActivity extends AppCompatActivity implements View.OnClickListener{

    private MicroBlog microBlog;
    private CircleImageView comment_weibo_profile;
    private TextView comment_weibo_name;
    private TextView comment_weibo_time;
    private TextView comment_phone_type;
    private TextView comment_weibo_content;
    private ListView listView;
    private MyListViewAdapter adapter;
    private List<Comment> commentList =new ArrayList<>();

    private LinearLayout comment;
    private EditText input_comment;
    private TextView btn_publish_comment;
    private CustomDialog commentDialog;
    private String time;

    private User user;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        initView();
    }

    private void initView() {
        BaseApplication application = (BaseApplication) getApplication();
        user = application.getUser();
        comment_weibo_profile = (CircleImageView) findViewById(R.id.comment_weibo_profile);
        comment_weibo_name = (TextView) findViewById(R.id.comment_weibo_name);
        comment_weibo_time = (TextView) findViewById(R.id.comment_weibo_time);
        comment_phone_type = (TextView) findViewById(R.id.comment_phone_type);
        comment_weibo_content = (TextView) findViewById(R.id.comment_weibo_content);

        microBlog = (MicroBlog) getIntent().getSerializableExtra("blog");
        PicassoUtils.loadImageViewHolder(this, StaticClass.URL.substring(0, StaticClass.URL.length() - 1) + microBlog.getProfile(),
                R.drawable.add_pic, R.drawable.add_pic, comment_weibo_profile);
        comment_weibo_name.setText(microBlog.getNick());
        comment_weibo_time.setText(microBlog.getWeibo_date());
        comment_phone_type.setText("来自 " + SystemUtils.getDeviceBrand());
        comment_weibo_content.setText(microBlog.getWeibo_content());

        listView = (ListView) findViewById(R.id.list_view);

        OkHttpUtils
                .post()
                .addParams("method", "getComment")
                .addParams("weibo_id", Integer.toString(microBlog.getId()))
                //.addParams("weibo_id", "1")
                .url(StaticClass.URL + "WeiboServlet")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("response",response);
                        Gson gson = new Gson();
                        commentList = gson.fromJson(response,new TypeToken<List<Comment>>(){}.getType());
                        adapter = new MyListViewAdapter(CommentActivity.this,commentList);
                        listView.setAdapter(adapter);
                    }
                });

        //评论

        commentDialog = new CustomDialog(this, 100, 100,R.layout.dialog_comment,
                R.style.ActionSheetDialogStyle, Gravity.BOTTOM, R.style.ActionSheetDialogAnimation);

        comment = (LinearLayout) findViewById(R.id.comment);
        input_comment = (EditText) commentDialog.findViewById(R.id.input_comment);
        btn_publish_comment = (TextView) commentDialog.findViewById(R.id.btn_publish_comment);
        btn_publish_comment.setOnClickListener(this);
       commentDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                InputMethodManager inputManager = (InputMethodManager) input_comment.getContext().getSystemService(INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(input_comment, 0);
            }
        });
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input_comment.setText("");
                commentDialog.show();
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_publish_comment:
                time = UtilTools.getNowTime();
                updateComment();
                break;
        }
    }

    private void updateComment() {
        OkHttpUtils
                .post()
                .addParams("method","updateComment")
                .url(StaticClass.URL+"WeiboServlet")
                .addParams("weibo_id",Integer.toString(microBlog.getId()))
                .addParams("comment_name", user.getName())
                .addParams("comment_content", input_comment.getText().toString().trim())
                .addParams("comment_date", time)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(CommentActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (Integer.parseInt(response.trim()) > 0) {
                            Toast.makeText(CommentActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                            commentDialog.dismiss();
                            Comment comment = new Comment(user.getName(),user.getProfile(),user.getNick(),time,input_comment.getText().toString().trim());
                            adapter.addDate(comment);
                        } else {
                            Toast.makeText(CommentActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
