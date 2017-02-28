package com.tianyi.chulaibar.activity;


import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Update;
import com.bigkoo.alertview.AlertView;
import com.tianyi.chulaibar.R;
import com.tianyi.chulaibar.base.BaseActivity;
import com.tianyi.chulaibar.db.User;
import com.tianyi.chulaibar.util.Constant;
import com.tianyi.chulaibar.util.L;
import com.tianyi.chulaibar.util.OkHttpUtil;
import com.tianyi.chulaibar.util.ShareUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity {

    private EditText et_username_login, et_password_login;
    private TextView tv_change_login;
    private String phone, email, password;
    private boolean isQiYeLogin = true;


    @Override
    protected int getContentResid() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        et_username_login = (EditText) findViewById(R.id.et_username_login);
        et_password_login = (EditText) findViewById(R.id.et_password_login);
        tv_change_login = (TextView) findViewById(R.id.tv_change_loginstyle);

    }

    public void DengLuBtn(View v) {
        switch (v.getId()) {
            case R.id.denglu_sure://登录


                if (isQiYeLogin == false) {//企业登录
                    Log.d("print", "企业登录开始");
                    email = et_username_login.getText().toString().trim();
                    password = et_password_login.getText().toString().trim();

                    if (email.length() == 0 || password.length() == 0) {
                        //                    Toast.makeText(DengLuActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                        new AlertView("用户名或密码不能为空", null, null, new String[]{"知道了"}, null, LoginActivity.this,
                                AlertView.Style.Alert, null).show();
                    } else if (password.length() < 6) {
                        new AlertView("用户名或密码格式不对", null, null, new String[]{"知道了"}, null, LoginActivity.this,
                                AlertView.Style.Alert, null).show();
                    } else { //网络请求登录
                        Log.d("print", "企业登录请求");
                        String url_login = String.format(Constant.URL.LOGIN_URL, email, password, Constant.USERTYPE.user_qiye);
                        LoginByPost(url_login);

                    }


                } else if (isQiYeLogin == true) {//个人登录

                    Log.d("print", "个人登录开始");
                    phone = et_username_login.getText().toString().trim();
                    password = et_password_login.getText().toString().trim();

                    if (phone.length() == 0 || password.length() == 0) {
                        //                    Toast.makeText(DengLuActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                        new AlertView("用户名或密码不能为空", null, null, new String[]{"知道了"}, null, LoginActivity.this,
                                AlertView.Style.Alert, null).show();
                    } else if (phone.length() != 11 || password.length() < 6) {
                        new AlertView("用户名或密码格式不对", null, null, new String[]{"知道了"}, null, LoginActivity.this,
                                AlertView.Style.Alert, null).show();
                    } else { //网络请求登录
                        Log.d("print", "个人登录请求");
                        String url_login = String.format(Constant.URL.LOGIN_URL, phone, password, Constant.USERTYPE.user_geren);
                        LoginByPost(url_login);
                    }
                }

                break;
            case R.id.tv_zhuce://跳转到注册的按钮
                Intent zhuceIntent = new Intent(this, ZhuCeActivity.class);
                startActivity(zhuceIntent);
                break;
            case R.id.denglu_wangjipassword://忘记密码的按钮
                Intent getpasswordIntent = new Intent(LoginActivity.this, ZhaoHuiPassWordActivity.class);
                startActivity(getpasswordIntent);
                break;
            case R.id.tv_change_loginstyle://切换个人和企业的登录方式
                Log.d("change", "查看登录方式：-------->>>>>>");

                if (isQiYeLogin) {
                    Log.d("change", "查看登录方式：-------->>>>>>111");
                    tv_change_login.setText("切换成个人");
                    et_username_login.setHint("请输入邮箱地址");
                    et_username_login.setHintTextColor(Color.parseColor("#cbd8db"));

                    et_username_login.setText("");
                    et_password_login.setText("");

                    isQiYeLogin = false;
                } else {
                    Log.d("change", "查看登录方式：-------->>>>>>222");
                    tv_change_login.setText("切换成企业");
                    et_username_login.setHint("请输入手机号");
                    et_username_login.setHintTextColor(Color.parseColor("#cbd8db"));

                    et_username_login.setText("");
                    et_password_login.setText("");

                    isQiYeLogin = true;
                }
                break;
        }
    }

    private void LoginByPost(String url_login) {
        // String md5Str = MD5Util.getMD5Str(password);//只对密码加密
        //  String params = "?user=%s&password=%s&userType=%s";


        OkHttpUtil.downJSON(url_login, new OkHttpUtil.OnDownDataListener() {
            @Override
            public void onResponse(String url, String json) throws JSONException {

                //  Log.d("print","成功返回");

                JSONObject obj1 = new JSONObject(json);

                L.d("msg", "json is " + obj1.toString());
                String message = obj1.getString("message");
                L.d("msg", "mesg is " + message.toString());
                //
                JSONObject json_users = obj1.getJSONObject("users");

                String userId = json_users.getString("userId");
                String telphone = json_users.getString("telphone");
                String email = json_users.getString("email");
                String password = json_users.getString("password");

                L.d("tag","telphone is  "+telphone.toString());
                ShareUtil.putString("mUserId", userId);
                L.d("sssssssssssssssss", "userId  is  the " + userId);

                new Update(User.class).set("telphone=?",telphone).where("logoen=?",0).execute();
                new Update(User.class).set("email=?",email).where("logoen=?",0).execute();
                new Update(User.class).set("password=?",password).where("logoen=?",0).execute();
                new Update(User.class).set("logoen=?",1).where("logoen=?",0).execute();

                if (message.equals("ok")) {
                     Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    //登陆到主界面
                    Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(loginIntent);
                    finish();

                } else {
                    new AlertView("用户名或密码错误", null, null, new String[]{"知道了"}, null, LoginActivity.this,
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

}













