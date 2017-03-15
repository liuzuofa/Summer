package com.zuofa.summer.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zuofa.summer.R;
import com.zuofa.summer.adapter.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;


/**
 * A simple {@link Fragment} subclass.
 */
public class DynamicFragment extends Fragment {

    private View view;
    private Fragment conversationlist;
    public static DynamicFragment newInstance(String argument) {
        Bundle bundle = new Bundle();
        bundle.putString("argument", argument);
        DynamicFragment fragment = new DynamicFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dynamic, container, false);
        initView();
        return view;
    }

    private void initView() {
        if (conversationlist == null){
            conversationlist = initConversationListFragment();
        }
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.conversationlist, conversationlist).commit();
    }
    /**
     * 封装的代码加载融云的会话列表的 fragment
     * @return
     */
    private Fragment initConversationListFragment() {
        ConversationListFragment fragment = new ConversationListFragment();
        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")
                .build();
        fragment.setUri(uri);
        return fragment;
    }

}
