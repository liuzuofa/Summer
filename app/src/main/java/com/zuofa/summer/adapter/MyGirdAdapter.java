package com.zuofa.summer.adapter;
/*
 *  项目名：  Summer 
 *  包名：    com.zuofa.summer.adapter 
 *  文件名:   MyGirdAdapter 
 *  创建者:   Summers
 *  创建时间: 2017/3/1 18:07 
 *  描述：    TODO
 */

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zuofa.summer.R;
import com.zuofa.summer.utils.StaticClass;

import java.util.ArrayList;
import java.util.List;

public class MyGirdAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mList = new ArrayList<>();

    public void setDate(Context context,String list){
        String[] ss =list.split(";");
        //List<String> stringList = new ArrayList<>();
        mList.clear();
        for (int i = 0; i < ss.length; i++) {
            Log.e("ss", StaticClass.PHOTO_URL+ss[i]);
            mList.add(StaticClass.PHOTO_URL+ss[i]);
        }
        this.mContext = context;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if (view == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(240, 240));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(10, 10, 10, 10);
        } else {
            imageView = (ImageView) view;
        }
        Glide.with(mContext).load(mList.get(i)).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
        return imageView;
    }


}
