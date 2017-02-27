package com.zuofa.summer.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zuofa.summer.R;
import com.zuofa.summer.adapter.MyRecyclerViewAdapter;
import com.zuofa.summer.application.BaseApplication;
import com.zuofa.summer.bean.MicroBlog;
import com.zuofa.summer.bean.User;
import com.zuofa.summer.utils.L;
import com.zuofa.summer.utils.StaticClass;
import com.zuofa.summer.utils.UtilTools;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements PullLoadMoreRecyclerView.PullLoadMoreListener {

    private PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;
    private MyRecyclerViewAdapter mRecyclerViewAdapter;
    private View view;
    private RecyclerView recyclerView;
    private int mCount;
    private List<MicroBlog> microBlogList = new ArrayList<>();

    public static HomeFragment newInstance(String argument) {
        Bundle bundle = new Bundle();
        bundle.putString("argument", argument);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        return view;
    }


    private void initView() {
        BaseApplication application = (BaseApplication)getActivity().getApplication();
        User user = application.getUser();
        mPullLoadMoreRecyclerView = (PullLoadMoreRecyclerView) view.findViewById(R.id.mPullRecyclerView);
        recyclerView = mPullLoadMoreRecyclerView.getRecyclerView();
        recyclerView.setVerticalScrollBarEnabled(true);
        //显示下拉刷新
        mPullLoadMoreRecyclerView.setRefreshing(true);
        //设置上拉刷新文字
        mPullLoadMoreRecyclerView.setFooterViewText("loading");

        mPullLoadMoreRecyclerView.setLinearLayout();

        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(this);
        mRecyclerViewAdapter = new MyRecyclerViewAdapter(getContext());
        mPullLoadMoreRecyclerView.setAdapter(mRecyclerViewAdapter);
        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
        mRecyclerViewAdapter.getUser(user);
        getData();
    }

    private void getData() {
        OkHttpUtils
                .post()
                .addParams("method","getBlog")
                .url(StaticClass.URL+"WeiboServlet")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        microBlogList = gson.fromJson(response,new TypeToken<List<MicroBlog>>(){}.getType());
                        mRecyclerViewAdapter.addAllData(microBlogList);
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                    }
                });
    }

    //解析Json
    private void parsingJson(String t) {
        try {
            JSONArray jsonList = new JSONArray(t);
            for (int i = 0; i < jsonList.length(); i++) {
                JSONObject json = (JSONObject) jsonList.get(i);
                MicroBlog microBlog = new MicroBlog();
                microBlog.setId(json.getInt("id"));
                microBlog.setName(json.getString("name"));
                microBlog.setNick(json.getString("nick"));
                microBlog.setProfile(json.getString("profile"));
                microBlog.setWeibo_content(json.getString("weibo_content"));
                //microBlog.setWeibo_photo(json.getString("weibo_photo"));
                microBlog.setWeibo_date(json.getString("weibo_date"));
                microBlogList.add(microBlog);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onRefresh() {
        Log.e("wxl", "onRefresh");
        setRefresh();
        getData();

    }

    @Override
    public void onLoadMore() {
        Log.e("wxl", "onLoadMore");
        mCount = mCount + 1;
        getData();
    }

    private void setRefresh() {
        mRecyclerViewAdapter.clearData();
        mCount = 1;
    }
}
