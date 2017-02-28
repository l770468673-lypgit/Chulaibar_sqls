package com.tianyi.chulaibar.activity;

import android.webkit.WebView;

import com.tianyi.chulaibar.R;
import com.tianyi.chulaibar.base.BaseActivity;
import com.tianyi.chulaibar.util.Constant;

public class AboutUsActivity extends BaseActivity {

    private WebView web_activity_about;


    @Override
    protected int getContentResid() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void init() {

        web_activity_about= (WebView) findViewById(R.id.web_activity_about);

    }

    @Override
    protected void loadDatas() {
        super.loadDatas();

        web_activity_about.loadUrl(Constant.URL.ABOUTUS_URL);

    }
}
