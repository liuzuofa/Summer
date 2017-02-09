package com.zuofa.summer.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/*import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;*/
import com.zuofa.summer.R;
import com.zuofa.summer.adapter.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private Button sendJson;
    private LayoutInflater mInflater;
    private List<String> mTitleList = new ArrayList<>();
    private View view, view1,view2;//页卡视图
    private List<View> mViewList =new ArrayList<>();//页卡视图合集

    private RecyclerView mRecyclerView;

    private String json="{userName:'yueyue',userPassWord:'123'}";
    private String result=null;

    public static NewsFragment newInstance(String argument) {
        Bundle bundle = new Bundle();
        bundle.putString("argument", argument);
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news, container, false);
        initView();
        return view;
    }



    private void initRecyclerView() {
        mRecyclerView = (RecyclerView)view.findViewById(R.id.news_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
       // mRecyclerView.setAdapter();

    }


    private void initView() {
        mViewPager = (ViewPager)view.findViewById(R.id.view_page);
        mTabLayout = (TabLayout)view.findViewById(R.id.tabs);

        mInflater = LayoutInflater.from(this.getContext());
        view1 = mInflater.inflate(R.layout.fragment_test,null);
        //view1 = mInflater.inflate(R.layout.fragment_test2,null);
        view2 = mInflater.inflate(R.layout.fragment_test,null);

        //添加页卡视图
        mViewList.add(view1);
        mViewList.add(view2);

        //添加页卡标题
        mTitleList.add("校内");
        mTitleList.add("院系");

        //添加页卡标题
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));

        MyPagerAdapter mNewsAdapter = new MyPagerAdapter(mViewList,mTitleList);
        mViewPager.setAdapter(mNewsAdapter);//给ViewPage设置适配器
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initSendJson() {
        sendJson = (Button) view1.findViewById(R.id.send_json);
        sendJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                responseJson();
            }
        });
    }

    private void responseJson() {
         new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client;
                    client = new OkHttpClient();
                    RequestBody body = RequestBody.create(JSON,json);
                    Request request = new Request.Builder()
                            .url("http://localhost:8080/Summers/LoginServlet")
                            .post(body)
                            .build();
                    Response response = client.newCall(request).execute();

                    result = response.body().toString();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final TextView textView = (TextView)view1.findViewById(R.id.tv_2);
                textView.setText(result);
            }
        });

    }
    public static final MediaType JSON
            =MediaType.parse("application/json; charset=utf-8");


}
