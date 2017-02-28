package com.tianyi.chulaibar.activity;

import android.view.View;
import android.widget.TextView;
import com.tianyi.chulaibar.R;
import com.tianyi.chulaibar.base.BaseActivity;
import com.tianyi.chulaibar.customview.StarView;


public class PrizeActivity extends BaseActivity implements StarView.OnStarItemClickListener {

  private StarView starView_01,starView_02,starView_03,starView_04,starView_05;
  private TextView tv_01,tv_02,tv_03,tv_04,tv_05;
  private String[] comment_titles={"很差","较差","一般","较好","很好"};

    @Override
    protected int getContentResid() {
        return R.layout.activity_prize;
    }

    @Override
    protected void init() {


        tv_01= (TextView) findViewById(R.id.tv_huodong2_prize_activity);
        starView_01= (StarView) findViewById(R.id.starview_01_prize_activity);
        starView_01.setmStarItemClickListener(this,"starview_01");

        tv_02= (TextView) findViewById(R.id.tv_fabu2_prize_activity);
        starView_02= (StarView) findViewById(R.id.starview_02_prize_activity);
        starView_02.setmStarItemClickListener(this,"starview_02");

        tv_03= (TextView) findViewById(R.id.tv_guanli2_prize_activity);
        starView_03= (StarView) findViewById(R.id.starview_03_prize_activity);
        starView_03.setmStarItemClickListener(this,"starview_03");

        tv_04= (TextView) findViewById(R.id.tv_tiyan2_prize_activity);
        starView_04= (StarView) findViewById(R.id.starview_04_prize_activity);
        starView_04.setmStarItemClickListener(this,"starview_04");

        tv_05= (TextView) findViewById(R.id.tv_gongneng2_prize_activity);
        starView_05= (StarView) findViewById(R.id.starview_05_prize_activity);
        starView_05.setmStarItemClickListener(this,"starview_05");

    }

    @Override
    public void onItemClick(View view, int pos, String tag) {

        switch (tag){

            case "starview_01":

                tv_01.setText(comment_titles[pos]);

                break;

            case "starview_02":

                tv_02.setText(comment_titles[pos]);
                break;
            case "starview_03":

                tv_03.setText(comment_titles[pos]);
                break;
            case "starview_04":

                tv_04.setText(comment_titles[pos]);

                break;
            case "starview_05":

                tv_05.setText(comment_titles[pos]);
                break;
        }

    }

}
