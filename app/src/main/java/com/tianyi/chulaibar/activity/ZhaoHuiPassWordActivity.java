package com.tianyi.chulaibar.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ZhaoHuiPassWordActivity extends BaseActivity {


    private EditText et_number_zhaohuipassword_activity,et_yanzhengma_zhaohuipassword_activity,
            et_newpassword_zhaohuipassword_activity,et_newpassword2_zhaohuipassword_activity;
    private TextView tv_yanzhegnma_zhaohuipassword_activity;
    private Button btn_yanzheng_zhaohuipassword_activity,btn_commit_zhaohuipassword_activity;

    private RelativeLayout rl_zhaohuipassword_activity;
    private LinearLayout ll_zhaohuipassword_activity,ll_commit_zhaohuipassword_activity;
    private String code,phone1,userId;

    @Override
    protected int getContentResid() {
        return R.layout.activity_zhao_hui_password;
    }


    @Override
    protected void init() {

        rl_zhaohuipassword_activity= (RelativeLayout) findViewById(R.id.rl_zhaohuipassword_activity);
        ll_zhaohuipassword_activity= (LinearLayout) findViewById(R.id.ll_zhaohuipassword_activity);
        ll_commit_zhaohuipassword_activity= (LinearLayout) findViewById(R.id.ll_commit_zhaohuipassword_activity);

        btn_yanzheng_zhaohuipassword_activity= (Button) findViewById(R.id.btn_yanzheng_zhaohuipassword_activity);
        btn_commit_zhaohuipassword_activity= (Button) findViewById(R.id.btn_commit_zhaohuipassword_activity);

        et_number_zhaohuipassword_activity= (EditText) findViewById(R.id.et_number_zhaohuipassword_activity);
        et_yanzhengma_zhaohuipassword_activity= (EditText) findViewById(R.id.et_yanzhengma_zhaohuipassword_activity);
        et_newpassword_zhaohuipassword_activity= (EditText) findViewById(R.id.et_newpassword_zhaohuipassword_activity);
        et_newpassword2_zhaohuipassword_activity= (EditText) findViewById(R.id.et_newpassword2_zhaohuipassword_activity);

        tv_yanzhegnma_zhaohuipassword_activity= (TextView) findViewById(R.id.tv_yanzhegnma_zhaohuipassword_activity);


    }

    @Override
    protected void loadDatas() {


    }

    public void zhaohuiPassword(View view){


        switch (view.getId()){


            case R.id.iv_back_zhaohuipassword_activity://回退

                finish();

                break;

            case R.id.tv_yanzhegnma_zhaohuipassword_activity://获取验证码

                //发送验证码
                final TimeCount time = new TimeCount(120000, 1000);//短信验证码倒计时初始化

                phone1 = et_number_zhaohuipassword_activity.getText().toString().trim();

                if (phone1.length() == 0 ) {
                    //弹一个Dialog  提示用户
                    new AlertView("手机号不能为空", null, null, new String[]{"知道了"}, null, ZhaoHuiPassWordActivity.this,
                            AlertView.Style.Alert, null).show();
                } else if (phone1.length() != 11){

                    new AlertView("手机号格式不正确", null, null, new String[]{"知道了"}, null, ZhaoHuiPassWordActivity.this,
                            AlertView.Style.Alert, null).show();

                }else {
                    time.start();//短信验证码倒计时开始
                    String url_yanzhegnma = String.format(Constant.URL.SENDMESSAGE_URL, phone1);//发送验证码的url

                    //得到验证码
                    OkHttpUtil.downJSON(url_yanzhegnma, new OkHttpUtil.OnDownDataListener() {
                        @Override
                        public void onResponse(String url, String json) throws JSONException {

                            Log.d("print","成功返回");

                            JSONObject obj1 = new JSONObject(json);
                            String message= obj1.getString("message");
                            if (message.equals("ok")) {

                                Toast.makeText(ZhaoHuiPassWordActivity.this, "获取验证码成功，请输入", Toast.LENGTH_SHORT).show();
                                code= obj1.getString("code");
                                Log.d("print","返回的验证码：-------->>>>>>"+code);

                            }else {
                                new AlertView("重新获取验证码", null, null, new String[]{"知道了"}, null, ZhaoHuiPassWordActivity.this,
                                        AlertView.Style.Alert, null).show();
                            }
                        }

                        @Override
                        public void onFailure(String url, String error) {

                        }
                    });

                }

                break;


            case R.id.btn_yanzheng_zhaohuipassword_activity://验证

                Log.d("print","验证手机号开始");
                // 验证手机号开始
                String phone2 = et_number_zhaohuipassword_activity.getText().toString().trim();
                String yanzhengma = et_yanzhengma_zhaohuipassword_activity.getText().toString().trim();

                if (phone2.length() == 0||yanzhengma.length()==0) {
                    new AlertView("输入内容不能为空", null, null, new String[]{"知道了"}, null, ZhaoHuiPassWordActivity.this,
                            AlertView.Style.Alert, null).show();
                } else if(yanzhengma.equals("code")){//对比验证码是否一致
                    new AlertView("输入的验证码有误，请重新输入", null, null, new String[]{"知道了"}, null, ZhaoHuiPassWordActivity.this,
                            AlertView.Style.Alert, null).show();
                } else if(phone2.length()!=11){
                    new AlertView("输入格式有误，请重新输入", null, null, new String[]{"知道了"}, null, ZhaoHuiPassWordActivity.this,
                            AlertView.Style.Alert, null).show();
                }else if(phone1.equals(phone2)==false ){

                    new AlertView("手机号不正确，请重新输入", null, null, new String[]{"知道了"}, null, ZhaoHuiPassWordActivity.this,
                            AlertView.Style.Alert, null).show();

                } else {

                    String url_yanzheng = String.format(Constant.URL.QUERYBYTELUSERS_URL, phone1);//验证并获取userID的url

                    checkYanZhengMa(url_yanzheng);

                }

                break;


            case R.id.btn_commit_zhaohuipassword_activity://确认提交

                String password1=et_newpassword_zhaohuipassword_activity.getText().toString().trim();
                String password2=et_newpassword2_zhaohuipassword_activity.getText().toString().trim();


                if (password1.length() == 0||password2.length()==0) {
                    new AlertView("密码不能为空", null, null, new String[]{"知道了"}, null, ZhaoHuiPassWordActivity.this,
                            AlertView.Style.Alert, null).show();
                } else if(password1.length()<6||password2.length()<6){
                    new AlertView("密码不能少于6位，请重新输入", null, null, new String[]{"知道了"}, null, ZhaoHuiPassWordActivity.this,
                            AlertView.Style.Alert, null).show();
                }else if(password1.equals(password2)==false ){

                    new AlertView("密码前后不一致，请重新输入", null, null, new String[]{"知道了"}, null, ZhaoHuiPassWordActivity.this,
                            AlertView.Style.Alert, null).show();

                } else {

//                    Toast.makeText(ZhaoHuiPassWordActivity.this, "密码修改成功", Toast.LENGTH_SHORT).show();
//                    //登陆到主界面
//                    Intent loginIntent = new Intent(ZhaoHuiPassWordActivity.this, LoginActivity.class);
//                    startActivity(loginIntent);



                    String commit_url = String.format(Constant.URL.MODIFYPASSWORD_URL,userId,password1);//

                    Log.d("print","提交修改的url：-------->>>>>>"+commit_url);

                    commitPassWord(commit_url);

                }

                break;

        }

    }

    private void commitPassWord( String commit_url) {


        OkHttpUtil.downJSON(commit_url, new OkHttpUtil.OnDownDataListener() {
            @Override
            public void onResponse(String url, String json) throws JSONException {

                Log.d("print","提交修改后：-------->>>>>>");

                JSONObject obj1 = new JSONObject(json);

                String message= obj1.getString("message");
                String info= obj1.getString("info");

                if (message.equals("ok")) {

                    Toast.makeText(ZhaoHuiPassWordActivity.this, "密码修改成功", Toast.LENGTH_SHORT).show();
                    //登陆到主界面
                    Intent loginIntent = new Intent(ZhaoHuiPassWordActivity.this, LoginActivity.class);
                    startActivity(loginIntent);

                }else {
                    new AlertView("修改密码失败", null, null, new String[]{"知道了"}, null, ZhaoHuiPassWordActivity.this,
                            AlertView.Style.Alert, null).show();
                }
            }

            @Override
            public void onFailure(String url, String error) {

            }
        });


    }

    private void checkYanZhengMa( String url_yanzheng) {


        OkHttpUtil.downJSON(url_yanzheng, new OkHttpUtil.OnDownDataListener() {
            @Override
            public void onResponse(String url, String json) throws JSONException {

                Log.d("print","成功返回");

                JSONObject obj1 = new JSONObject(json);

                String message= obj1.getString("message");
                 userId= obj1.getString("userId");

                if (message.equals("ok")) {

                    Toast.makeText(ZhaoHuiPassWordActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
                    rl_zhaohuipassword_activity.setBackgroundResource(R.mipmap.find_password_02);
                    ll_zhaohuipassword_activity.setVisibility(View.GONE);
                    ll_commit_zhaohuipassword_activity.setVisibility(View.VISIBLE);

                }else {
                    new AlertView("验证失败，请重新验证", null, null, new String[]{"知道了"}, null, ZhaoHuiPassWordActivity.this,
                            AlertView.Style.Alert, null).show();
                }
            }

            @Override
            public void onFailure(String url, String error) {

            }
        });

    }

    //短信倒计时
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            tv_yanzhegnma_zhaohuipassword_activity.setText("立即验证");
            tv_yanzhegnma_zhaohuipassword_activity.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            tv_yanzhegnma_zhaohuipassword_activity.setClickable(false);//防止重复点击
            tv_yanzhegnma_zhaohuipassword_activity.setText(millisUntilFinished / 1000 + "s");
        }
    }

}
