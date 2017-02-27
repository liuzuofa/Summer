package com.zuofa.summer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zuofa.summer.R;
import com.zuofa.summer.application.BaseApplication;
import com.zuofa.summer.bean.MicroBlog;
import com.zuofa.summer.bean.User;
import com.zuofa.summer.ui.CommentActivity;
import com.zuofa.summer.utils.L;
import com.zuofa.summer.utils.StaticClass;
import com.zuofa.summer.utils.SystemUtils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;



public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    Bitmap bitmap ;
    private Context mContext;
    private Activity mActivity;
    //用户头像下载标志位
    boolean status;
    private User user;
    private List<MicroBlog> microBlogsList = new ArrayList<>();
    private String res;



    static class ViewHolder extends RecyclerView.ViewHolder {
        View weiboView;
        CircleImageView weibo_profile;
        TextView weibo_name;
        TextView weibo_time;
        TextView weibo_phone_type;
        Button weibo_isCare;
        TextView weibo_content;
        NineGridImageView nineImage_view;
        LinearLayout btn_weibo_comment;
        TextView weibo_comment;
        ImageView weibo_admire_iv;
        TextView weibo_admire;
        public ViewHolder(View itemView) {
            super(itemView);
            //weiboView = itemView;
            weibo_profile = (CircleImageView) itemView.findViewById(R.id.weibo_profile);
            weibo_name = (TextView) itemView.findViewById(R.id.weibo_name);
            weibo_time = (TextView) itemView.findViewById(R.id.weibo_time);
            weibo_phone_type = (TextView) itemView.findViewById(R.id.weibo_phone_type);
            weibo_isCare = (Button) itemView.findViewById(R.id.weibo_isCare);
            weibo_content = (TextView) itemView.findViewById(R.id.weibo_content);
            nineImage_view = (NineGridImageView) itemView.findViewById(R.id.nineImage_view);
            btn_weibo_comment = (LinearLayout) itemView.findViewById(R.id.btn_weibo_comment);
            weibo_comment = (TextView) itemView.findViewById(R.id.weibo_comment);
            weibo_admire_iv = (ImageView) itemView.findViewById(R.id.weibo_admire_iv);
            weibo_admire = (TextView) itemView.findViewById(R.id.weibo_admire);
        }
    }

    /*public MyRecyclerViewAdapter(List<MicroBlog> blogs){
        this.microBlogsList.addAll(blogs) ;
        notifyDataSetChanged();
    }*/
    public MyRecyclerViewAdapter(Context context){
        this.mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weibo_recyclerview,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (getBitmap(microBlogsList.get(position).getProfile()) !=null) {
            holder.weibo_profile.setImageBitmap(getBitmap(microBlogsList.get(position).getProfile()));
        }else {
            holder.weibo_profile.setImageResource(R.drawable.add_pic);
        }
        holder.weibo_name.setText(microBlogsList.get(position).getNick());
        holder.weibo_time.setText(microBlogsList.get(position).getWeibo_date());
        holder.weibo_phone_type.setText("来自 "+SystemUtils.getDeviceBrand());
        holder.weibo_content.setText(microBlogsList.get(position).getWeibo_content());
        holder.nineImage_view.setVisibility(View.VISIBLE);
        holder.btn_weibo_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext,position+"",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext,CommentActivity.class);
                intent.putExtra("blog",microBlogsList.get(position));
                mContext.startActivity(intent);

            }
        });

        /*holder.weibo_admire_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpUtils
                        .post()
                        .addParams("method","updateAdmire")
                        .addParams("weibo_id",Integer.toString(microBlogsList.get(position).getId()))
                        .addParams("name",user.getName())
                        .url(StaticClass.URL+"WeiboServlet")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                            }
                            @Override
                            public void onResponse(String response, int id) {
                                res = response;
                                Toast.makeText(mActivity,"response="+response,Toast.LENGTH_SHORT).show();
                                *//*if (Integer.parseInt(response.trim())>0){
                                    mHolder.weibo_admire.setText(response.trim());
                                }*//*
                            }
                        });
                holder.weibo_admire.setText(Integer.parseInt(res.trim()));
            }
        });*/
    }


    private Bitmap getBitmap(String profile) {
        OkHttpUtils
                .get()//
                .url(StaticClass.URL.substring(0,StaticClass.URL.length()-1) + profile)//
                .build()//
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(Bitmap response, int id) {
                        bitmap =response;
                    }
                });
        return bitmap;
    }

    @Override
    public int getItemCount() {
        return microBlogsList.size();
    }

    public void clearData() {
        this.microBlogsList.clear();
    }

    public void addAllData(List<MicroBlog> dataList) {
        this.microBlogsList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void getUser(User mUser) {
        user = mUser;
    }

}
