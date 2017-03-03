package com.zuofa.summer.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zuofa.summer.R;
import com.zuofa.summer.bean.Comment;
import com.zuofa.summer.utils.PicassoUtils;
import com.zuofa.summer.utils.StaticClass;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

import static android.R.attr.width;
import static com.zuofa.summer.R.attr.height;

/**
 * Created by liuzu on 2017/2/21.
 */

public class MyListViewAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<Comment> mCommentList;
    private Bitmap bitmap;

    public MyListViewAdapter(Context context, List<Comment> commentList) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mCommentList = commentList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mCommentList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCommentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.comment_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.listview_profile = (CircleImageView) convertView.findViewById(R.id.listview_profile);
            viewHolder.listview_name = (TextView) convertView.findViewById(R.id.listview_name);
            viewHolder.listview_time = (TextView) convertView.findViewById(R.id.listview_time);
            viewHolder.listview_content = (TextView) convertView.findViewById(R.id.listview_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Comment comment = mCommentList.get(position);
        viewHolder.listview_name.setText(comment.getNick());
        viewHolder.listview_time.setText(comment.getDate());
        viewHolder.listview_content.setText(comment.getContent());
        if (!TextUtils.isEmpty(comment.getProfile())) {
           /* PicassoUtils.loadImageViewHolder(mContext, StaticClass.PROFILE_URL + comment.getProfile()
                    , R.drawable.add_pic, R.drawable.add_pic, viewHolder.listview_profile);*/
            Glide.with(mContext).load(StaticClass.PROFILE_URL + comment.getProfile()).error(R.drawable.add_pic)
                    .into(viewHolder.listview_profile);

        }
        return convertView;
    }
    /*if (getBitmap(comment.getProfile()) !=null) {
                viewHolder.listview_profile.setImageBitmap(getBitmap(comment.getProfile()));
            }else {
                viewHolder.listview_profile.setImageResource(R.drawable.add_pic);
            }*/
    private final class ViewHolder {
        CircleImageView listview_profile;
        TextView listview_name;
        TextView listview_time;
        TextView listview_content;
    }

    private Bitmap getBitmap(String profile) {
        OkHttpUtils
                .get()//
                .url(StaticClass.URL.substring(0, StaticClass.URL.length() - 1) + profile)//
                .build()//
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        //status =false;
                    }

                    @Override
                    public void onResponse(Bitmap response, int id) {
                        //L.e("网络下载的图片");
                        bitmap = response;
                        //status =true;
                    }
                });
        return bitmap;
    }

    public void addDate(Comment comment){
        mCommentList.add(comment);
        notifyDataSetChanged();
    }

}
