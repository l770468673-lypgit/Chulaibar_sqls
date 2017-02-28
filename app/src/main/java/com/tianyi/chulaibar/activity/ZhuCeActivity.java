package com.tianyi.chulaibar.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.tianyi.chulaibar.R;
import com.tianyi.chulaibar.base.BaseActivity;
import com.tianyi.chulaibar.util.Constant;
import com.tianyi.chulaibar.util.L;
import com.tianyi.chulaibar.util.OkHttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class ZhuCeActivity extends BaseActivity {

    private LinearLayout ll_change_gerenzhuce, ll_change_qiyezhuce;
    private boolean isQiYeZhuCe = true;
    private TextView tv_change_zhucestyle, tv_huoqu_gerenzhuce;
    private EditText et_zhuce_username, et_message_gerenzhuce, et_password_gerenzhuce, et_nikename_gerenzhuce, et_password_qiyezhuce, et_confirm_password_qiyezhuce;
    private CheckBox checkbox_zhuce;
    private Button zhuce_sure;
    private String phone, email, code;
    private String mPassword;
    private String mUrl_login;

    @Override
    protected int getContentResid() {
        return R.layout.activity_zhu_ce;
    }

    @Override
    protected void init() {

        ll_change_gerenzhuce = (LinearLayout) findViewById(R.id.ll_change_gerenzhuce);
        ll_change_qiyezhuce = (LinearLayout) findViewById(R.id.ll_change_qiyezhuce);
        tv_change_zhucestyle = (TextView) findViewById(R.id.tv_change_zhucestyle);

        //个人信息
        et_zhuce_username = (EditText) findViewById(R.id.et_zhuce_username);
        et_message_gerenzhuce = (EditText) findViewById(R.id.et_message_gerenzhuce);
        et_password_gerenzhuce = (EditText) findViewById(R.id.et_password_gerenzhuce);
        et_nikename_gerenzhuce = (EditText) findViewById(R.id.et_nikename_gerenzhuce);
        tv_huoqu_gerenzhuce = (TextView) findViewById(R.id.tv_huoqu_gerenzhuce);
        //企业信息
        et_password_qiyezhuce = (EditText) findViewById(R.id.et_password_qiyezhuce);
        et_confirm_password_qiyezhuce = (EditText) findViewById(R.id.et_confirm_password_qiyezhuce);

        //条款声明
        checkbox_zhuce = (CheckBox) findViewById(R.id.checkbox_zhuce);
        //注册
        zhuce_sure = (Button) findViewById(R.id.zhuce_sure);

    }

    @Override
    protected void loadDatas() {

    }


    //注册界面所有的按钮的点击事件
    public void ZhuCeBtn(View view) {

        switch (view.getId()) {

            case R.id.tv_change_zhucestyle:  //切换个人和企业注册界面

                if (isQiYeZhuCe) {
                    Log.d("change", "查看登录方式：-------->>>>>>111");
                    tv_change_zhucestyle.setText("切换成个人");
                    et_zhuce_username.setHint("请输入你的邮箱");
                    et_zhuce_username.setHintTextColor(Color.parseColor("#6606b4a1"));
                    //切换注册布局
                    ll_change_gerenzhuce.setVisibility(View.GONE);
                    et_zhuce_username.setText("");
                    et_message_gerenzhuce.setText("");
                    et_password_gerenzhuce.setText("");
                    et_nikename_gerenzhuce.setText("");

                    ll_change_qiyezhuce.setVisibility(View.VISIBLE);

                    isQiYeZhuCe = false;

                    Log.d("print", "isQiYeZhuCe：-------->>>>>>" + isQiYeZhuCe);
                } else {
                    Log.d("change", "查看登录方式：-------->>>>>>222");
                    tv_change_zhucestyle.setText("切换成企业");
                    et_zhuce_username.setHint("请输入你的手机号");
                    et_zhuce_username.setHintTextColor(Color.parseColor("#6606b4a1"));
                    //切换注册布局
                    ll_change_qiyezhuce.setVisibility(View.GONE);
                    et_zhuce_username.setText("");
                    et_password_qiyezhuce.setText("");
                    et_confirm_password_qiyezhuce.setText("");

                    ll_change_gerenzhuce.setVisibility(View.VISIBLE);

                    isQiYeZhuCe = true;
                    Log.d("print", "isQiYeZhuCe：-------->>>>>>" + isQiYeZhuCe);
                }
                break;

            case R.id.tv_huoqu_gerenzhuce:  //个人注册时，获取短信验证码


                //发送验证码
                final TimeCount time = new TimeCount(120000, 1000);//短信验证码倒计时初始化

                String str1 = et_zhuce_username.getText().toString().trim();

                if (str1.length() == 0 || str1.length() != 11) {
                    //弹一个Dialog  提示用户
                    new AlertView("手机号格式不正确", null, null, new String[]{"知道了"}, null, ZhuCeActivity.this,
                            AlertView.Style.Alert, null).show();
                } else {
                    time.start();//短信验证码倒计时开始
                    String url_yanzhegnma = String.format(Constant.URL.SENDMESSAGE_URL, str1);//发送验证码的url

                    OkHttpUtil.downJSON(url_yanzhegnma, new OkHttpUtil.OnDownDataListener() {
                        @Override
                        public void onResponse(String url, String json) throws JSONException {

                            Log.d("print", "成功返回");

                            JSONObject obj1 = new JSONObject(json);

                            String message = obj1.getString("message");
                            if (message.equals("ok")) {

                                Toast.makeText(ZhuCeActivity.this, "获取验证码成功，请输入", Toast.LENGTH_SHORT).show();
                                code = obj1.getString("code");
                                Log.d("print", "返回的验证码：-------->>>>>>" + code);

                            } else {
                                new AlertView("重新获取验证码", null, null, new String[]{"知道了"}, null, ZhuCeActivity.this,
                                        AlertView.Style.Alert, null).show();
                            }
                            //    return message;
                        }

                        @Override
                        public void onFailure(String url, String error) {

                        }
                    });


                }

                break;

            case R.id.zhuce_sure:  //确认注册

                Log.d("print", "企业注册：-------->>>>>>" + isQiYeZhuCe);

                if (isQiYeZhuCe == false) {//企业注册
                    Log.d("print", "企业注册开始");

                    email = et_zhuce_username.getText().toString().trim();
                    String password = et_password_qiyezhuce.getText().toString().trim();
                    String password_confirm = et_confirm_password_qiyezhuce.getText().toString().trim();
                    boolean check_state = checkbox_zhuce.isChecked();

                    Log.d("print", "查看数据谁为空：" + email + password + password_confirm);

                    if (email.length() == 0 || password.length() == 0 || password_confirm.length() == 0) {
                        new AlertView("输入内容不能为空", null, null, new String[]{"知道了"}, null, ZhuCeActivity.this,
                                AlertView.Style.Alert, null).show();
                    } else if (password.length() < 6 || password.length() < 6) {
                        new AlertView("输入内容格式不对", null, null, new String[]{"知道了"}, null, ZhuCeActivity.this,
                                AlertView.Style.Alert, null).show();
                    } else if (!password.equals(password_confirm)) {

                        new AlertView("前后密码必须一致", null, null, new String[]{"知道了"}, null, ZhuCeActivity.this,
                                AlertView.Style.Alert, null).show();

                    } else if (check_state == false) {

                        new AlertView("请同意注册条款", null, null, new String[]{"知道了"}, null, ZhuCeActivity.this,
                                AlertView.Style.Alert, null).show();

                    } else {

                        //    String url_login = String.format(Constant.URL.LOGIN_URL, email,mPassword, Constant.USERTYPE.user_qiye);

                        String url_register = String.format(Constant.URL.QIYEZHUCE_URL, email, password);
                        Log.d("print", url_register);
                        register(url_register);//注册的方法

                    }

                } else if (isQiYeZhuCe == true) {//个人注册
                    Log.d("print", "个人注册开始");
                    //  个人注册    注册有4个参数，短信验证码接口暂无，先写3个
                    phone = et_zhuce_username.getText().toString().trim();
                    String yanzhengma = et_message_gerenzhuce.getText().toString().trim();
                    mPassword = et_password_gerenzhuce.getText().toString().trim();
                    String nikename = et_nikename_gerenzhuce.getText().toString().trim();
                    boolean check_state = checkbox_zhuce.isChecked();


                    if (phone.length() == 0 || yanzhengma.length() == 0 || mPassword.length() == 0 || nikename.length() == 0) {
                        new AlertView("输入内容不能为空", null, null, new String[]{"知道了"}, null, ZhuCeActivity.this,
                                AlertView.Style.Alert, null).show();
                    } else if (yanzhengma.equals("code")) {//对比验证码是否一致
                        new AlertView("输入的验证码有误，请重新输入", null, null, new String[]{"知道了"}, null, ZhuCeActivity.this,
                                AlertView.Style.Alert, null).show();
                    } else if (phone.length() != 11 || mPassword.length() < 6) {
                        new AlertView("输入格式有误，请重新输入", null, null, new String[]{"知道了"}, null, ZhuCeActivity.this,
                                AlertView.Style.Alert, null).show();
                    } else if (check_state == false) {

                        new AlertView("请同意注册条款", null, null, new String[]{"知道了"}, null, ZhuCeActivity.this,
                                AlertView.Style.Alert, null).show();

                    } else {


                        mUrl_login = String.format(Constant.URL.LOGIN_URL, phone, mPassword, Constant.USERTYPE.user_qiye);
                        // String password_secret = MD5Util.getMD5Str(password);//加密后的密码

                        String url_register = String.format(Constant.URL.GERENZHUCE_URL, phone, mPassword, nikename);

                        Log.d("print", url_register);

                        register(url_register);//注册的方法

                    }
                }

                break;


            case R.id.denglu_yiyouzhanghao:  //从已有账号登录


                break;

            case R.id.iv_back_zhuce:  //回退

                finish();
                break;

        }

    }

    private void register(String url_register) {


        OkHttpUtil.downJSON(url_register, new OkHttpUtil.OnDownDataListener() {
            @Override
            public void onResponse(String url, String json) throws JSONException {

                Log.d("print", "成功返回");

                JSONObject obj1 = new JSONObject(json);

                String message = obj1.getString("message");
                String info = obj1.getString("info");

                if (message.equals("ok")) {

                    Toast.makeText(ZhuCeActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    //登陆到主界面


                    LoginByPost(mUrl_login);
                    //                    Intent loginIntent = new Intent(ZhuCeActivity.this, MainActivity.class);
                    //                    startActivity(loginIntent);

                } else {
                    new AlertView("当前账户已被注册", null, null, new String[]{"知道了"}, null, ZhuCeActivity.this,
                            AlertView.Style.Alert, null).show();
                }

                //     return message;
            }


            @Override
            public void onFailure(String url, String error) {

            }
        });

    }

    private void LoginByPost(String url_login) {
        OkHttpUtil.downJSON(url_login, new OkHttpUtil.OnDownDataListener() {
            @Override
            public void onResponse(String url, String json) throws JSONException {

                Log.d("print", "成功返回");

                JSONObject obj1 = new JSONObject(json);
                L.d("json", "json is " + json.toString());

                String message = obj1.getString("message");
                //String userId = obj1.getString("userId");
                String info = obj1.getString("info");

               // ShareUtil.putString("mUserId", userId);
              //  L.d("sssssssssssssssss", "mUserId  is  the " + userId.toString());

                if (message.equals("ok")) {

                    Toast.makeText(ZhuCeActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    //登陆到主界面
                    Intent loginIntent = new Intent(ZhuCeActivity.this, MainActivity.class);
                    startActivity(loginIntent);

                } else {
                    new AlertView("用户名或密码错误", null, null, new String[]{"知道了"}, null, ZhuCeActivity.this,
                            AlertView.Style.Alert, null).show();
                }
            }

            @Override
            public void onFailure(String url, String error) {
                //                new AlertView("用户名或密码错误", null, null, new String[]{"知道了"}, null, LoginActivity.this,
                //                        AlertView.Style.Alert, null).show();
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
            tv_huoqu_gerenzhuce.setText("获取");
            tv_huoqu_gerenzhuce.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            tv_huoqu_gerenzhuce.setClickable(false);//防止重复点击
            tv_huoqu_gerenzhuce.setText(millisUntilFinished / 1000 + "s");
        }
    }


}
