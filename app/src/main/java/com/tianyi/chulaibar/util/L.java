package com.tianyi.chulaibar.util;

import android.util.Log;

/**
 * @
 * @类名称: ${type_name}
 * @类描述: ${todo}
 * @创建人：
 * @创建时间：${date} ${time}
 * log输出
 * @备注：
 */
public class L {
    private static final String TAG = "print";

    /**
     * 日志输出级别
     */
    private static final int VERBOSE = 0;
    private static final int DEBUG = 1;
    private static final int INFO = 2;
    private static final int WARM = 3;
    private static final int ERROR = 4;

    /**
     * 当前允许输出的日志级别
     */
    private static final int OUT_STATE = 0;

    public static void v(String str){
        if(OUT_STATE <= VERBOSE) {
            Log.v(TAG, str);
        }
    }

    public static void d(String str){
        if(OUT_STATE <= DEBUG) {
            Log.d(TAG, str);
        }
    }

    public static void i(String str){
        if(OUT_STATE <= INFO) {
            Log.i(TAG, str);
        }
    }

    public static void w(String str){
        if(OUT_STATE <= WARM) {
            Log.w(TAG, str);
        }
    }

    public static void e(String str){
        if(OUT_STATE <= ERROR) {
            Log.e(TAG, str);
        }
    }

    public static void v(String tag, String str){
        if(OUT_STATE <= VERBOSE) {
            Log.v(tag, str);
        }
    }

    public static void d(String tag, String str){
        if(OUT_STATE <= DEBUG) {
            Log.d(tag, str);
        }
    }

    public static void i(String tag, String str){
        if(OUT_STATE <= INFO) {
            Log.i(tag, str);
        }
    }

    public static void w(String tag, String str){
        if(OUT_STATE <= WARM) {
            Log.w(tag, str);
        }
    }

    public static void e(String tag, String str){
        if(OUT_STATE <= ERROR) {
            Log.e(tag, str);
        }
    }
}
