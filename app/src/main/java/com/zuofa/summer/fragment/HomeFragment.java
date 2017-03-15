package com.zuofa.summer.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

import static android.widget.NumberPicker.OnScrollListener.SCROLL_STATE_IDLE;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements PullLoadMoreRecyclerView.PullLoadMoreListener {

    private MyRecyclerViewAdapter mRecyclerViewAdapter;
    private View view;
    private RecyclerView recyclerView;
    private int mCount;
    private List<MicroBlog> microBlogList = new ArrayList<>();
    private SwipeRefreshLayout swipe_refresh;

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
        recyclerView = (RecyclerView) view.findViewById(R.id.mRecycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        mRecyclerViewAdapter = new MyRecyclerViewAdapter(getContext());
        recyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerViewAdapter.getUser(user);

        swipe_refresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        //swipe_refresh.setColorSchemeColors(R.color.colorPrimary);
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                swipe_refresh.setRefreshing(false);
            }
        });

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
        getData();

    }

    @Override
    public void onLoadMore() {

    }


}
