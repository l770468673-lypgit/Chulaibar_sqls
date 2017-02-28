package com.tianyi.chulaibar.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.tianyi.chulaibar.R;
import com.tianyi.chulaibar.adapter.ActivityDetailGiftListViewAdapter;
import com.tianyi.chulaibar.adapter.ActivityDetailRecomendListViewAdapter;
import com.tianyi.chulaibar.base.BaseActivity;
import com.tianyi.chulaibar.bean.ActivityDetailBean;
import com.tianyi.chulaibar.bean.Beannloaction;
import com.tianyi.chulaibar.util.Constant;
import com.tianyi.chulaibar.util.DensityUtil;
import com.tianyi.chulaibar.util.GsonUtil;
import com.tianyi.chulaibar.util.L;
import com.tianyi.chulaibar.util.OkHttpUtil;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HuoDongDetailActivty extends BaseActivity implements View.OnClickListener {


    private boolean flag = false;
    private String activityID;
    private String detail_url;
    private ActivityDetailBean activityDetailBean;
    private ImageView iv_head;
    private CircleImageView iv_touxiang_activity_detail;
    private TextView tv_name_hoster, tv_gaugnzhu, tv_name_huodong, tv_money,
            tv_collection_num, tv_preview_num, tv_baoming_num, tv_baoming_limit, tv_start_time, tv_end_time,
            tv_baoming_start_time, tv_baoming_end_time, tv_telephone, tv_baoming, tv_baoming_next, tv_form_queding;

    private ListView lv_gift_activity_detail, lv_tuijian_activity_detail;

    private int[] pictures = {R.mipmap.holder, R.mipmap.holder, R.mipmap.holder, R.mipmap.holder,
            R.mipmap.holder, R.mipmap.holder, R.mipmap.holder, R.mipmap.holder, R.mipmap.holder, R.mipmap.holder
            , R.mipmap.holder, R.mipmap.holder, R.mipmap.holder, R.mipmap.holder, R.mipmap.holder};

    private List<ImageView> list_pictures;//报名
    private LinearLayout ll_baoming;
    private RelativeLayout rl_baoming_more, rl_total, rl_read_more;

    private List<ActivityDetailBean.ResultBean.PartListBean> partList;//报名数据源

    private List<ActivityDetailBean.ResultBean.GiftListBean> giftListBeanList;//礼物
    private ActivityDetailGiftListViewAdapter giftAdapter;

    private List<ActivityDetailBean.ResultBean.RecommendListBean> recommendList;//推荐
    ActivityDetailRecomendListViewAdapter recomendAdapter;
    private MapView mapView;
    private AMap aMap;
    private String address;
    private WebView webview;
    private HorizontalScrollView hsl_activity_detail;
    private LinearLayout baominglayout;
    private LinearLayout formlayout, ll_h5;
    private PopupWindow pop_baoming;
    private EditText et_name_form_baoming, et_phone_form_baoming, et_beizhu_form_baoming;

    @Override
    protected int getContentResid() {
        return R.layout.activity_huo_dong_detail_activty;
    }


    @Override
    protected void init() {
        //初始化控件
        iv_head = (ImageView) findViewById(R.id.iv_head_activity_detail);//头部图片
        iv_touxiang_activity_detail = (CircleImageView) findViewById(R.id.iv_touxiang_activity_detail);//头像
        tv_name_hoster = (TextView) findViewById(R.id.tv_name_hoster);//举办方名称
        tv_gaugnzhu = (TextView) findViewById(R.id.tv_gaugnzhu);//关注
        tv_name_huodong = (TextView) findViewById(R.id.tv_name_huodong);//活动名称
        tv_money = (TextView) findViewById(R.id.tv_money);//报名费用
        tv_collection_num = (TextView) findViewById(R.id.tv_collection_num);//收藏数量
        tv_preview_num = (TextView) findViewById(R.id.tv_preview_num);//浏览数量
        tv_baoming_num = (TextView) findViewById(R.id.tv_baoming_num);//报名人数
        tv_baoming_limit = (TextView) findViewById(R.id.tv_baoming_limit);//报名人数限制
        tv_start_time = (TextView) findViewById(R.id.tv_start_time);//活动开始时间
        tv_end_time = (TextView) findViewById(R.id.tv_end_time);//活动结束时间
        tv_baoming_start_time = (TextView) findViewById(R.id.tv_baoming_start_time);//报名开始时间
        tv_baoming_end_time = (TextView) findViewById(R.id.tv_baoming_end_time);//报名结束时间
        tv_telephone = (TextView) findViewById(R.id.tv_telephone);//联系电话

        tv_baoming = (TextView) findViewById(R.id.tv_baoming_activity_detail);//报名按钮
        tv_baoming.setOnClickListener(this);

        partList = new ArrayList<>();//报名的数据源

        giftListBeanList = new ArrayList<>();
        lv_gift_activity_detail = (ListView) findViewById(R.id.lv_gift_activity_detail);//礼物的listview

        recommendList = new ArrayList<>();
        lv_tuijian_activity_detail = (ListView) findViewById(R.id.lv_tuijian_activity_detail);//精彩推荐的listview
        //底部试图
        TextView textView = new TextView(this);
        textView.setHeight(DensityUtil.dip2px(this, 46));
        textView.setWidth(100);
        textView.setBackgroundColor(Color.parseColor("#ffffff"));
        lv_tuijian_activity_detail.addFooterView(textView);


        hsl_activity_detail = (HorizontalScrollView) findViewById(R.id.hsl_activity_detail);
        ll_baoming = (LinearLayout) findViewById(R.id.ll_baoming);//当前报名的人:LinearLayout
        rl_baoming_more = (RelativeLayout) findViewById(R.id.rl_baoming_more);//更多报名

        rl_total = (RelativeLayout) findViewById(R.id.rl_total);


        //地图 mapview
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        //地图
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        //        LatLonPoint lp = new LatLonPoint(29.528364,106.518892);//设置经纬度
        //        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lp.getLatitude(), lp.getLongitude()), 14));


        //更多报名
        rl_baoming_more = (RelativeLayout) findViewById(R.id.rl_baoming_more);
        rl_baoming_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ll_h5 = (LinearLayout) findViewById(R.id.ll_h5);

        //展开阅读
        rl_read_more = (RelativeLayout) findViewById(R.id.rl_read_more);
        rl_read_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == false) {

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    ll_h5.setLayoutParams(params);
                    flag = true;
                } else {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            DensityUtil.dip2px(HuoDongDetailActivty.this, 100));
                    ll_h5.setLayoutParams(params);
                    flag = false;

                }

            }
        });


    }

    @Override
    protected void loadDatas() {

        //获取当前活动的id和详情页的url
        activityID = getIntent().getStringExtra("activityID");
        Log.d("print", "activityID:     " + activityID);

        detail_url = String.format(Constant.URL.QUERYACTIVITYINFO_URL, activityID);
        //请求当前页的数据
        OkHttpUtil.downJSON(detail_url, new OkHttpUtil.OnDownDataListener() {
                    @Override
                    public void onResponse(String url, String json) throws JSONException {

                        L.d("print", "请求到了数据：");
                        L.d("print", "活动详情页的json：" + json);

                        if (json != null) {
                            //所有的数据源
                            Gson gson = GsonUtil.getInstance();
                            activityDetailBean = gson.fromJson(json, ActivityDetailBean.class);


                            //picasso加载顶部的海报,图片暂时不能用
                            String path = activityDetailBean.getResult().getActivity().getPosterPic();
                            String poster_url = Constant.URL.IMAGE_HEAD_URL + path;
                            Log.d("print", "头部海报的url：" + poster_url);
                            Picasso.with(HuoDongDetailActivty.this).load(poster_url).into(iv_head);


                            //活动基本信息
                            //iv_head  设置海报 posterPic
                            //iv_touxiang_activity_detail  设置小头像  companyLogo
                            //  tv_name_hoster.setText((String) activityDetailBean.getResult().getActivity().getCompanyName());//公司名称，暂无数据

                            tv_name_huodong.setText(activityDetailBean.getResult().getActivity().getActivityName());//活动名称
                            tv_collection_num.setText("(" + activityDetailBean.getResult().getActivity().getCollections() + ")");//收藏数量
                            tv_preview_num.setText("" + activityDetailBean.getResult().getActivity().getClick());//浏览数量
                            tv_baoming_num.setText("" + activityDetailBean.getResult().getActivity().getParticipation());//报名人数
                            tv_baoming_limit.setText("(限" + activityDetailBean.getResult().getActivity().getNumber() + "人)");//报名人数限制
                            tv_start_time.setText(activityDetailBean.getResult().getActivity().getStartTime());//活动开始时间
                            tv_end_time.setText(activityDetailBean.getResult().getActivity().getEndTime());//活动结束时间
                            tv_baoming_start_time.setText("" + activityDetailBean.getResult().getActivity().getSignUpTime());//报名开始时间
                            tv_baoming_end_time.setText("" + activityDetailBean.getResult().getActivity().getSignEndTime());//报名结束时间

                            //tv_telephone.setText((String)activityDetailBean.getResult().getActivity().getAPhone()); //aPhone 活动电话
                            //根据传回来的address ,地图定位
                            address = activityDetailBean.getResult().getActivity().getAddress();

                            Log.i("print", "期待的地址adress--" + address);

                            String position_url = String.format(Constant.URL.GETDATAS, address);
                            OkHttpUtil.downJSON(position_url, new OkHttpUtil.OnDownDataListener() {
                                @Override
                                public void onResponse(String url, String json) throws JSONException {
                                    if (json != null) {
                                        final Beannloaction beannloaction = new Gson().fromJson(json, Beannloaction.class);
                                        final List<Beannloaction.GeocodesBean> geocodes =
                                                beannloaction.getGeocodes();

                                        if (geocodes == null) {
                                            return;
                                        }

                                        final String location = geocodes.get(0).getLocation();

                                        Double lng = Double.valueOf(location.substring(0, location.indexOf(",")));
                                        Double lat = Double.valueOf(location.substring(location.indexOf(",") + 1));

                                        Log.i("print", "期望的返回来的经纬度--维度" + lat + "经度--" + lng);

                                        //                                                    mapView = (MapView) findViewById(R.id.mapview);
                                        //                                                    mapView.onCreate(savedInstanceState);
                                        //                                                    aMap = mapView.getMap();
                                        //地图
                                        //  aMap.setMapType(AMap.MAP_TYPE_NORMAL);
                                        //                                LatLonPoint lp = new LatLonPoint(lng, lat);//设置经纬度
                                        //                                aMap.clear();
                                        LatLng lng1 = new LatLng(lat, lng);
                                        MarkerOptions markerOption = new MarkerOptions();
                                        markerOption.position(lng1);
                                        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                                                .decodeResource(getResources(), R.mipmap.mark_detail)));
                                        aMap.addMarker(markerOption);
                                        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lng1, 14));


                                        // doSearchQuery(new LatLonPoint(lng,lat));
                                    }
                                }

                                @Override
                                public void onFailure(String url, String error) {

                                }
                            });

                            //活动详情 h5
                            webview = (WebView) findViewById(R.id.webview_activity_detail);
                            webview.getSettings().setJavaScriptEnabled(true);
                            String html = (String) activityDetailBean.getResult().getActivity().getRemark();
                            L.d("tag", "html is the " + html);
                            webview.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
                            webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);  //这句话将图片缩放至屏幕宽度，非常好嘛
                            webview.getSettings().setJavaScriptEnabled(true);
                            // 设置可以支持缩放
                            webview.getSettings().setSupportZoom(true);
                            //                      // 设置出现缩放工具
                            webview.getSettings().setBuiltInZoomControls(true);
                            //扩大比例的缩放
                            webview.getSettings().setUseWideViewPort(true);
                            //自适应屏幕
                            webview.getSettings().setLoadWithOverviewMode(true);

                            webview.setWebChromeClient(new WebChromeClient());


                            //webview.loadUrl("http://baidu.com/");
                            //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
                            //                    webview.setWebViewClient(new WebViewClient(){
                            //                        @Override
                            //                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            //                            // TODO Auto-generated method stub
                            //                            //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                            //                            view.loadUrl(url);
                            //                            return true;
                            //                        }
                            //                    });
                        }

                        // 礼物
                        giftListBeanList.clear();
                        giftListBeanList.addAll(activityDetailBean.getResult().getGiftList());
                        giftAdapter = new ActivityDetailGiftListViewAdapter(HuoDongDetailActivty.this, giftListBeanList);
                        lv_gift_activity_detail.setAdapter(giftAdapter);
                        setListViewHeightBasedOnChildren(lv_gift_activity_detail);
                        giftAdapter.notifyDataSetChanged();


                        //当前报名
                        partList.clear();
                        partList.addAll(activityDetailBean.getResult().getPartList());
                        list_pictures = new ArrayList<>();//目前用假数据
                        if (partList.size() == 0) {
                            hsl_activity_detail.setVisibility(View.GONE);
                        }


                        for (int i = 0; i < partList.size(); i++) {//把网络请求下来的图片存入线性布局中,此处假数据

                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            params.setMargins(DensityUtil.dip2px(HuoDongDetailActivty.this, 16), 0, 0, 0);
                            final LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(HuoDongDetailActivty.this).inflate(R.layout.item_baoming_activity_detial, null);
                            linearLayout.setLayoutParams(params);
                            //添加活动人头像
                            ImageView iv_baoming = (ImageView) linearLayout.findViewById(R.id.iv_item_baoming_activity_detail);
                            //添加活动人名字
                            TextView tv_baoming = (TextView) linearLayout.findViewById(R.id.tv_item_baoming_activity_detail);
                            tv_baoming.setText(partList.get(i).getUserName());


                            //点击事件
                            linearLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Toast.makeText(HuoDongDetailActivty.this, "点击我了。。。。", Toast.LENGTH_SHORT).show();


                                }
                            });
                            //动态添加布局
                            ll_baoming.addView(linearLayout);
                        }


                        //精彩推荐
                        recommendList.clear();
                        recommendList.addAll(activityDetailBean.getResult().getRecommendList());
                        recomendAdapter = new ActivityDetailRecomendListViewAdapter(HuoDongDetailActivty.this, recommendList);
                        lv_tuijian_activity_detail.setAdapter(recomendAdapter);
                        setListViewHeightBasedOnChildren(lv_tuijian_activity_detail);
                        recomendAdapter.notifyDataSetChanged();

                        //加载完数据源后，处理listview的点击事件，跳转到活动详情界面
                        lv_tuijian_activity_detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {//精彩推荐的listview的点击事件.
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Intent intent = new Intent(HuoDongDetailActivty.this, HuoDongDetailActivty.class);
                                String activityID = recommendList.get(position).getActivityId();
                                //传递活动ID
                                intent.putExtra("activityID", activityID);
                                startActivity(intent);
                            }
                        });


                    }

                    @Override
                    public void onFailure(String url, String error) {

                    }

                }
        );

    }

    /**
     * 解决嵌套listview只显示一条数据的方法
     */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }


    @Override
    public void onClick(View v) {  //控件的点击事件处理

        switch (v.getId()) {

            case R.id.tv_baoming_activity_detail: //报名按钮


                final PopupWindow pop_form = initFormPopwindow(HuoDongDetailActivty.this);//获取报名联系人pop
                pop_form.showAtLocation(rl_total, Gravity.BOTTOM, 0, 0);
                tv_form_queding.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        //获取报名信息
                        String name = et_name_form_baoming.getText().toString();
                        String phone = et_phone_form_baoming.getText().toString();
                        String beizhu = et_beizhu_form_baoming.getText().toString();

                        //调用接口上传

                        Toast.makeText(HuoDongDetailActivty.this, "报名信息：" + name + phone + beizhu, Toast.LENGTH_SHORT).show();

                        pop_form.dismiss();//报名pop消失

                        //设置pop之外的区域为完全透明
                        //                        WindowManager.LayoutParams params = HuoDongDetailActivty.this.getWindow().getAttributes();
                        //                        params.alpha = 1f;
                        //                        HuoDongDetailActivty.this.getWindow().setAttributes(params);

                    }
                });


                // 新建报名pop
                //  pop_baoming = initBaomingPopwindow(this);
                //popwindow显示位置

                //                tv_baoming_next.setOnClickListener(new View.OnClickListener() {
                //                    @Override
                //                    public void onClick(View v) {
                //
                //                        pop_baoming.dismiss();//报名pop消失
                //
                //                        final PopupWindow pop_form = initFormPopwindow(HuoDongDetailActivty.this);//获取报名联系人pop
                //                        pop_form.showAtLocation(baominglayout, Gravity.BOTTOM, 0, 0);
                //
                //                        tv_form_queding.setOnClickListener(new View.OnClickListener() {
                //                            @Override
                //                            public void onClick(View v) {
                //                                Toast.makeText(HuoDongDetailActivty.this, "输入信息完成，已确定", Toast.LENGTH_SHORT).show();
                //                                pop_form.dismiss();//报名pop消失
                //
                //                                //设置pop之外的区域为完全透明
                //                                WindowManager.LayoutParams params = HuoDongDetailActivty.this.getWindow().getAttributes();
                //                                params.alpha = 1f;
                //                                HuoDongDetailActivty.this.getWindow().setAttributes(params);
                //
                //                            }
                //                        });
                //
                //
                //                    }
                //                });
                //
                //                pop_baoming.showAtLocation(baominglayout, Gravity.BOTTOM, 0, 0);


                break;


        }


    }


    //    private PopupWindow initBaomingPopwindow(Context context) {//报名的popwindow
    //
    //        Log.d("print", "开始创建报名的popwindow。。。。。");
    //
    //        //获取xml布局
    //
    //        baominglayout = (LinearLayout) View.inflate(this,
    //                R.layout.order_baoming_activty_detail, null);
    //
    //        tv_baoming_next = (TextView) baominglayout.
    //                findViewById(R.id.tv_baoming_next_activity_detail);//报名选票后下一步
    //
    //        PopupWindow window = new PopupWindow(baominglayout);
    //        window.setContentView(baominglayout);
    //
    //        //设置popwindow的背景图片
    //        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
    //        //设置popwindow的高和宽
    //
    //        window.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
    //        window.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    //        // 设置PopupWindow外部区域是否可触摸
    //        window.setFocusable(true); //设置PopupWindow可获得焦点
    //        window.setTouchable(true); //设置PopupWindow可触摸
    //        // window.setOutsideTouchable(true); //设置非PopupWindow区域可触摸
    //        // window.showAtLocation(button, Gravity.RIGHT | Gravity.BOTTOM, 10,10);
    //
    //        window.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);//处理软键盘遮挡
    //        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    //
    //        //设置pop之外的区域为半透明
    //        WindowManager.LayoutParams params = HuoDongDetailActivty.this.getWindow().getAttributes();
    //        params.alpha = 0.7f;
    //        HuoDongDetailActivty.this.getWindow().setAttributes(params);
    //
    //        return window;
    //    }


    private PopupWindow initFormPopwindow(Context context) {//联系人的popwindow

        Log.d("print", "开始创建联系人的popwindow。。。。。");

        //获取xml布局

        formlayout = (LinearLayout) View.inflate(this,
                R.layout.order_baoming_form_activty_detail, null);

        tv_form_queding = (TextView) formlayout.findViewById(R.id.tv_queding_baoming_form_activity_detail);//确定按钮
        //输入框
        et_name_form_baoming = (EditText) formlayout.findViewById(R.id.et_name_form_baoming);
        et_phone_form_baoming = (EditText) formlayout.findViewById(R.id.et_phone_form_baoming);
        et_beizhu_form_baoming = (EditText) formlayout.findViewById(R.id.et_beizhu_form_baoming);


        PopupWindow window = new PopupWindow(formlayout);
        window.setContentView(formlayout);

        //设置popwindow的背景图片
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        //设置popwindow的高和宽
        window.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        window.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置PopupWindow外部区域是否可触摸
        window.setFocusable(true); //设置PopupWindow可获得焦点
        window.setTouchable(true); //设置PopupWindow可触摸
        //  window.setOutsideTouchable(true); //设置非PopupWindow区域可触摸
        // window.showAtLocation(button, Gravity.RIGHT | Gravity.BOTTOM, 10,10);

        window.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);//处理软键盘遮挡
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return window;
    }


    @Override
    protected void onResume() {
        super.onResume();

        Log.d("print", "执行了onResume()。。。。。。。");
    }
}
