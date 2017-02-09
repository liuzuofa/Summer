package com.zuofa.summer.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by 刘祚发 on 2017/1/29.
 */
public class UtilTools {
    public static void setFont(Context mContext, TextView textView){
        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(),"fonts/FONT.TTF");
        textView.setTypeface(typeface);
    }
}
