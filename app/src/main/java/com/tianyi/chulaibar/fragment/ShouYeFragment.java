package com.tianyi.chulaibar.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tianyi.chulaibar.R;
import com.tianyi.chulaibar.activity.HuoDongDetailActivty;
import com.tianyi.chulaibar.adapter.ShouYeListViewAdapter;
import com.tianyi.chulaibar.adapter.ShouYeViewPagerAdapter;
import com.tianyi.chulaibar.base.BaseFragment;
import com.tianyi.chulaibar.bean.ActivityListBean;
import com.tianyi.chulaibar.util.Constant;
import com.tianyi.chulaibar.util.GsonUtil;
import com.tianyi.chulaibar.util.OkHttpUtil;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/29 0029.
 */
public class ShouYeFragment extends BaseFragment implements View.OnClickListener{


    private ListView mListView;
    private ViewPager mViewPager;
    private  List<ImageView> pics_list;
    private List<ImageView> pointList;
    private  static int [] images={R.mipmap.vp_shouye,R.mipmap.vp_shouye,R.mipmap.vp_shouye,R.mipmap.vp_shouye};
    private LinearLayout ll_points;
    private  LinearLayout.LayoutParams  point_layoutParams;
    private LinearLayout ll_fresh_shou_ye,ll_hot_shou_ye;
    private LinearLayout headview;
    private Context mContext;
    private ShouYeListViewAdapter mAdapter;
    private TextView tv_fresh,tv_fresh_English,tv_hot,tv_hot_English;
    private List<ActivityListBean.ResultBean.ListBean> mList,fresh_list,hot_list;
    private ProgressDialog progressDialog;


   public ShouYeFragment() {
       // Required empty public constructor
   }

    @Override
    protected int getContentResid() {
        return R.layout.fragment_shou_ye;
    }

    @Override
    protected void init(View view) {

       //加载数据时的提示
       // progressDialog = ProgressDialog.show(getActivity(), "", "正在努力加载中...", true, true);

        //初始化listview
        mListView = (ListView) view.findViewById(R.id.lv_shouye );
        pointList = new ArrayList<>();

       //初始化头部视图
        initHeadview();
        //listview添加头部视图
        mListView.addHeaderView(headview);

        mList=new ArrayList<>();
        fresh_list=new ArrayList<>();
        hot_list=new ArrayList<>();

        mAdapter = new ShouYeListViewAdapter(getActivity(),mList);
        mListView.setAdapter(mAdapter);


    }

    private void initHeadview() {

        //绑定headview控件
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                AbsListView.LayoutParams.WRAP_CONTENT);
        headview = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.headview_shouye,null);
        headview.setLayoutParams(layoutParams);

        //初始化fresh和hot两个按钮
        ll_fresh_shou_ye = (LinearLayout) headview.findViewById(R.id.ll_fresh_shou_ye);
        ll_fresh_shou_ye.setOnClickListener(this);
        ll_fresh_shou_ye.setBackgroundResource(R.drawable.fresh_backgroud);
        ll_hot_shou_ye = (LinearLayout) headview.findViewById(R.id.ll_hot_shou_ye);
        ll_hot_shou_ye.setOnClickListener(this);

        tv_fresh = (TextView) headview.findViewById(R.id.tv_fresh);
        tv_fresh_English = (TextView) headview.findViewById(R.id.tv_fresh_English);
        tv_hot = (TextView) headview.findViewById(R.id.tv_hot);
        tv_hot_English = (TextView) headview.findViewById(R.id.tv_hot_English);

        mViewPager = (ViewPager) headview.findViewById(R.id.vp_shouye);
        //初始化viewpager的数据源
        pics_list = new ArrayList<>();

        //判断viewpager数据源是否小于等于3，是的话乘以2解决向左滑动空白页问题
        for(int i=0;i<images.length;i++){
            ImageView imageView = new ImageView(getActivity());
            //解决imageview 的两边留白问题
            ViewGroup.LayoutParams viewPagerImageViewParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.FILL_PARENT);
            imageView.setLayoutParams(viewPagerImageViewParams);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(images[i]);
            pics_list.add(imageView);
        }

        ShouYeViewPagerAdapter adapter = new ShouYeViewPagerAdapter(getActivity(),pics_list);
        mViewPager.setAdapter(adapter);
        //设置第一张图片
        mViewPager.setCurrentItem(6000);

        //初始化小圆点
        ll_points = (LinearLayout) headview.findViewById(R.id.ll_points);
        initPoints();
        //viewpager切换
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

//                if ( pics_list.size() > 1) { //多于1，才会循环跳转
//                    if ( position < 1) { //首位之前，跳转到末尾（N）
//                        position = pics_list.size()-2;
//                        mViewPager.setCurrentItem(position,false);
//                    } else if ( position >  pics_list.size()-2) { //末位之后，跳转到首位（1）
//                        position = 1;
//                        mViewPager.setCurrentItem(1,false); //false:不显示跳转过程的动画
//                    }
//                }

                // viewpager切换时，小圆点背景发生改变
                for (int i = 0; i < pointList.size(); i++) {
                    if (position%pointList.size()==i) {
                        ll_points.getChildAt(i).setEnabled(true);
                    }else {
                        ll_points.getChildAt(i).setEnabled(false);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initPoints() {

        ImageView pointVidew;
        point_layoutParams= new LinearLayout.LayoutParams(15,15);
        point_layoutParams.leftMargin=20;

        for (int i = 0; i < pics_list.size(); i++) {

            pointVidew= new ImageView(getActivity());
            pointVidew.setBackgroundResource(R.drawable.selector_point);

            if (i==0) {
                pointVidew.setEnabled(true);
            }else {
                pointVidew.setEnabled(false);
            }
            pointVidew.setLayoutParams(point_layoutParams);
            ll_points.setGravity(Gravity.CENTER);
            ll_points.addView(pointVidew);
            pointList.add(pointVidew);
        }
        //设置position=0和最后一个位置时，小圆点不可见，第一个小圆点为默认状态
//        ll_points.getChildAt(0).setVisibility(View.INVISIBLE);
//        ll_points.getChildAt(ll_points.getChildCount() - 1).setVisibility(View.INVISIBLE);

    }


    @Override
    protected void loadDatas() {

        Log.d("print","loadDatas:"+"开始执行");



        final String fresh_url = String.format(Constant.URL.QUERYACTIVITYLIST_FRESH_URL);//请求最新的数据源
        OkHttpUtil.downJSON(fresh_url, new OkHttpUtil.OnDownDataListener() {
                @Override
                public void onResponse(String url, String json) throws JSONException {

                    Log.d("print","成功返回首页最新json数据:"+json);

                    if(json!=null){
                        Gson gson =GsonUtil.getInstance();
                        ActivityListBean  activityListBean= gson.fromJson(json, ActivityListBean.class);
                        fresh_list.clear();
                        fresh_list.addAll(activityListBean.getResult().getList());

                        //listviewt添加数据源并设置适配器
                        mList.clear();
                        mList.addAll(fresh_list);
                        mAdapter.notifyDataSetChanged();

                    }
                }

                @Override
                public void onFailure(String url, String error) {

                }
            });


        final String hot_url = String.format(Constant.URL.QUERYACTIVITYLIST_HOT_URL,"hot");//请求最热的数据源
        OkHttpUtil.downJSON(hot_url, new OkHttpUtil.OnDownDataListener() {
            @Override
            public void onResponse(String url, String json) throws JSONException {

                Log.d("print","成功返回首页最热json数据:"+json);

                if(json!=null){
                    Gson gson =GsonUtil.getInstance();
                    ActivityListBean  activityListBean= gson.fromJson(json, ActivityListBean.class);

                    hot_list.clear();
                    hot_list.addAll(activityListBean.getResult().getList());


                }
            }

            @Override
            public void onFailure(String url, String error) {

            }
        });


        //加载完数据源后，处理listview的点击事件，跳转到活动详情界面
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//首页今日首选条目 点击事件.
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), HuoDongDetailActivty.class);

                String activityID = mList.get(position-1).getActivityId();
                //传递活动ID
                intent.putExtra("activityID", activityID);
                startActivity(intent);
            }
        });
        //加载数据的提示消失
      //  progressDialog.dismiss();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.ll_fresh_shou_ye:

                ll_fresh_shou_ye.setBackgroundResource(R.drawable.fresh_backgroud);
                ll_hot_shou_ye.setBackgroundColor(Color.parseColor("#f5f5f5"));

                tv_fresh.setTextColor(Color.parseColor("#8cc21f"));
                tv_fresh_English.setTextColor(Color.parseColor("#8cc21f"));
                tv_hot.setTextColor(Color.parseColor("#666666"));
                tv_hot_English.setTextColor(Color.parseColor("#666666"));
                //切换数据源，并刷新适配器
                mList.clear();
                mList.addAll(fresh_list);
                mAdapter.notifyDataSetChanged();

                break;

            case R.id.ll_hot_shou_ye:

                ll_hot_shou_ye.setBackgroundResource(R.drawable.fresh_backgroud);
                ll_fresh_shou_ye.setBackgroundColor(Color.parseColor("#f5f5f5"));

                tv_fresh.setTextColor(Color.parseColor("#666666"));
                tv_fresh_English.setTextColor(Color.parseColor("#666666"));
                tv_hot.setTextColor(Color.parseColor("#8cc21f"));
                tv_hot_English.setTextColor(Color.parseColor("#8cc21f"));

                //切换数据源，并刷新适配器
                mList.clear();
                mList.addAll(hot_list);
                mAdapter.notifyDataSetChanged();
                break;

        }

    }


}
