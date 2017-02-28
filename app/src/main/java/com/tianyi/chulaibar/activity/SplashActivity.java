package com.tianyi.chulaibar.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.tianyi.chulaibar.R;
import com.tianyi.chulaibar.db.User;
import com.tianyi.chulaibar.util.L;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @
 * @类名称: ${}
 * @类描述: ${type_name}
 * @创建人： Lyp
 * @创建时间：${date} ${time}
 * @备注：
 */
public class SplashActivity extends Activity {
    private static final String TAG = "SplashActivity";



    private Timer mTimer;
    private int mLogoen;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        L.d(TAG, "lifecycle // onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);



        ActiveAndroid.beginTransaction();

        User user = new User();
        user.setLogoen(0);
        user.save();
        ActiveAndroid.setTransactionSuccessful();
        ActiveAndroid.endTransaction();

        List<User> userdate = new Select().from(User.class).execute();

        mLogoen = userdate.get(0).getLogoen();

        mTimer = new Timer();
        timerss();

    }

    private void timerss() {

       // int logoen = mUser.getLogoen();

        switch (mLogoen) {
            case 1:
                final Intent imain = new Intent(this, MainActivity.class); //你要转向的Activity
                TimerTask taskmain = new TimerTask() {
                    @Override
                    public void run() {
                        startActivity(imain); //执行
                        finish();
                    }

                };
                mTimer.schedule(taskmain, 1000 * 3); //10秒后
                break;
            case 0:
                final Intent ilog = new Intent(this, LoginActivity.class); //你要转向的Activity

                TimerTask tasklog = new TimerTask() {
                    @Override
                    public void run() {
                        startActivity(ilog); //执行
                        finish();
                    }
                };
                mTimer.schedule(tasklog, 1000 * 3); //10秒后
                break;
        }


    }


}
