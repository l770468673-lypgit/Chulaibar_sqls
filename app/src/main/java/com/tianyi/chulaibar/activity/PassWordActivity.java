package com.tianyi.chulaibar.activity;

import android.view.View;
import android.widget.Toast;

import com.tianyi.chulaibar.R;
import com.tianyi.chulaibar.base.BaseActivity;

public class PassWordActivity extends BaseActivity {


    @Override
    protected int getContentResid() {

        return R.layout.activity_pass_word;
    }


    public void xiugaiPassWord(View view){

        switch (view.getId()){

            case R.id.iv_back_password_activity:

                finish();
                break;

            case R.id.btn_commit_password_activity:

                Toast.makeText(this,"修改密码成功",Toast.LENGTH_SHORT).show();

                break;

        }


    }

}
