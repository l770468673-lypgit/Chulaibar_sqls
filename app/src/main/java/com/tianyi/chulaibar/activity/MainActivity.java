package com.tianyi.chulaibar.activity;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.tianyi.chulaibar.R;
import com.tianyi.chulaibar.base.BaseActivity;
import com.tianyi.chulaibar.fragment.FaBuFragment;
import com.tianyi.chulaibar.fragment.FaXianFragment;
import com.tianyi.chulaibar.fragment.MineFragment;
import com.tianyi.chulaibar.fragment.ShouYeFragment;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity {

    private RadioGroup rg_Button;
    private static Boolean isExit = false;//双击退出APP
    private ImageView iv_line ;
    private ShouYeFragment shouYeFragment;
    private FaXianFragment faXianFragment;
    private FaBuFragment faBuFragment;
    private MineFragment mineFragment;
    private FragmentManager fragmentManager;
    //存储选中位置标签
    private int checkFlag = 0;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;

    private static final long MAX_TIME = 2000;

    private boolean isBackPressed = false;
    @Override
    protected int getContentResid() {

        return R.layout.activity_main;
    }


    @Override
    protected void init() {

        sp=MainActivity.this.getSharedPreferences("gridview",MODE_PRIVATE);
        edit = sp.edit(); //编辑文件



        //初始化四个fragment
        fragmentManager = getSupportFragmentManager();
        shouYeFragment = new ShouYeFragment();
        faXianFragment = new FaXianFragment();
        faBuFragment = new FaBuFragment();
        mineFragment = new MineFragment();
       //初始化底部导航栏
        rg_Button = (RadioGroup) findViewById(R.id.rg_navigation);
        //设置首页为默认展示状态
        rg_Button.getChildAt(0).performClick();//模拟点击第一个RB  (使用代码的方式)
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.replace(R.id.ll_activity_main, shouYeFragment);
        fragmentTransaction.commit();

        iv_line = (ImageView) findViewById(R.id.iv_line_activity_mian);
    }

    @Override
    protected void loadDatas() {

        rg_Button.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                rg_Button.getChildAt(0).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (!shouYeFragment.isAdded() && !shouYeFragment.isVisible()
                                    && !shouYeFragment.isRemoving()) {

                                FragmentTransaction fragmentTransaction = fragmentManager
                                        .beginTransaction();

                                fragmentTransaction.replace(R.id.ll_activity_main, shouYeFragment);

                                fragmentTransaction.commit();

                                checkFlag = 0;

                                //切换fragment时，清空上次sp中的数据
                                edit.clear();
                                edit.commit();
                            }
                        }
                    });

                rg_Button.getChildAt(1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!faXianFragment.isAdded() && !faXianFragment.isVisible()
                                && !faXianFragment.isRemoving()) {

                            FragmentTransaction fragmentTransaction = fragmentManager
                                    .beginTransaction();
                            //设置发现界面的动画效果（从上向下平移进入，从下向上平移移出）
//                            fragmentTransaction.setCustomAnimations(R.anim.faxian_in,R.anim.faxian_out);

                            fragmentTransaction.replace(R.id.ll_activity_main, faXianFragment);
                            fragmentTransaction.commit();
                            //默认隐藏底部导航栏
                            rg_Button.setVisibility(View.GONE);
                            iv_line.setVisibility(View.GONE);

                            faXianFragment.setPassValue(new FaXianFragment.PassValue() {
                                @Override
                                public void pass(int flag) {


                                    if(flag == 1){//点击Fragment确定按钮，接口回调传值1.

                                        rg_Button.setVisibility(View.VISIBLE);
                                        iv_line.setVisibility(View.VISIBLE);

                                    }else if(flag == 2){//回到发现的搜索界面，接口回调传值3.

                                        rg_Button.setVisibility(View.GONE);
                                        iv_line.setVisibility(View.GONE);

                                    }else if(flag == 3){//点击Fragment取消按钮，接口回调传值2.

                                        rg_Button.setVisibility(View.VISIBLE);
                                        iv_line.setVisibility(View.VISIBLE);

                                        //根据chackFlag的值设置RadioButton选中状态
                                        rg_Button.getChildAt(checkFlag).performClick();

                                        //根据chackFlag的值replace对应的Fragment
                                        fanhuishezhi();


                                    }

                                }
                            });

                        }


                    }
                });
                rg_Button.getChildAt(2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!faBuFragment.isAdded() && !faBuFragment.isVisible()
                                && !faBuFragment.isRemoving()) {

                            FragmentTransaction fragmentTransaction = fragmentManager
                                    .beginTransaction();

                            fragmentTransaction.replace(R.id.ll_activity_main, faBuFragment);

                            fragmentTransaction.commit();

                            checkFlag = 2;
                            //切换fragment时，清空上次sp中的数据
                            edit.clear();
                            edit.commit();
                        }
                    }
                });
                rg_Button.getChildAt(3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!mineFragment.isAdded() && !mineFragment.isVisible()
                                && !mineFragment.isRemoving()) {
                            FragmentTransaction fragmentTransaction = fragmentManager
                                    .beginTransaction();
                            fragmentTransaction.replace(R.id.ll_activity_main, mineFragment);

                            fragmentTransaction.commit();

                            checkFlag = 3;

                            //切换fragment时，清空上次sp中的数据
                            edit.clear();
                            edit.commit();
                        }
                    }
                });

            }
        });


    }


//
//    @Override
//    //双击退出当前的Activity，且屏蔽软键盘的双击退出
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        // TODO Auto-generated method stub
//
//      //  InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
//
//        if(keyCode == KeyEvent.KEYCODE_BACK){
//           //判断软键盘是否弹出,弹出则屏蔽软键盘的回退键功能，没有弹出执行双击退出，解决发现页面EditText删除搜索文字问题
//            if(getWindow().getAttributes().softInputMode!= WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED){
//                exitBy2Click();
//            }else {
//
//            }
//
//        }
//        return false;
//    }

//    private void exitBy2Click() {
//        // TODO Auto-generated method stub
//        Timer tExit = null;
//        if (isExit == false) {
//            isExit = true; // 准备退出
//            Toast.makeText(this, "双击退出", Toast.LENGTH_SHORT).show();
//            tExit = new Timer();
//            tExit.schedule(new TimerTask() {
//                public void run() {
//                    isExit = false; // 取消退出
//                }
//            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
//
//        }
//
//        else {
//            finish();
//            System.exit(0);
//        }
//    }
    //双击退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == event.getKeyCode()) {
            if (isBackPressed) {
                finish();
            } else {
                //				new Timer().schedule(task, when);
                isBackPressed = true;
                Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();

                new Timer().schedule(new TimerTask() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        isBackPressed = false;
                    }
                }, MAX_TIME);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //根据chackFlag的值replace对应的Fragment

    public void fanhuishezhi(){

        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        switch (checkFlag){

            case 0:

                fragmentTransaction.replace(R.id.ll_activity_main, shouYeFragment);

                fragmentTransaction.commit();

                break;
            case 2:

                fragmentTransaction.replace(R.id.ll_activity_main, faBuFragment);

                fragmentTransaction.commit();

                break;
            case 3:

                fragmentTransaction.replace(R.id.ll_activity_main, mineFragment);

                fragmentTransaction.commit();

                break;

        }
    }





    }





