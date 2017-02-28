package com.tianyi.chulaibar.activity;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.tianyi.chulaibar.R;
import com.tianyi.chulaibar.base.BaseActivity;
import com.tianyi.chulaibar.util.Constant;
import com.tianyi.chulaibar.util.OkHttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;

public class PhoneActivity extends BaseActivity {

    private Button btn_yanzheng,btn_bangding;
    private RelativeLayout rl_phone_activity;
    private LinearLayout ll_yanzheng_phone_activity,ll_bangding_phone_activity;
    private TextView tv_back;
    private EditText et_number_activity_phone,et_yanzhengma_activity_phone;

    @Override
    protected int getContentResid() {
        return R.layout.activity_phone;
    }

    @Override
    protected void init() {


        tv_back= (TextView) findViewById(R.id.tv_title_phone_activity);
        rl_phone_activity= (RelativeLayout) findViewById(R.id.rl_phone_activity);//切换图片
       //验证手机的控件
        et_number_activity_phone= (EditText) findViewById(R.id.et_number_activity_phone);
        et_yanzhengma_activity_phone= (EditText) findViewById(R.id.et_yanzhengma_activity_phone);

        //更改手机的界面


    }

    public void xiugaiPhone(View view){

        switch (view.getId()){

            case R.id.btn_yanzheng_activity_phone:

                rl_phone_activity.setBackgroundResource(R.mipmap.bind_phonenumber_02);

                ll_yanzheng_phone_activity.setVisibility(View.GONE);
                ll_bangding_phone_activity.setVisibility(View.VISIBLE);

                break;

            case R.id.tv_yanzhegnma_activity_phone://发送验证码


                break;



            case R.id.iv_back_phone_activity: //返回键

                finish();

                break;


        }

    }



}
