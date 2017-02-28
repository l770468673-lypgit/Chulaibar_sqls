package com.tianyi.chulaibar.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tianyi.chulaibar.R;
import com.tianyi.chulaibar.adapter.ActivityCityGridViewAdapter;
import com.tianyi.chulaibar.adapter.ActivityFeeGridViewAdapter;
import com.tianyi.chulaibar.adapter.ActivityTimeGridViewAdapter;
import com.tianyi.chulaibar.adapter.ActivityTypeGridViewAdapter;
import com.tianyi.chulaibar.adapter.FaXianListViewAdapter;
import com.tianyi.chulaibar.base.BaseFragment;
import com.tianyi.chulaibar.bean.ActivityListBean;
import com.tianyi.chulaibar.bean.ActivityTypeListBean;
import com.tianyi.chulaibar.bean.HotCityBean;
import com.tianyi.chulaibar.util.Constant;
import com.tianyi.chulaibar.util.GsonUtil;
import com.tianyi.chulaibar.util.OkHttpUtil;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class FaXianFragment extends BaseFragment implements View.OnClickListener{

    private ImageView iv_back;
    private GridView gridview_city,gridview_time,gridview_activityType,gridview_fee;
    private TextView textview_fragment_faxian_queding,tv_leixing_second,tv_time_second,tv_feiyong_second;
    private EditText et1_search_faxian,et2_search_faxian;
    private LinearLayout linearlayout_fragment_faxian_frist,
            linearlayout_fragment_faxian_second,ll_choose;
    private RelativeLayout rl_search_first;
    private ListView lv_faxian_second;
    private List<ActivityListBean.ResultBean.ListBean> mlist;


    private  PassValue passValue;
    private ListView listview;
    private int item_height=60;

    private List<HotCityBean.CityListBean>  hotCityBean_list;//热门城市
    private ActivityCityGridViewAdapter hotCity_adapter;

    private String[] time_name= {"全部","本周","今天","明天","周末","下一周","选择日期"}; //活动时间
    private ActivityTimeGridViewAdapter time_adapter;

    private List<ActivityTypeListBean.ResultBean> activityType_list;//活动类型
    private ActivityTypeGridViewAdapter activityType_adapter;

    private String[] fee_name= {"全部","收费","免费"};//活动费用
    private ActivityFeeGridViewAdapter fee_adapter;

    private int cityId=-1;
    private int typeId=-1;
    private String time="全部";
    private String fee="全部";

    private SharedPreferences sp;
    private SharedPreferences.Editor edit;

    private FaXianListViewAdapter adapter;

    public FaXianFragment() {
        // Required empty public constructor
    }

    public void setPassValue(PassValue passValue){

        this.passValue = passValue;
    }


    @Override
    protected int getContentResid() {
        return R.layout.fragment_fa_xian;
    }

    @Override
    protected void init(View view) {

        mlist=new ArrayList();

        sp=getActivity().getSharedPreferences("gridview",MODE_PRIVATE);


        //初始化发现fragment的两套布局
        linearlayout_fragment_faxian_frist = (LinearLayout) view.findViewById(R.id.linearlayout_fragment_faxian_frist);
        linearlayout_fragment_faxian_second = (LinearLayout) view.findViewById(R.id.linearlayout_fragment_faxian_second);


        iv_back = (ImageView) view.findViewById(R.id.iv_back_detail_faxian);
        iv_back.setOnClickListener(this);

        //确定键
        textview_fragment_faxian_queding = (TextView) view.findViewById(R.id.textview_fragment_faxian_queding);
        textview_fragment_faxian_queding.setOnClickListener(this);
        //发现结果界面的筛选textview
        tv_leixing_second= (TextView) view.findViewById(R.id.tv_leixing_second);
        tv_leixing_second.setOnClickListener(this);

        tv_time_second= (TextView) view.findViewById(R.id.tv_time_second);
        tv_time_second.setOnClickListener(this);
        tv_feiyong_second= (TextView) view.findViewById(R.id.tv_feiyong_second);
        tv_feiyong_second.setOnClickListener(this);

        //搜索框
        et1_search_faxian= (EditText) view.findViewById(R.id.et1_search_faxian);
        et2_search_faxian= (EditText) view.findViewById(R.id.et2_search_faxian);


        //初始化发现的展示界面的ListView
        lv_faxian_second = (ListView) view.findViewById(R.id.lv_faxian_second);


        //初始化热门城市的GridView
        initCityGridView(view);

        //初始化时间的GridView
        initActivityTimeGridView(view);

        //初始化活动类型的GridView
        initActivityTypeGridView(view);

        //初始化费用的GridView
        initActivityFeeGridView(view);
    }

    private void initActivityFeeGridView(View view) {

        int fee_line_count;
        //初始化xingshi的gridview
        gridview_fee = (GridView) view.findViewById(R.id.gridview_xingshi);
        //去掉本身的点击效果
        gridview_fee.setSelector(new ColorDrawable(Color.TRANSPARENT));
        //根据数据源的数量给gridview动态设置权重
        if (fee_name.length != 0) {

            //设置行间距和列间距
            gridview_fee.setVerticalSpacing(15);
            gridview_fee.setHorizontalSpacing(0);
            //设置适配器
            fee_adapter = new ActivityFeeGridViewAdapter(getActivity(), fee_name);
            gridview_fee.setAdapter(fee_adapter);
        }

    }


    private void initActivityTimeGridView(View view) {

        int time_line_count;
        //初始化time的gridview
        gridview_time = (GridView) view.findViewById(R.id.gridview_time);
        //去掉本身的点击效果
        gridview_time.setSelector(new ColorDrawable(Color.TRANSPARENT));
        //根据数据源的数量给gridview动态设置权重
        if (time_name.length != 0) {

            //设置行间距和列间距
            gridview_time.setVerticalSpacing(15);
            gridview_time.setHorizontalSpacing(0);
            //设置适配器
            time_adapter = new ActivityTimeGridViewAdapter(getActivity(), time_name);
            gridview_time.setAdapter(time_adapter);
        }

    }

    private void initActivityTypeGridView(View view) {

        gridview_activityType = (GridView) view.findViewById(R.id.gridview_fenlei);
        //去掉本身的点击效果
        gridview_activityType.setSelector(new ColorDrawable(Color.TRANSPARENT));
        activityType_list= new ArrayList<>();

        String getActivityType_rul = String.format(Constant.URL.QUERYACTIVITYTYPELIST_URL,"");



        OkHttpUtil.downJSON(getActivityType_rul, new OkHttpUtil.OnDownDataListener() {
            @Override

            public void onResponse(String url, String json) throws JSONException {


                    Log.d("print","获取活动类型数据json:"+json);
                    if(json!=null){
                        Gson gson = GsonUtil.getInstance();
                        ActivityTypeListBean activityTypeListBean= gson.fromJson(json, ActivityTypeListBean.class);
                        activityType_list.clear();
                        activityType_list.addAll(activityTypeListBean.getResult());

                        Log.d("print","activityType_list.size():"+activityType_list.size());
                        //根据数据源的数量给gridview动态设置权重
                        if (activityType_list.size() != 0) {

                            //设置行间距和列间距
                            gridview_city.setVerticalSpacing(15);
                            gridview_city.setHorizontalSpacing(0);
                            //设置适配器
                            activityType_adapter=new ActivityTypeGridViewAdapter(getActivity(),activityType_list);
                            gridview_activityType.setAdapter(activityType_adapter);
                            activityType_adapter.notifyDataSetChanged();
                        }

                    }
            }

            @Override
            public void onFailure(String url, String error) {

            }
        });

    }

    private void initListViewSecond(ActivityListBean activityListBean) {

        //初始化listview
        mlist.clear();
        if((ArrayList) activityListBean.getResult().getList()!=null){
            mlist.addAll((ArrayList) activityListBean.getResult().getList());//?空指针
        }

        Log.e("data",mlist.toString());
         adapter = new FaXianListViewAdapter(getActivity(),mlist);
        lv_faxian_second.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


    private void initCityGridView(View view) {

        gridview_city = (GridView) view.findViewById(R.id.gridview_city);
        //去掉本身的点击效果
        gridview_city.setSelector(new ColorDrawable(Color.TRANSPARENT));
        hotCityBean_list=new ArrayList<>();

        int city_count=7;
        String getHotCityData_rul = String.format(Constant.URL.QUERYYCITYHOT_URL, city_count);
        OkHttpUtil.downJSON(getHotCityData_rul, new OkHttpUtil.OnDownDataListener() {
            @Override
            public void onResponse(String url, String json) throws JSONException {

                Log.d("print","成功返回热门城市的数据源");
                Log.d("print","热门城市的数据源："+json);

//                 JSONObject obj1 = new JSONObject(json);

                    if(json!=null){

                        Gson gson = GsonUtil.getInstance();
                        HotCityBean hotCityBean= gson.fromJson(json, HotCityBean.class);
                        hotCityBean_list.clear();
                        hotCityBean_list.addAll(hotCityBean.getCityList());

                        Log.d("print", "1-----------------------" + hotCityBean_list.size());

                        //根据数据源的数量给gridview动态设置权重
                        if (hotCityBean_list.size() != 0) {
                            Log.d("print", "2-----------------------" + hotCityBean_list.size());

                            //设置行间距和列间距
                            gridview_city.setVerticalSpacing(15);
                            gridview_city.setHorizontalSpacing(0);

                            Log.i("print", "期望的数据源---" + hotCityBean_list.size());

                            //设置适配器
                            hotCity_adapter=new ActivityCityGridViewAdapter(getActivity(),hotCityBean_list);
                            gridview_city.setAdapter(hotCity_adapter);
                            hotCity_adapter.notifyDataSetChanged();
                        }

                    }

            }

            @Override
            public void onFailure(String url, String error) {

            }
        });

    }

    @Override
    protected void loadDatas() {


    }


    @Override
    public void onClick(View v) {
         //切换发现的搜索界面和展示界面
        switch (v.getId()) {
//            case R.id.iv_close_faxian://取消按钮监听
//
//                passValue.pass(3);
//                break;
            case R.id.textview_fragment_faxian_queding://从搜索界面跳转到结果界面

                //获取存入的cityId
               cityId= sp.getInt("cityId",-1);
               Log.d("print","cityId:"+cityId);

                //time
                time= sp.getString("time","全部");
                Log.d("print","time:"+time);

                //typeId
                typeId= sp.getInt("typeId",-1);
                Log.d("print","typeId:"+typeId);

                //fee
                fee= sp.getString("fee","全部");
                Log.d("print","fee:"+fee);

                //搜索活动
                String keywords = et1_search_faxian.getText().toString();

                //String url = String.format(Constant.URL.QUERYACTIVITYLIST_URL,keywords,cityId,time,typeId,fee,"1","20");
                String url = String.format(Constant.URL.QUERYACTIVITYLIST_URL,keywords);
                Log.d("print","url:----->"+url);


                if (true){
                    OkHttpUtil.downJSON(url, new OkHttpUtil.OnDownDataListener() {
                        @Override
                        public void onResponse(String url, String json) throws JSONException {


                            Log.d("print","成功返回首页json数据:"+json);

                            if(json!=null){

                                Gson gson = GsonUtil.getInstance();
                                ActivityListBean activityListBean= gson.fromJson(json, ActivityListBean.class);
                                if(activityListBean!=null){
                                    initListViewSecond(activityListBean);
                                }

//                                list_all =activityListBean.getResult().getList();
//
//                                //listviewt添加数据源并设置适配器
//                                mList.clear();
//                                mList.addAll(list_all);
//                                mAdapter.notifyDataSetChanged();

                            }
                        }

                        @Override
                        public void onFailure(String url, String error) {

                        }
                    });
                }

                passValue.pass(1);
                linearlayout_fragment_faxian_frist.setVisibility(View.GONE);
                linearlayout_fragment_faxian_second.setVisibility(View.VISIBLE);

                tv_leixing_second.setTextColor(Color.parseColor("#02a7ab"));
                tv_time_second.setTextColor(Color.parseColor("#808080"));
                tv_feiyong_second.setTextColor(Color.parseColor("#808080"));



//                Log.d("print","搜索活动:"+"开始执行");
//                String url = String.format(Constant.URL.QUERYACTIVITYLIST_URL);
//                Log.d("print","url:"+url);
//
//                OkHttpUtil.downJSON(url, new OkHttpUtil.OnDownDataListener() {
//                    @Override
//                    public void onResponse(String url, String json) throws JSONException {
//
//                        Log.d("print","成功返回");
//
//                        JSONObject obj1 = new JSONObject(json);
//
//                        String message= obj1.getString("message");
//                        String info= obj1.getString("info");
//
//                        if (message.equals("ok")) {
//
//                            Toast.makeText(getActivity(), "登录成功", Toast.LENGTH_SHORT).show();
//                            //登陆到主界面
//                            Intent loginIntent = new Intent(getActivity(), MainActivity.class);
//                            startActivity(loginIntent);
//
//                        }else {
//                            new AlertView("用户名或密码错误", null, null, new String[]{"知道了"}, null, getActivity(),
//                                    AlertView.Style.Alert, null).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(String url, String error) {
//
//                    }
//                });


                break;

            case R.id.iv_back_detail_faxian://从结果页面回到搜索界面

                Log.d("back","iv_back_detail_faxian执行了");

                passValue.pass(2);
                linearlayout_fragment_faxian_frist.setVisibility(View.VISIBLE);
                linearlayout_fragment_faxian_second.setVisibility(View.GONE);
                break;


            case R.id.tv_leixing_second://类型:全部，亲子，娱乐，社交，运动。文化，公益，其他

                tv_leixing_second.setTextColor(Color.parseColor("#02a7ab"));
                tv_time_second.setTextColor(Color.parseColor("#808080"));
                tv_feiyong_second.setTextColor(Color.parseColor("#808080"));

               //弹出分类的pop
                int fenlei_type=0;
                PopupWindow leixing_pop = initFenleiPopwindow(getActivity(),activityType_list,fenlei_type);
                //popwindow与按钮之间的相对位置
                leixing_pop.showAsDropDown(tv_leixing_second);

                break;

            case R.id.tv_time_second://时段：本周，今天，明天，周末，下一周，选择日期
                tv_leixing_second.setTextColor(Color.parseColor("#808080"));
                tv_time_second.setTextColor(Color.parseColor("#02a7ab"));
                tv_feiyong_second.setTextColor(Color.parseColor("#808080"));

                //弹出时段的pop
               // String [] time_data={"时段","本周","今天","明天","周末","下一周","选择日期"};
                List <String>time_list= new ArrayList<>();
                for (int i = 0; i < time_name.length; i++) {

                    time_list.add(time_name[i]);
                }

                int time_type=1;
                PopupWindow time_pop = initTimeAndFeePopwindow(getActivity(),time_list,time_type);
                //popwindow与按钮之间的相对位置
                time_pop.showAsDropDown(tv_time_second);


                break;

            case R.id.tv_feiyong_second://费用：收费，免费

                tv_leixing_second.setTextColor(Color.parseColor("#808080"));
                tv_time_second.setTextColor(Color.parseColor("#808080"));
                tv_feiyong_second.setTextColor(Color.parseColor("#02a7ab"));


                List <String>fee_list= new ArrayList<>();
                for (int i = 0; i < fee_name.length; i++) {

                    fee_list.add(fee_name[i]);
                }

                int feiyong_type=2;
                PopupWindow fee_pop = initTimeAndFeePopwindow(getActivity(),fee_list,feiyong_type);
                //popwindow与按钮之间的相对位置
                fee_pop.showAsDropDown(tv_feiyong_second);


                break;

        }

    }

          //定义接口用于传递返回哪个fragment的值
    public interface PassValue{

        void pass(int flag);

    }

    // 创建分类的pop

    private PopupWindow initFenleiPopwindow(Context context,List<ActivityTypeListBean.ResultBean> fenlei_list,  int type) {

        WindowManager windowManager = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();

        int height = item_height*fenlei_list.size();

        Log.d("screen","文本的高度："+item_height);
        Log.d("screen","手机屏幕的宽度："+width);
        Log.d("screen","手机屏幕的高度："+height);


        final PopupWindow window;
        window = new PopupWindow(context);


        Log.d("print","makePopupWindow方法执行了。。。。。");

        listview = new ListView(context);
       // listview.setLayoutParams(new ViewGroup.LayoutParams(width, height*spinnerList.size()));


        listview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        final FenLeiAdapter adapter =new FenLeiAdapter(context,fenlei_list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.changeSelected(position);
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
        //设置popwindow的高和宽,获取屏幕的宽度和高度

        window.setWidth(width);
        window.setHeight(height);

        // 设置PopupWindow外部区域是否可触摸
        window.setFocusable(true); //设置PopupWindow可获得焦点
        window.setTouchable(true); //设置PopupWindow可触摸
        window.setOutsideTouchable(true); //设置非PopupWindow区域可触摸
        // window.showAtLocation(button, Gravity.RIGHT | Gravity.BOTTOM, 10,10);

        return window;
    }

    //创建time和fee的pop
    private PopupWindow initTimeAndFeePopwindow(Context context,List<String>list,  int type) {

        WindowManager windowManager = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();

        int height = item_height*list.size();

        Log.d("screen","文本的高度："+item_height);
        Log.d("screen","手机屏幕的宽度："+width);
        Log.d("screen","手机屏幕的高度："+height);


        final PopupWindow window;
        window = new PopupWindow(context);


        Log.d("print","makePopupWindow方法执行了。。。。。");

        listview = new ListView(context);
        // listview.setLayoutParams(new ViewGroup.LayoutParams(width, height*spinnerList.size()));


        listview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        final TimeAndFeeAdapter adapter =new TimeAndFeeAdapter(context,list,type);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.changeSelected(position);
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
        //设置popwindow的高和宽,获取屏幕的宽度和高度

        window.setWidth(width);
        window.setHeight(height);

        // 设置PopupWindow外部区域是否可触摸
        window.setFocusable(true); //设置PopupWindow可获得焦点
        window.setTouchable(true); //设置PopupWindow可触摸
        window.setOutsideTouchable(true); //设置非PopupWindow区域可触摸
        // window.showAtLocation(button, Gravity.RIGHT | Gravity.BOTTOM, 10,10);

        return window;
    }


    class FenLeiAdapter extends BaseAdapter {

        private Context context;
        List<ActivityTypeListBean.ResultBean> fenlei_list;

        public FenLeiAdapter(Context context, List<ActivityTypeListBean.ResultBean> fenlei_list ){
            this.context=context;
            this.fenlei_list=fenlei_list;

        }


        int mSelect = 0;   //选中项
        public void changeSelected(int position){

            if(position!=mSelect){
                mSelect=position;
                notifyDataSetChanged();
                //更改选中后文本
                RelativeLayout relativeLayout= (RelativeLayout) listview.getChildAt(mSelect);
               item_height= relativeLayout.getHeight();
                TextView textView= (TextView) relativeLayout.getChildAt(0);

                        tv_leixing_second.setText(textView.getText());
                        tv_leixing_second.setTextColor(Color.parseColor("#02a7ab"));
                        tv_leixing_second.setTextSize(12);

            }

        }


        @Override
        public int getCount() {
            return fenlei_list.size();
        }

        @Override
        public Object getItem(int position) {
            return fenlei_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder=null;

            if(convertView==null){
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.item_pop_fenlei_faxian, parent, false);
                viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_pop_fenlei_fabu);

                convertView.setTag(viewHolder);

            }else {
                viewHolder= (ViewHolder) convertView.getTag();
            }


            viewHolder.textView.setText(fenlei_list.get(position).getTypeName());

            return convertView;

        }

        class ViewHolder{

            TextView textView;
        }

    }



    class TimeAndFeeAdapter extends BaseAdapter {

        private Context context;
        private List<String> list;
        private int type;

        public TimeAndFeeAdapter(Context context, List<String> list ,int type){
            this.context=context;
            this.list=list;
            this.type=type;
        }


        int mSelect = 0;   //选中项
        public void changeSelected(int position){

            if(position!=mSelect){
                mSelect=position;
                notifyDataSetChanged();
                //更改选中后文本
                RelativeLayout relativeLayout= (RelativeLayout) listview.getChildAt(mSelect);
                item_height= relativeLayout.getHeight();
                TextView textView= (TextView) relativeLayout.getChildAt(0);

                switch (type){

//                    case 0:
//
//                        tv_leixing_second.setText(textView.getText());
//                        tv_leixing_second.setTextColor(Color.parseColor("#02a7ab"));
//                        tv_leixing_second.setTextSize(12);
//
//                        break;

                    case 1:

                        tv_time_second.setText(textView.getText());
                        tv_time_second.setTextColor(Color.parseColor("#02a7ab"));
                        tv_time_second.setTextSize(12);

                        break;

                    case 2:

                        tv_feiyong_second.setText(textView.getText());
                        tv_feiyong_second.setTextColor(Color.parseColor("#02a7ab"));
                        tv_feiyong_second.setTextSize(12);

                        break;

                }
            }

        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder=null;

            if(convertView==null){
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.item_pop_fenlei_faxian, parent, false);
                viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_pop_fenlei_fabu);

                convertView.setTag(viewHolder);

            }else {
                viewHolder= (ViewHolder) convertView.getTag();
            }

            viewHolder.textView.setText(list.get(position));

            return convertView;

        }

        class ViewHolder{

            TextView textView;
        }

    }















}
