package com.tianyi.chulaibar.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * 基础Activity
 */
public abstract class BaseActivity extends AppCompatActivity{
    public Bundle savedInstanceState;

    protected abstract int getContentResid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentResid());
        this.savedInstanceState = savedInstanceState;
        ButterKnife.bind(this);

        init();
        loadDatas();
    }

    /**
     * 初始化
     */
    protected void init() {

    }

    /**
     * 数据加载
     */
    protected void loadDatas(){

    }

    /**
     * 带过场动画的启动activity方式
     * @param intent
     * @param enterAnim
     * @param exitAnim
     */
    public void startActivity(Intent intent, int enterAnim, int exitAnim) {
        super.startActivity(intent);
        overridePendingTransition(enterAnim, exitAnim);
    }


}
