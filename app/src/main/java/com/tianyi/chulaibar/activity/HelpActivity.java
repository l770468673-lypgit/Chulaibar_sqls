package com.tianyi.chulaibar.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.tianyi.chulaibar.R;
import com.tianyi.chulaibar.base.BaseActivity;

public class HelpActivity extends BaseActivity {

    @Override
    protected int getContentResid() {
        return R.layout.activity_help;
    }


    /**
     * 获取软件版本号
     * @param context
     * @return
     */
    public static int getVerCode(Context context) {
        int verCode = -1;
        try {
            //注意："com.example.try_downloadfile_progress"对应AndroidManifest.xml里的package="……"部分
            verCode = context.getPackageManager().getPackageInfo(
                    "com.tianyi.chulaibar", 0).versionCode;

            Log.d("version","versionCode:"+verCode);

        } catch (PackageManager.NameNotFoundException e) {
            Log.e("msg",e.getMessage());
        }
        return verCode;
    }

    /**
     * 获取版本名称
     * @param context
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(
                    "com.tianyi.chulaibar", 0).versionName;
            Log.d("version","versionCode:"+verName);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("msg",e.getMessage());
        }
        return verName;
    }

    public void click(View view){

        getVerCode(this);
        getVerName(this);

    }

}
