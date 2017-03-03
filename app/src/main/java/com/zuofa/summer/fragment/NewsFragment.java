package com.zuofa.summer.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/*import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;*/
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zuofa.summer.R;
import com.zuofa.summer.adapter.MyPagerAdapter;
import com.zuofa.summer.adapter.RecruitListViewAdapter;
import com.zuofa.summer.ui.WebViewActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
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

    private LayoutInflater mInflater;
    private List<String> mTitleList = new ArrayList<>();
    private View view, view1,view2;//页卡视图
    private List<View> mViewList =new ArrayList<>();//页卡视图合集

    private ListView recruit_listview;
    private ListView postgraduate_listview;
    private Button btn_test;

    private List<String> yan_title = new ArrayList<>();
    private List<String> yan_url = new ArrayList<>();
    private List<String> addr = new ArrayList<>();
    private List<String> title = new ArrayList<>();
    private List<String> url = new ArrayList<>();

    private RecruitListViewAdapter adapter;
    private RecruitListViewAdapter postAdapter;
    private Handler handler = new Handler(){
        public  void handleMessage(Message msg){
            switch (msg.what) {
                case 1:
                    adapter = new RecruitListViewAdapter(getContext(),title,url);
                    recruit_listview.setAdapter(adapter);
                    break;
                case 2:
                    postAdapter = new RecruitListViewAdapter(getContext(),yan_title,yan_url);
                    postgraduate_listview.setAdapter(postAdapter);
                    break;
            }
        }
    };




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

    private void initView() {
        mViewPager = (ViewPager) view.findViewById(R.id.view_page);
        mTabLayout = (TabLayout) view.findViewById(R.id.tabs);

        mInflater = LayoutInflater.from(this.getContext());
        view1 = mInflater.inflate(R.layout.fragment_recruit, null);
        view2 = mInflater.inflate(R.layout.fragment_postgraduate, null);

        //添加页卡视图
        mViewList.add(view1);
        mViewList.add(view2);

        //添加页卡标题
        mTitleList.add("招聘信息");
        mTitleList.add("考研天地");

        //添加页卡标题
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));

        MyPagerAdapter mNewsAdapter = new MyPagerAdapter(mViewList, mTitleList);
        mViewPager.setAdapter(mNewsAdapter);//给ViewPage设置适配器
        mTabLayout.setupWithViewPager(mViewPager);

        recruit_listview = (ListView) view1.findViewById(R.id.recruit_listview);
        /*recruit_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", url.get(i));
                startActivity(intent);
            }
        });*/

        postgraduate_listview = (ListView) view2.findViewById(R.id.postgraduate_listview);
        postgraduate_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", yan_url.get(i));
                startActivity(intent);
            }
        });
        //btn_test = (Button) view1.findViewById(R.id.btn_test);
        /*btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {*/
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //从一个URL加载一个Document对象。
                            Document doc = Jsoup.connect("http://xjh.haitou.cc/nc/uni-169").get();
                            Element element = doc.select("div.grid-view").first();
                            Elements elements =element.select("td.cxxt-title");
                            Log.e("elements",Integer.toString(elements.size()));



                            for (int i = 0; i < elements.size(); i++) {
                                Log.e("url",elements.get(i).select("a").attr("title"));
                                Log.e("title","http://xjh.haitou.cc"+elements.get(i).select("a").attr("href"));
                                title.add(elements.get(i).select("a").attr("title"));
                                url.add("http://xjh.haitou.cc"+elements.get(i).select("a").attr("href"));
                            }

                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);


                        }catch(Exception e) {
                            Log.e("mytags", e.toString());
                        }
                    }
                }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc1 = Jsoup.connect("http://www.chinakaoyan.com/info/list/ClassID/84.shtml").get();
                    Element element1 = doc1.select("div.uc_list").first();
                    Elements elements1 =element1.select("li");
                    Log.e("elements1",Integer.toString(elements1.size()));

                    for (int i = 0; i < elements1.size(); i++) {
                        Log.e("yan_title",elements1.get(i).select("a").text());
                        Log.e("yan_url","http://www.chinakaoyan.com"+elements1.get(i).select("a").attr("href"));
                        yan_title.add(elements1.get(i).select("a").text());
                        yan_url.add("http://www.chinakaoyan.com"+elements1.get(i).select("a").attr("href"));
                    }
                    Message s = new Message();
                    s.what = 2;
                    handler.sendMessage(s);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
            //}
        //});

    }
}
