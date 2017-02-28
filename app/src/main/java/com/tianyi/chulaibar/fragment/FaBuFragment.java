package com.tianyi.chulaibar.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bigkoo.alertview.AlertView;
import com.google.gson.Gson;
import com.tianyi.chulaibar.R;
import com.tianyi.chulaibar.activity.CostSetActivity;
import com.tianyi.chulaibar.activity.GiftSetActivity;
import com.tianyi.chulaibar.activity.MarkPositionActivity;
import com.tianyi.chulaibar.activity.PosterSetActivity;
import com.tianyi.chulaibar.activity.ProvinceListActivity;
import com.tianyi.chulaibar.base.BaseFragment;
import com.tianyi.chulaibar.bean.ActivityTypeListBean;
import com.tianyi.chulaibar.manager.HttpManager;
import com.tianyi.chulaibar.manager.ResponseHead;
import com.tianyi.chulaibar.util.Constant;
import com.tianyi.chulaibar.util.DateTimePickDialogUtil;
import com.tianyi.chulaibar.util.GsonUtil;
import com.tianyi.chulaibar.util.L;
import com.tianyi.chulaibar.util.OkHttpUtil;
import com.tianyi.chulaibar.util.ShareUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FaBuFragment extends BaseFragment implements OnClickListener, TextWatcher {

    private static final String TAG = "FaBuFragment";
    private LinearLayout ll_gengduo_fabu, ll_shangchuan_haibao, ll_shangchuan_feiyong_fabu, ll_shangchuan_liwu_fabu, ll_huodong_pic;
    private RelativeLayout rl_gengduo_fabu, rl_fenlei_fabu, rl_shangchuan_haibao, rl_city_fabu;
    private EditText et_name_fabu, et_contact_fabu, et_renshu_fabu, et_fenlei_fabu, address_info,
            et_huodongtime_start_fabu, et_huodongtime_end_fabu, et_baomingtime_start_fabu, et_baomingtime_end_fabu;
    private Button btn_fabu, btn_shangchuan_fabu, btn_feiyong_fabu, btn_liwu_fabu;
    private ToggleButton tglbtn_yinsi_fabu, tglbtn_weituo_fabu;
    private boolean isShow = false;
    public static boolean tagisown = false;

    private Spinner spinner;
    private List<String> spinnerList;
    private ImageView home_user_imgs;
    //  private String Spinner_city[]={"活动分类","亲子","娱乐","社交","运动","文化","公益","其他"};
    private ArrayAdapter<String> adapter;
    private SpinnerAdapter spinner_adapter;
    private TextView tv_fenlei_fabu, tv_detailaddress_fabu, addressText, tv_haibaostthc;
    private EditText jieshaotupian;
    private ListView listview;
    private SpinnerAdapter spinnerAdapter;
    private String initEndDateTime = null;
    private int REQUEST_CODE_ZERO = 0;
    private List<ActivityTypeListBean.ResultBean> activityType_list;  // 活动类型的集合
    private List<ActivityTypeListBean.ResultBean> popwindow_list;
    int fenlei_width;
    int fenlei_height;
    private LinearLayout lly_addview;

    private String poster_path;

    private final int IMAGE_CODE = 0x005;   //这里的IMAGE_CODE是自己任意定义的
    private final String IMAGE_TYPE = "image/*";
    private int RESULT_CODE = 2;
    private Bitmap mPhoto;

    private boolean ishanzi = true;
    //
    //    public static final int TAKE_PHOTO_XIANGJI = 1;
    //    public static final int CROP_PHOTO_ONE = 2;
    //    public static final int TAKE_CAMEAR_XIANGCE = 3;
    //    public static final int SET_CAMEAR_VIEW = 4;


    private String mPath;
    private String mUrl;


    private Uri imageUri;
    private BufferedOutputStream mBos;
    //   private String mResultactivityid;
    public static Handlersmain mHandlersmain;
    private String mResult;
    private Bitmap mBitmap;
    private Bitmap mBitmap1;
    private int mLeixingposition;
    private String mLeixingtext;

    private String mHaibaoResult;
    private String mAddress;
    private String mMapaddress;
    private int mPeoplenumber;
    private String mCityId;
    private String mProvinceId;
    private String mTheend0ftextsize;
    private String mMUserId;
    private int mCityid;
    private int mLength;
    private int mProvinceid;
    private String mHaibaostring;
    private static ArrayList<String> sNewstexthtml = null;
    private String mEndthtexttohtml;

    private String heads = "<!DOCTYPE html>" + "<html>" + "<head>" + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />" + "<title></title>" + "</head>" + "<body>";

    private String foot = "</body>" + "</html>";

    private String imahead = "<img\n src= \" ";
    private String imafoot = "/>";


    /// 坚挺文本改变的变化   改变之前
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {


        //   L.d(TAG, " beforeTextChanged 文本是 ：" + et_fenlei_fabu.getText().toString());
    }


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // L.d(TAG, "onTextChanged 文本是 ：" + et_fenlei_fabu.getText().toString());

    }


    @Override
    public void afterTextChanged(Editable s) {


        String s1 = et_fenlei_fabu.getText().toString();

        L.d(TAG, "afterTextChanged 文本是 ：" + s1);


        String[] strArray = null;
        strArray = convertStrToArray(s1);
        L.d(TAG, strArray.toString());

    }


    public class Handlersmain extends Handler {


        private String mMAttachPath;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 99) {
                Bundle data = msg.getData();
                mLeixingposition = data.getInt("position");


                //     mLeixingtext = data.getString("text");

            }
            if (msg.what == 98) {
                Bundle data = msg.getData();
                mHaibaoResult = data.getString("mResult");
                tv_haibaostthc.setText(mHaibaoResult);
            }
            if (msg.what == 97) {
                Bundle data = msg.getData();
                String mMAttachPath = data.getString("mAttachPath");
                tv_haibaostthc.setText(mMAttachPath);


            }


        }
    }

    @Override
    protected int getContentResid() {
        return R.layout.fragment_fa_bu;
    }

    @Override
    protected void init(View view) {
        mHandlersmain = new Handlersmain();

        //更多设置的布局
        ll_gengduo_fabu = (LinearLayout) view.findViewById(R.id.ll_gengduo_fabu);
        lly_addview = (LinearLayout) view.findViewById(R.id.lly_addview);
        //上传海报
        rl_shangchuan_haibao = (RelativeLayout) view.findViewById(R.id.rl_shangchuan_haibao);//获取列表的宽度

        ll_huodong_pic = (LinearLayout) view.findViewById(R.id.ll_huodong_pic);// 添加照片

        ll_shangchuan_haibao = (LinearLayout) view.findViewById(R.id.ll_shangchuan_haibao);
        //上传费用设置
        ll_shangchuan_feiyong_fabu = (LinearLayout) view.findViewById(R.id.ll_shangchuan_feiyong_fabu);

        //详细地址
        tv_detailaddress_fabu = (TextView) view.findViewById(R.id.tv_detailaddress_fabu);

        jieshaotupian = (EditText) view.findViewById(R.id.jieshaotupian);
        jieshaotupian.setFocusable(false);
        jieshaotupian.setEnabled(false);

        //省市
        addressText = (TextView) view.findViewById(R.id.address);
        tv_haibaostthc = (TextView) view.findViewById(R.id.tv_haibaostthc);
        home_user_imgs = (ImageView) view.findViewById(R.id.home_user_imgs);
        //上传礼物设置
        ll_shangchuan_liwu_fabu = (LinearLayout) view.findViewById(R.id.ll_shangchuan_liwu_fabu);

        //选择城市
        rl_city_fabu = (RelativeLayout) view.findViewById(R.id.rl_city_fabu);
        //控制更多设置的布局
        rl_gengduo_fabu = (RelativeLayout) view.findViewById(R.id.rl_gengduo_fabu);
        rl_fenlei_fabu = (RelativeLayout) view.findViewById(R.id.rl_fenlei_fabu);
        //EditText 绑定控件
        et_name_fabu = (EditText) view.findViewById(R.id.et_name_fabu);
        et_contact_fabu = (EditText) view.findViewById(R.id.et_contact_fabu);
        et_renshu_fabu = (EditText) view.findViewById(R.id.et_renshu_fabu);
        et_fenlei_fabu = (EditText) view.findViewById(R.id.et_fenlei_fabu);


        //活动分类textview
        tv_fenlei_fabu = (TextView) view.findViewById(R.id.tv_fenlei_fabu);
        tv_detailaddress_fabu.setOnClickListener(this);

        //Button 绑定控件
        btn_fabu = (Button) view.findViewById(R.id.btn_fabu);

        //ToggleButton 绑定控件
        tglbtn_yinsi_fabu = (ToggleButton) view.findViewById(R.id.tglbtn_yinsi_fabu);
        // tglbtn_weituo_fabu= (ToggleButton) view.findViewById(R.id.tglbtn_weituo_fabu);

        //设置监听事件
        rl_gengduo_fabu.setOnClickListener(this);
        rl_fenlei_fabu.setOnClickListener(this);
        ll_huodong_pic.setOnClickListener(this);
        et_fenlei_fabu.setOnClickListener(this);
        et_fenlei_fabu.addTextChangedListener(this);

        ll_shangchuan_feiyong_fabu.setOnClickListener(this);
        ll_shangchuan_liwu_fabu.setOnClickListener(this);

        btn_fabu.setOnClickListener(this);
        ll_shangchuan_haibao.setOnClickListener(this);
        rl_city_fabu.setOnClickListener(this);

        tglbtn_yinsi_fabu.setOnClickListener(this);
        //        tglbtn_weituo_fabu.setOnClickListener(this);
        //初始化活动时间和报名时间的选择器
        initDateTimeDialog(view);

        //初始化活动分类的集合
        activityType_list = new ArrayList<>();

        lodadateactivityid();


        sNewstexthtml = new ArrayList<>();
    }

    // 活动主题
    private void lodadateactivityid() {
        // 活动主题
        OkHttpUtil.downJSON(Constant.URL.GETNEWACTIVITYID, new OkHttpUtil.OnDownDataListener() {
            @Override
            public void onResponse(String url, String json) throws JSONException {
                JSONObject obj_all = new JSONObject(json);
                String result = obj_all.getString("result");
                ShareUtil.putString("activityId", result);

                L.d(TAG, "result is " + result.toString());

            }

            @Override
            public void onFailure(String url, String error) {

            }
        });
    }

    //获取活动类型的数据源
    @Override
    protected void loadDatas() {
        super.loadDatas();

        //获取活动类型的数据源
        String getLeiXingData_rul = String.format(Constant.URL.QUERYACTIVITYTYPELIST_URL, "");

        OkHttpUtil.downJSON(getLeiXingData_rul, new OkHttpUtil.OnDownDataListener() {
            @Override
            public void onResponse(String url, String json) throws JSONException {
                Log.d("print", "成功返回活动类型的数据源");
                JSONObject obj1 = new JSONObject(json);
                int code = obj1.getInt("code");
                if (code == 0) {
                    Log.d("print", "获取活动类型数据成功");
                    if (json != null) {
                        Gson gson = GsonUtil.getInstance();
                        ActivityTypeListBean activityListBean = gson.fromJson(json, ActivityTypeListBean.class);
                        //listviewt添加数据源并设置适配器
                        activityType_list.clear();
                        activityType_list = activityListBean.getResult();
                        // spinner_adapter.notifyDataSetChanged();
                    }
                }
                // return url;
            }

            @Override
            public void onFailure(String url, String error) {

            }
        });


    }


    // create 一个包含自定义view的PopupWindow
    private PopupWindow initFenleiPopwindow(Context context) {

        // popwindow 里面承载着 分类的数据
        popwindow_list = new ArrayList<>();
        for (int i = 0; i < activityType_list.size(); i++) {
            popwindow_list.add(activityType_list.get(i));

        }

        final PopupWindow window;
        window = new PopupWindow(context);


        Log.d("print", "makePopupWindow方法执行了。。。。。");

        listview = new ListView(context);
        listview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        spinnerAdapter = new SpinnerAdapter(context, popwindow_list);
        listview.setAdapter(spinnerAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                spinnerAdapter.changeSelected(position);

                Message msg = new Message();
                msg.what = 99;

                Bundle b = new Bundle();
                b.putInt("position", position);
                //    b.putString("text", typeName);
                msg.setData(b);

                mHandlersmain.sendMessage(msg);

                window.dismiss();
            }
        });

        //动态设置window内部的布局
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.addView(listview);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER);
        window.setContentView(linearLayout); //选择布局方式
        //设置popwindow的背景图片
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        //设置popwindow的高和宽
        fenlei_width = rl_shangchuan_haibao.getWidth();
        fenlei_height = rl_shangchuan_haibao.getHeight();
        window.setWidth(fenlei_width);
        window.setHeight(fenlei_height * popwindow_list.size());
        // 设置PopupWindow外部区域是否可触摸
        window.setFocusable(true); //设置PopupWindow可获得焦点
        window.setTouchable(true); //设置PopupWindow可触摸
        window.setOutsideTouchable(true); //设置非PopupWindow区域可触摸
        // window.showAtLocation(button, Gravity.RIGHT | Gravity.BOTTOM, 10,10);

        return window;
    }

    //
    private void initDateTimeDialog(View view) {

        // 活动开始和结束的时间控件
        et_huodongtime_start_fabu = (EditText) view.findViewById(R.id.et_huodongtime_start_fabu);
        et_huodongtime_end_fabu = (EditText) view.findViewById(R.id.et_huodongtime_end_fabu);
        et_baomingtime_start_fabu = (EditText) view.findViewById(R.id.et_baomingtime_start_fabu);
        et_baomingtime_end_fabu = (EditText) view.findViewById(R.id.et_baomingtime_end_fabu);

        et_huodongtime_start_fabu.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        getActivity(), initEndDateTime);
                dateTimePicKDialog.dateTimePicKDialog(et_huodongtime_start_fabu);

            }
        });
        et_huodongtime_end_fabu.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        getActivity(), initEndDateTime);
                dateTimePicKDialog.dateTimePicKDialog(et_huodongtime_end_fabu);

            }
        });

        et_baomingtime_start_fabu.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        getActivity(), initEndDateTime);
                dateTimePicKDialog.dateTimePicKDialog(et_baomingtime_start_fabu);
            }
        });
        et_baomingtime_end_fabu.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        getActivity(), initEndDateTime);
                dateTimePicKDialog.dateTimePicKDialog(et_baomingtime_end_fabu);
            }
        });


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.rl_gengduo_fabu:  //显示或者隐藏更多设置

                if (isShow) {

                    ll_gengduo_fabu.setVisibility(View.GONE);
                    isShow = false;
                } else {

                    ll_gengduo_fabu.setVisibility(View.VISIBLE);
                    isShow = true;
                }

                break;

            case R.id.btn_fabu:  //发布，提交数据

                mMUserId = ShareUtil.getString("mUserId");
                L.d(TAG, " 用户编号   is  the " + mMUserId);
                String et_name_fabustring = et_name_fabu.getText().toString().trim();
                L.d(TAG, "活动主题  is " + et_name_fabustring.toString());


                mTheend0ftextsize = et_fenlei_fabu.getText().toString();
                //
                String[] strArray = null;
                strArray = convertStrToArray(mTheend0ftextsize);
                L.d(TAG, strArray.toString());
                printArray(strArray);
                //                String.copyValueOf(sNewstexthtml)
                StringBuffer sb = new StringBuffer();
                for (String s : sNewstexthtml) {
                    sb.append(s);
                }
                mEndthtexttohtml = sb.toString();
                //   L.d(TAG, "活动详情 文本是 ：" + mTheend0ftextsize);
                L.d(TAG, "活动详情 文本是 is ：" + heads + mEndthtexttohtml + foot);

                String activityId = ShareUtil.getString("activityId");

                L.d(TAG, "活动编号   is " + activityId);

                //联系方式
                String phoinetrim = et_contact_fabu.getText().toString().trim();
                L.d(TAG, "联系方式  is " + phoinetrim.toString());
                // 参与人数
                String numberpeople = et_renshu_fabu.getText().toString().trim();


                if (numberpeople.length() > 0) {
                    mPeoplenumber = Integer.parseInt(numberpeople);
                    L.d(TAG, "参加人数 is " + mPeoplenumber);
                } else if (numberpeople.length() <= 0 || numberpeople == null) {
                    new AlertView("活动人数不能为空", null, null, new String[]{"知道了"}, null, getActivity(),
                            AlertView.Style.Alert, null).show();
                }

                // 报名时间
                String baomingshijiajn = et_baomingtime_start_fabu.getText().toString().trim();
                String baomingshijiajns = baomingshijiajn;
                L.d(TAG, "报名时间 is " + baomingshijiajns);
                // 截止报名
                String jiezhibaoming = et_baomingtime_end_fabu.getText().toString().trim();
                String jiezhibaomings = jiezhibaoming;
                L.d(TAG, "截止报名 is " + jiezhibaomings);
                //   发布时间
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日    HH-mm-ss     ");
                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                String fabushijian = formatter.format(curDate);


                //活动开始日期
                String huodongkaishi = et_huodongtime_start_fabu.getText().toString().trim();
                String huodongkaishishijian = huodongkaishi;
                L.d(TAG, "活动开始 is " + huodongkaishishijian);

                //  发布活动的周几
                String weekOfDate = getWeekOfDate(curDate);
                L.d(TAG, "周几 is " + weekOfDate);
                String huodongjieshu = et_huodongtime_end_fabu.getText().toString().trim();
                String huodongjieshushijian = huodongjieshu;
                L.d(TAG, "活动结束时间 is " + huodongjieshushijian);
                if (mCityId != null || mProvinceId != null) {

                    mCityid = Integer.parseInt(mCityId);
                    L.d(TAG, "城市id is " + mCityid);
                    mLength = mCityId.length();


                    String substringpriid = mProvinceId.substring(mLength);
                    mProvinceid = Integer.parseInt(substringpriid);
                } else {
                    new AlertView("城市id不能为空", null, null, new String[]{"知道了"}, null, getActivity(),
                            AlertView.Style.Alert, null).show();
                }


                L.d(TAG, "省 id is " + mProvinceid);
                if (activityId == null) {
                    new AlertView("活动编号不能为空", null, null, new String[]{"知道了"}, null, getActivity(),
                            AlertView.Style.Alert, null).show();
                } else if (et_name_fabustring == null) {
                    new AlertView("活动名称不能为空", null, null, new String[]{"知道了"}, null, getActivity(),
                            AlertView.Style.Alert, null).show();
                } else if (mLeixingposition <= 0) {
                    new AlertView("活动类型不能为空", null, null, new String[]{"知道了"}, null, getActivity(),
                            AlertView.Style.Alert, null).show();
                } else if (mCityid <= 0) {
                    new AlertView("活动城市不能为空", null, null, new String[]{"知道了"}, null, getActivity(),
                            AlertView.Style.Alert, null).show();
                } else if (huodongkaishishijian == null) {
                    new AlertView("活动开始时间不能为空", null, null, new String[]{"知道了"}, null, getActivity(),
                            AlertView.Style.Alert, null).show();
                } else if (huodongjieshushijian == null) {
                    new AlertView("活动结束时间不能为空", null, null, new String[]{"知道了"}, null, getActivity(),
                            AlertView.Style.Alert, null).show();
                } else if (jiezhibaomings == null) {
                    new AlertView("活动截止时间不能为空", null, null, new String[]{"知道了"}, null, getActivity(),
                            AlertView.Style.Alert, null).show();
                } else if (baomingshijiajns == null) {
                    new AlertView("报名时间不能为空", null, null, new String[]{"知道了"}, null, getActivity(),
                            AlertView.Style.Alert, null).show();
                    //                } else if (mHaibaoResult == null) {
                    //                    new AlertView("海报不能为空", null, null, new String[]{"知道了"}, null, getActivity(),
                    //                            AlertView.Style.Alert, null).show();
                } else {
                    L.d(TAG, "活动类型 id  " + mLeixingposition);
                    mHaibaostring = tv_haibaostthc.getText().toString().trim();

                    L.d(TAG, "海报的 图片" + mHaibaostring.toString());
                    L.d(TAG, "详细地址is  " + "崂山区");
                    L.d(TAG, "是否私人活动  " + tagisown);
                    Call<ResponseHead> responseHeadCall = HttpManager.getInstance().getHttpClient().getaddActivity(
                            activityId, et_name_fabustring, mMUserId, mLeixingposition,
                            mHaibaostring, mCityid, mProvinceid, "崂山区", huodongkaishishijian + ":12",
                            huodongjieshushijian + ":12", weekOfDate, phoinetrim, mPeoplenumber, heads + mEndthtexttohtml + foot, baomingshijiajns + ":12", jiezhibaomings + ":12", tagisown);
                    L.d(TAG, "info is   " + responseHeadCall.toString());
                    responseHeadCall.enqueue(new Callback<ResponseHead>() {
                        @Override
                        public void onResponse(Call<ResponseHead> call, Response<ResponseHead> response) {
                            ResponseHead body = response.body();
                            if (body != null) {

                                String info = body.getInfo();

                                Toast.makeText(getActivity(), "info is  " + info.toString(), Toast.LENGTH_SHORT).show();
                                L.d(TAG, "info is   " + info.toString());
                            } else {
                                L.d(TAG, "body is " + response.body());
                            }


                        }

                        @Override
                        public void onFailure(Call<ResponseHead> call, Throwable t) {
                            L.d(TAG, "info is   " + t.toString());
                        }
                    });
                }

                //  }


                break;
            case R.id.et_fenlei_fabu:  //点击文本的时候

                break;

            case R.id.ll_huodong_pic:  // 添加照片
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setNegativeButton("从相册选取", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        File outputImage = new File(Environment.getExternalStorageDirectory(), "output_image.jpg");
                        try {
                            if (outputImage.exists()) {
                                outputImage.delete();
                            }
                            outputImage.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Intent intent = new Intent("android.intent.action.GET_CONTENT");
                        intent.setType("image/*");
                        startActivityForResult(intent, Constant.CODE.TAKE_CAMEAR_XIANGCE);

                    }

                });
                builder.setPositiveButton("拍照", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                 /* 创建一个File对象，用来存储摄像头拍下的照片 File类构造方法接收两个参数 ①SD卡路径 通过Environment对象的getExternalStorageDirectory()方法获取sd的根目录
                 * ② 文件名称*/
                        File outputImage = new File(Environment.getExternalStorageDirectory(), "output_image.jpg");
                        try {
                            if (outputImage.exists()) {//判断文件是否存在，弱存在就删除。
                                outputImage.delete();
                            }
                            outputImage.createNewFile();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                   /* 调用Uri的fromFile()方法将File对象转化成Uri对象。 */
                        imageUri = Uri.fromFile(outputImage);
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");//设置隐形Intent启动的匹配条件,IMAGE_CAPTURE表示启动相机。
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//调用Intent的putExtra()方法设置拍下照片储存的路径，跟File储存的路径一样。
                        startActivityForResult(intent, Constant.CODE.TAKE_PHOTO_XIANGJI);//startActivityForResult方式启动活动。即启动相机 参数1传入Intent对象，参数2传入一个用来返回的自定义值。在onActivityResult中做判断


                        //                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        //                        startActivityForResult(cameraIntent, Constant.CODE.BIAOSHI_CODE);

                    }
                });
                builder.create().show();

                break;


            case R.id.ll_shangchuan_haibao:  //上传海报按钮

                Intent posterIntent = new Intent(getActivity(), PosterSetActivity.class);
                startActivity(posterIntent);

                break;
            case R.id.ll_shangchuan_feiyong_fabu:  //费用设置按钮
                Intent costIntent = new Intent(getActivity(), CostSetActivity.class);
                startActivity(costIntent);

                break;
            case R.id.ll_shangchuan_liwu_fabu:  //礼物设置按钮

                Intent giftIntent = new Intent(getActivity(), GiftSetActivity.class);
                startActivity(giftIntent);
                break;

            case R.id.rl_fenlei_fabu:  //分类设置

                // 新建一个popwindow，并显示里面的内容
                PopupWindow popupWindow = initFenleiPopwindow(getActivity());
                //popwindow与按钮之间的相对位置
                popupWindow.showAsDropDown(rl_fenlei_fabu);

                break;


            case R.id.rl_city_fabu:  //选择城市
                Intent intent = new Intent(getContext(), ProvinceListActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ZERO);

                break;


            case R.id.tv_detailaddress_fabu:  //详细地址

                Intent markPositionIntent = new Intent(getActivity(), MarkPositionActivity.class);


                startActivityForResult(markPositionIntent, Constant.CODE.REUQEST_CODE2);

                break;


            case R.id.tglbtn_yinsi_fabu:  //隐私活动设置
                if (tagisown) {
                    tglbtn_yinsi_fabu.setBackgroundDrawable(getResources().getDrawable(R.mipmap.button_close));
                    tagisown = false;

                    L.d(TAG, "tagisown is  " + tagisown);
                } else {

                    tglbtn_yinsi_fabu.setBackgroundDrawable(getResources().getDrawable(R.mipmap.button_open));
                    tagisown = true;

                    L.d(TAG, "tagisown is  " + tagisown);
                }
                break;

        }

    }

    //根据日期得周几
    public static String getWeekOfDate(Date date) {
        String[] weekDaysName = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        String[] weekDaysCode = {"0", "1", "2", "3", "4", "5", "6"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysCode[intWeek];
    }

    public static String[] convertStrToArray(String str) {
        String[] strArray = null;
        strArray = str.split(","); //拆分字符为"," ,然后把结果交给数组strArray
        //String strArrays = "<p>"+strArray + "</p>";
        return strArray;
    }

    //使用StringTokenizer实现
    //api说明：StringTokenizer 是出于兼容性的原因而被保留的遗留类
    //（虽然在新代码中并不鼓励使用它）。建议所有寻求此功能的人使用
    //String 的 split 方法或 java.util.regex 包
    //    public static String[] convertStrToArray2(String str) {
    //        StringTokenizer st = new StringTokenizer(str, "#");//把","作为分割标志，然后把分割好的字符赋予StringTokenizer对象。
    //
    //        String[] strArray = new String[st.countTokens()];//通过StringTokenizer 类的countTokens方法计算在生成异常之前可以调用此 tokenizer 的 nextToken 方法的次数。
    //        int i = 0;
    //        while (st.hasMoreTokens()) {//看看此 tokenizer 的字符串中是否还有更多的可用标记。
    //            strArray[i++] = st.nextToken();//返回此 string tokenizer 的下一个标记。
    //        }
    //        return strArray;
    //    }

    //输出数组
    public static void printArray(String[] array) {
        for (int i = 0; i < array.length; i++) {

            //array[i]="<p>"+array[i]+"</p>";
            System.out.print(array[i]);


            if ((array[i].getBytes().length == array[i].length())) {

                if (array[i].length() > 0) {
                    array[i] = "<img \n src =\"" + array[i] + "\" />";
                    L.d(TAG, "array[i] is" + array[i]);
                } else {
                }

            } else {
                if (array[i].length() > 0) {
                    array[i] = "<p>" + array[i] + "</p>";
                    L.d(TAG, "array[i] is" + array[i]);
                } else {
                }


            }


            sNewstexthtml.add(array[i]);


            //            if(i==array.length-1){
            //                System.out.print("\n");
            //            }else{
            //                System.out.print(",");
            //            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case Constant.CODE.TAKE_PHOTO_XIANGJI:
                if (resultCode == -1) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, Constant.CODE.CROP_PHOTO_ONE);
                }
                break;
            case Constant.CODE.CROP_PHOTO_ONE:

                if (resultCode == -1) {
                    //
                    try {
                        mBitmap1 = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "output_image.jpg"))));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    ladDatetoCamear();
                    L.d(TAG, "this pic name is " + mResult);
                    mHandlersmain.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            setDrawable(mResult, mBitmap1);

                            L.d(TAG, "相机： " + et_fenlei_fabu.getText().toString());
                        }
                    }, 3000);
                }

                break;

            case Constant.CODE.TAKE_CAMEAR_XIANGCE:
                Log.d("MainActivity", "???????: " + resultCode);
                if (resultCode == -1) {
                    String imagePath = getPath(getActivity(), data.getData()); //
                    Uri imageUri = Uri.fromFile(new File(imagePath));
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("crop", true);
                    intent.putExtra("scale", true);
                    intent.putExtra("return-data", false);

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "output_image.jpg")));

                    startActivityForResult(intent, Constant.CODE.SET_CAMEAR_VIEW);
                }
                break;
            case Constant.CODE.SET_CAMEAR_VIEW:

                //xiangce
                if (resultCode == -1) {
                    try {
                        mBitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "output_image.jpg"))));
                        ladDatetoCamear();

                        mHandlersmain.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                setDrawable(mResult, mBitmap);
                                L.d(TAG, "mResult is " + mResult);
                            }
                        }, 3000);

                        //		picture.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }

                break;

            //            case Constant.CODE.SET_HAIBAOI_VIEW_HAIBAO:
            //                if (resultCode == -1) {
            //
            //                    Intent intent = getActivity().getIntent();
            //                    Bundle extras = intent.getExtras();
            //                    String mAttachPath = extras.getString("mAttachPath");
            //
            //
            //
            //                }
            //
            //                break;
            default:
                break;

        }


        if (requestCode == REQUEST_CODE_ZERO && resultCode == 1) {

            mAddress = data.getStringExtra("address");
            mCityId = data.getStringExtra("cityId");
            mProvinceId = data.getStringExtra("provinceId");

            addressText.setText(mAddress);
        }

        if (requestCode == Constant.CODE.REUQEST_CODE2 && resultCode == Constant.CODE.RESULT_CODE3) {
            mMapaddress = data.getStringExtra("mapaddress");
            tv_detailaddress_fabu.setText(mMapaddress);
            L.i("print", "返回值----" + mMapaddress);
        }


    }


    private void setDrawable(String faceTitle, Bitmap bitmap) {
        int selectionStart = et_fenlei_fabu.getSelectionStart();
        Spannable mingzi = et_fenlei_fabu.getText().insert(selectionStart, "," + faceTitle + ",");
        Drawable drawable = new BitmapDrawable(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(drawable, faceTitle + ".gif", ImageSpan.ALIGN_BASELINE);
        mingzi.setSpan(span, selectionStart, selectionStart + ("," + faceTitle + ",").length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

    }

    public void ladDatetoCamear() {

        File file = new File(Environment.getExternalStorageDirectory(), "output_image.jpg");//将要保存图片的路径
        String url = Constant.URL.TIJIAOTUPIANURL;

        OkHttpUtil.downpic(url, file, new OkHttpUtil.OnDownDataListener() {

            @Override
            public void onResponse(String url, String json) throws JSONException {

                JSONObject object = new JSONObject(json);
                mResult = object.getString("result");
                if (mResult != null) {
                    //    L.d(TAG, "result is  " + mResult.toString());
                }
                //  return mResult;

            }

            @Override
            public void onFailure(String url, String error) {

            }
        });


    }


    @TargetApi(19)
    public static String getPath(Activity context, Uri imageUri) {
        if (context == null || imageUri == null)
            return null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imageUri)) {
            if (isExternalStorageDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(imageUri)) {
                String id = DocumentsContract.getDocumentId(imageUri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } // MediaStore (and general)
        else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(imageUri))
                return imageUri.getLastPathSegment();
            return getDataColumn(context, imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


    class SpinnerAdapter extends BaseAdapter {

        private Context context;
        private List<ActivityTypeListBean.ResultBean> popwindow_list;

        int mSelect = 0;   //选中项

        public void changeSelected(int position) {

            if (position != mSelect) {
                mSelect = position;
                notifyDataSetChanged();
                //更改选中后文本
                RelativeLayout relativeLayout = (RelativeLayout) listview.getChildAt(mSelect);
                TextView textViews = (TextView) relativeLayout.getChildAt(1);

                tv_fenlei_fabu.setText(textViews.getText());
                tv_fenlei_fabu.setTextColor(Color.parseColor("#848484"));
                tv_fenlei_fabu.setTextSize(12);
            }

        }

        public SpinnerAdapter(Context context, List<ActivityTypeListBean.ResultBean> popwindow_list) {
            this.context = context;
            this.popwindow_list = popwindow_list;

        }

        @Override
        public int getCount() {
            return popwindow_list.size();
        }

        @Override
        public Object getItem(int position) {
            return popwindow_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = null;

            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.item_pop_fenlei_fabu, parent, false);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_pop_fenlei_fabu);
                viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_pop_fenlei_fabu);

                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            if (mSelect == position) {

                viewHolder.imageView.setSelected(true);
            } else {

                viewHolder.imageView.setSelected(false);
            }


            String typeName = popwindow_list.get(position).getTypeName();
            viewHolder.textView.setText(typeName);

            return convertView;

        }

        class ViewHolder {
            ImageView imageView;
            TextView textView;
        }

    }

}
