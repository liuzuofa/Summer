package com.zuofa.summer.utils;

import android.util.Log;

/**
 * Created by 刘祚发 on 2017/1/30.
 */
public class L {
    public static final boolean DEBUG = true;

    public static final String TAG = "Summer";

    public static void d(String text){
        if (DEBUG) {
            Log.d(TAG,text);
        }
    }

    public static void i(String text){
        if (DEBUG) {
            Log.i(TAG,text);
        }
    }

    public static void w(String text){
        if (DEBUG) {
            Log.w(TAG,text);
        }
    }

    public static void e(String text){
        if (DEBUG) {
            Log.e(TAG,text);
        }
    }

}
