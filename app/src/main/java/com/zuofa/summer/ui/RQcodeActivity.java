package com.zuofa.summer.ui;
/*
 *  项目名：  Summer 
 *  包名：    com.zuofa.summer.ui
 *  文件名:   RQcodeActivity
 *  创建者:   Summers
 *  创建时间: 2017/2/1022:27
 *  描述：    生成二维码
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.xys.libzxing.zxing.encoding.EncodingUtils;
import com.zuofa.summer.R;
import com.zuofa.summer.application.BaseApplication;
import com.zuofa.summer.bean.User;


public class RQcodeActivity extends BaseActivity{

    private ImageView qrCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        initView();
    }

    private void initView() {
        BaseApplication application = (BaseApplication) getApplication();
        User user =application.getUser();
        qrCode = (ImageView) findViewById(R.id.qrCode);
        //屏幕的宽
        int width = getResources().getDisplayMetrics().widthPixels;

        Bitmap qrCodeBitmap = EncodingUtils.createQRCode(user.getName(), width / 2, width / 2,
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        qrCode.setImageBitmap(qrCodeBitmap);
    }
}
