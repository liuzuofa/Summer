package com.zuofa.summer.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 刘祚发 on 2017/1/29.
 */
public class UtilTools {
    public static void setFont(Context mContext, TextView textView){
        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(),"fonts/FONT.TTF");
        textView.setTypeface(typeface);
    }

    /*//将date类型转换成string
    public static String dateToString(java.util.Date date) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }*/

    /*public static Date stringToDate(String s)  {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
             Date date =sdf.parse(s);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }*/
    public static String getNowTime(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
    public static String getNowTimeString(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return sdf.format(new Date());
    }

}

