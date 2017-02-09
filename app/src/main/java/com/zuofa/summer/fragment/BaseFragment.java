package com.zuofa.summer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zuofa.summer.R;

/**
 * Created by 刘祚发 on 2017/1/19.
 */
public class BaseFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test,container,false);
        TextView mFragmentText = (TextView) view.findViewById(R.id.text_view);
        Bundle bundle = getArguments();
        String args = bundle.getString("argument");
        mFragmentText.setText(args);
        return view;
    }
}
