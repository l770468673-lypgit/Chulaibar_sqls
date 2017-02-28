package com.tianyi.chulaibar;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.tianyi.chulaibar.manager.HttpManager;
import com.tianyi.chulaibar.util.OkHttpUtil;
import com.tianyi.chulaibar.util.ShareUtil;


/**
 * Created by Administrator on 2016/11/17 0017.
 */
public class MyApplication extends Application {


    //18560285299
    @Override
    public void onCreate() {
        super.onCreate();

        ActiveAndroid.initialize(this);
        OkHttpUtil.initOkHttp();
        ShareUtil.initShared(this);
        HttpManager.getInstance();


    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        ActiveAndroid.dispose();
    }


}
