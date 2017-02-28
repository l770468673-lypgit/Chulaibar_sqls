package com.tianyi.chulaibar.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tianyi.chulaibar.R;
import com.tianyi.chulaibar.base.BaseActivity;
import com.tianyi.chulaibar.util.GlideCacheUtil;

public  class SheZhiActivity extends BaseActivity implements View.OnClickListener {



    private RelativeLayout rl_phone_xiugai, rl_password_xiugai, rl_news_shezhi,
            rl_yijian_fankui, rl_rating, rl_share, rl_cache, rl_help, rl_about_bar, rl_exit;
    private ImageView iv_back_shezhi;

    @Override
    protected int getContentResid() {
        return R.layout.activity_she_zhi;
    }

    @Override
    protected void init() {

        rl_phone_xiugai = (RelativeLayout) findViewById(R.id.rl_phone_xiugai);
        rl_password_xiugai = (RelativeLayout) findViewById(R.id.rl_password_xiugai);
        rl_news_shezhi = (RelativeLayout) findViewById(R.id.rl_news_shezhi);
        rl_yijian_fankui = (RelativeLayout) findViewById(R.id.rl_yijian_fankui);
        rl_rating = (RelativeLayout) findViewById(R.id.rl_rating);
        rl_share = (RelativeLayout) findViewById(R.id.rl_share);
        rl_cache = (RelativeLayout) findViewById(R.id.rl_cache);
        rl_help = (RelativeLayout) findViewById(R.id.rl_help);
        rl_about_bar = (RelativeLayout) findViewById(R.id.rl_about_bar);
        rl_exit = (RelativeLayout) findViewById(R.id.rl_exit);

        iv_back_shezhi = (ImageView) findViewById(R.id.iv_back_shezhi);


        rl_phone_xiugai.setOnClickListener(this);
        rl_password_xiugai.setOnClickListener(this);
        rl_news_shezhi.setOnClickListener(this);
        rl_yijian_fankui.setOnClickListener(this);
        rl_rating.setOnClickListener(this);
        rl_share.setOnClickListener(this);
        rl_cache.setOnClickListener(this);
        rl_help.setOnClickListener(this);
        rl_about_bar.setOnClickListener(this);
        rl_exit.setOnClickListener(this);
        iv_back_shezhi.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.iv_back_shezhi://回退

                finish();
                break;

            case R.id.rl_phone_xiugai://更改手机号

                Intent phoneIntent = new Intent(this, PhoneActivity.class);
                startActivity(phoneIntent);

                break;
            case R.id.rl_password_xiugai://修改密码

                Intent passwowdIntent = new Intent(this, PassWordActivity.class);
                startActivity(passwowdIntent);

                break;
            case R.id.rl_news_shezhi://消息通知

                break;
            case R.id.rl_yijian_fankui://意见反馈

                break;
            case R.id.rl_rating://评分

                Intent ratingIntent = new Intent(this, PrizeActivity.class);
                startActivity(ratingIntent);

                break;
            case R.id.rl_share://邀请好友

                break;

            case R.id.rl_cache://清空缓存
                cleanCache();
                break;

            case R.id.rl_help://疑问帮助

                break;
            case R.id.rl_about_bar://关于出来bar

                Intent aboutIntent = new Intent(this, AboutUsActivity.class);
                startActivity(aboutIntent);


                break;

            case R.id.rl_exit://退出

                break;


        }

    }

    /**
     * 清除缓存
     */
    private void cleanCache() {
        String size = GlideCacheUtil.getInstance().getCacheSize(this);
        new AlertDialog.Builder(this)
                .setTitle("缓存大小为" + size)
                .setMessage("是否清除缓存")
                .setCancelable(false)
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(SheZhiActivity.this, "正在清理...", Toast.LENGTH_SHORT).show();
                if (GlideCacheUtil.getInstance().clearImageAllCache(SheZhiActivity.this))
                    Toast.makeText(SheZhiActivity.this, "清理完成", Toast.LENGTH_SHORT).show();
            }
        }).show();
    }


}
