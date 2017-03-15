package com.zuofa.summer.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.zuofa.summer.R;

/**
 * Created by AMing on 15/10/27.
 * Company RongCloud
 */
public class ConversationListActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_conversationlist);
    }

}
