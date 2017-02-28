package com.tianyi.chulaibar.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @
 * @类名称: ${type_name}
 * @类描述: ${todo}
 * @创建人：
 * @创建时间：${date} ${time}    共享参数的工具类
 * @备注：
 */
public class ShareUtil {

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    /**
     * 初始化共享参数
     * @param context
     */
    public static void initShared(Context context) {
        sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(String key) {
        return sharedPreferences.getString(key, null);
    }



    public static void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }


    public static int getInt(String key) {
        return sharedPreferences.getInt(key,0);
    }
}
