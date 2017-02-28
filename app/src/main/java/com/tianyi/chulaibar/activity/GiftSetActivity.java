package com.tianyi.chulaibar.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tianyi.chulaibar.R;
import com.tianyi.chulaibar.base.BaseActivity;
import com.tianyi.chulaibar.bean.ActivityListBean;
import com.tianyi.chulaibar.bean.GiftBean;
import com.tianyi.chulaibar.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class GiftSetActivity extends BaseActivity {

    private ListView listView;
    private TextView tv_additem, tv_queding_activity_gift;
    private ArrayList<String> list;

    private LinearLayout ll_additem;
    private List<GiftBean> giftBeens;

    @Override
    protected int getContentResid() {
        return R.layout.activity_gift_set;
    }

    @Override
    protected void init() {

//        listView = (ListView) findViewById(R.id.lv_activity_gift);
//        //添加头部和底部视图
//        LayoutInflater inflater = LayoutInflater.from(GiftSetActivity.this);
//        View headview = inflater.inflate(R.layout.headview_lv_activity_gift_set, null);
//        View footerview = inflater.inflate(R.layout.footerview_lv_activity_gift_set, null);
//        tv_additem = (TextView) footerview.findViewById(R.id.tv_additem);
//        tv_additem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(GiftSetActivity.this, "添加了一条新的数据", Toast.LENGTH_SHORT).show();
//            }
//        });
//        tv_queding_activity_gift = (TextView) footerview.findViewById(R.id.tv_queding_activity_gift);
//        tv_queding_activity_gift.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(GiftSetActivity.this, "完成设置", Toast.LENGTH_SHORT).show();
//            }
//        });
//        list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//
//            list.add("小二狗子" + i);
//        }
//
//        MyAdapter adapter = new MyAdapter(this, list);
//
//        listView.addHeaderView(headview);
//        listView.addFooterView(footerview);
//        //设置适配器
//        listView.setAdapter(adapter);
//
//        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//               // listView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//              //  listView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
//            }
//        });

        //Scroll了View嵌套LinerLayout动态添加

        //可以添加列表的总布局
        ll_additem = (LinearLayout) findViewById(R.id.ll_additem);

        //添加费用项和完成按钮
        tv_additem = (TextView) findViewById(R.id.tv_additem);
        tv_additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GiftSetActivity.this, "添加了一条新的数据", Toast.LENGTH_SHORT).show();
                //添加新一栏费用项的方法
                addColums();

            }

        });
        giftBeens = new ArrayList<>();
        //完成设置的确定按钮
        tv_queding_activity_gift = (TextView) findViewById(R.id.tv_queding_activity_gift);
        tv_queding_activity_gift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GiftSetActivity.this, "完成设置", Toast.LENGTH_SHORT).show();

                for (int i = 0; i < ll_additem.getChildCount(); i++) {

                    View view = ll_additem.getChildAt(i);
                    EditText et_name_activity_gift_set = (EditText) view.findViewById(R.id.et_name_activity_gift_set);
                    EditText et_moneny_activity_gift_set = (EditText) view.findViewById(R.id.et_moneny_activity_gift_set);
                    EditText et_shuoming_activity_gift_set = (EditText) view.findViewById(R.id.et_shuoming_activity_gift_set);

                    String giftName = et_name_activity_gift_set.getText().toString().trim();
                    String giftNumber = et_moneny_activity_gift_set.getText().toString().trim();
                    String giftShuoMing = et_shuoming_activity_gift_set.getText().toString().trim();

                    giftBeens.add(new GiftBean(giftName,giftNumber,giftShuoMing));

                }

                //可删除//////////////////////////////////////////////
                String str = "";
                for(int i=0; i<giftBeens.size();i++){

                    str += giftBeens.get(i).toString()+"\n";

                }

                Toast.makeText(GiftSetActivity.this,str,Toast.LENGTH_SHORT).show();
                /////////////////////end/////////////////////////////////
                giftBeens.clear();
            }
        });

        addColums();
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void addColums() {
        //添加item的总布局(线性布局)
//        LinearLayout.LayoutParams params_01 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//        LinearLayout linearLayout_01 = new LinearLayout(this);
//        linearLayout_01.setOrientation(LinearLayout.VERTICAL);
//        linearLayout_01.setLayoutParams(params_01);
//        linearLayout_01.setBackgroundColor(Color.parseColor("#f2f2f2"));
//
//        //第二层添加（相对布局）
//        RelativeLayout.LayoutParams params_02 = new RelativeLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,   ViewGroup.LayoutParams.WRAP_CONTENT);
//        RelativeLayout relativeLayout_02 = new RelativeLayout(this);
//        int relativeLayout_02_id=View.generateViewId();
//        relativeLayout_02.setId(relativeLayout_02_id);
//        relativeLayout_02.setLayoutParams(params_02);
//
//       //第三层添加
//        ImageView imageView = new ImageView(this);//阴影部分
//        LinearLayout.LayoutParams params_03 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                DensityUtil.dip2px(this,20));
//        imageView.setBackgroundColor(Color.parseColor("#f2f2f2"));
//        imageView.setLayoutParams(params_03);
//        //把阴影部分加到父相对布局中
//        relativeLayout_02.addView(imageView);
//
//        //3个RelativeLayout的父布局LinearLayout
//        LinearLayout.LayoutParams params_04 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//        LinearLayout linearLayout_04 = new LinearLayout(this);
//        linearLayout_04.setOrientation(LinearLayout.VERTICAL);
//        linearLayout_04.setLayoutParams(params_04);
//        linearLayout_04.setBackgroundColor(Color.parseColor("#f2f2f2"));
//
//
//        //第一次层的相对布局
//        RelativeLayout.LayoutParams params_05 = new RelativeLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,DensityUtil.dip2px(this,50));
//        RelativeLayout relativeLayout_05 = new RelativeLayout(this);
//        relativeLayout_05.setLayoutParams(params_05);
//        relativeLayout_05.setBackgroundColor(Color.parseColor("#ffffff"));
//        relativeLayout_05.setPadding(DensityUtil.dip2px(this,16),0,DensityUtil.dip2px(this,16),0);
//
//        TextView textView_05 = new TextView(this);
//        RelativeLayout.LayoutParams textView_05_params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
//                DensityUtil.dip2px(this,20));
//        textView_05.setText("礼物名称");
//        textView_05.setTextSize(12);
//        textView_05.setTextColor(Color.parseColor("#7d7d8e"));
//        textView_05.setGravity(Gravity.CENTER_VERTICAL);
//        textView_05.setLayoutParams(textView_05_params);
//        int textView_05_id=View.generateViewId();
//        textView_05.setId(textView_05_id);
//
//
//        EditText editText_05 = new EditText(this);
//        RelativeLayout.LayoutParams editText_05_params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
//                DensityUtil.dip2px(this,20));
//        editText_05_params.setMargins(DensityUtil.dip2px(this,30),0,0,0);
//        editText_05_params.addRule(RelativeLayout.RIGHT_OF,textView_05_id);
//        editText_05.setTextSize(12);
//        editText_05.setTextColor(Color.parseColor("#262728"));
//        editText_05.setGravity(Gravity.CENTER_VERTICAL);
//        editText_05.setLayoutParams(editText_05_params);
//        editText_05.setSingleLine();
//        //把textview和eidttex添加到相对布局
//        relativeLayout_05.addView(textView_05);
//        relativeLayout_05.addView(editText_05);
//       //添加第一个相对布局
//        linearLayout_04.addView(relativeLayout_05);
//
//      //下划线第一条
//        ImageView line_01=new ImageView(this);
//        LinearLayout.LayoutParams line_01_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                DensityUtil.dip2px(this,1));
//        line_01.setBackgroundColor(Color.parseColor("#cccccc"));
//        line_01.setLayoutParams(line_01_params);
//     //添加第一条下划线
//        linearLayout_04.addView(line_01);
//
//
//        //第二次层的相对布局
//        RelativeLayout.LayoutParams params_06 = new RelativeLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,DensityUtil.dip2px(this,50));
//        RelativeLayout relativeLayout_06 = new RelativeLayout(this);
//        relativeLayout_06.setLayoutParams(params_06);
//        relativeLayout_06.setBackgroundColor(Color.parseColor("#ffffff"));
//        relativeLayout_06.setPadding(DensityUtil.dip2px(this,16),0,DensityUtil.dip2px(this,16),0);
//
//        TextView textView_06_01 = new TextView(this);
//        RelativeLayout.LayoutParams textView_06_01_params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
//                DensityUtil.dip2px(this,20));
//        textView_06_01.setText("金额");
//        textView_06_01.setTextSize(12);
//        textView_06_01.setTextColor(Color.parseColor("#7d7d8e"));
//        textView_06_01.setGravity(Gravity.CENTER_VERTICAL);
//        textView_06_01.setLayoutParams(textView_06_01_params);
//        int textView_06_01_id=View.generateViewId();
//        textView_06_01.setId(textView_06_01_id);
//        textView_06_01.setVisibility(View.INVISIBLE);
//
//        TextView textView_06_02 = new TextView(this);
//        RelativeLayout.LayoutParams textView_06_02_params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
//                DensityUtil.dip2px(this,20));
//        textView_06_02.setText("金额");
//        textView_06_02.setTextSize(12);
//        textView_06_02.setTextColor(Color.parseColor("#7d7d8e"));
//        textView_06_02.setGravity(Gravity.CENTER_VERTICAL);
//        int textView_06_02_id=View.generateViewId();
//        textView_06_02.setId(textView_06_02_id);
//        textView_06_02_params.addRule(RelativeLayout.RIGHT_OF,textView_06_01_id);
//        textView_06_02.setLayoutParams(textView_06_02_params);
//
//
//        EditText editText_06 = new EditText(this);
//        RelativeLayout.LayoutParams editText_06_params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
//                DensityUtil.dip2px(this,20));
//        editText_06_params.setMargins(DensityUtil.dip2px(this,30),0,0,0);
//        editText_06_params.addRule(RelativeLayout.RIGHT_OF,textView_06_02_id);
//        editText_06.setTextSize(12);
//        editText_06.setTextColor(Color.parseColor("#262728"));
//        editText_06.setGravity(Gravity.CENTER_VERTICAL);
//        editText_06.setLayoutParams(editText_06_params);
//        editText_06.setSingleLine();
//        //把textview和eidttex添加到相对布局
//        relativeLayout_06.addView(textView_06_01);
//        relativeLayout_06.addView(textView_06_02);
//        relativeLayout_06.addView(editText_06);
//        //添加第二个相对布局
//        linearLayout_04.addView(relativeLayout_06);
//
//        //下划线第二条
//        ImageView line_02=new ImageView(this);
//        LinearLayout.LayoutParams line_02_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                DensityUtil.dip2px(this,1));
//        line_02.setBackgroundColor(Color.parseColor("#cccccc"));
//        line_02.setLayoutParams(line_02_params);
//        //添加第二条下划线
//        linearLayout_04.addView(line_02);
//
//        //第三次层的相对布局
//        RelativeLayout.LayoutParams params_07 = new RelativeLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,DensityUtil.dip2px(this,50));
//        RelativeLayout relativeLayout_07 = new RelativeLayout(this);
//        relativeLayout_07.setLayoutParams(params_07);
//        relativeLayout_07.setBackgroundColor(Color.parseColor("#ffffff"));
//        relativeLayout_07.setPadding(DensityUtil.dip2px(this,16),0,DensityUtil.dip2px(this,16),0);
//
//        TextView textView_07_01 = new TextView(this);
//        RelativeLayout.LayoutParams textView_07_01_params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
//                DensityUtil.dip2px(this,20));
//        textView_07_01.setText("说明");
//        textView_07_01.setTextSize(12);
//        textView_07_01.setTextColor(Color.parseColor("#7d7d8e"));
//        textView_07_01.setGravity(Gravity.CENTER_VERTICAL);
//        textView_07_01.setLayoutParams(textView_07_01_params);
//        int textView_07_01_id=View.generateViewId();
//        textView_07_01.setId(textView_07_01_id);
//        textView_07_01.setVisibility(View.INVISIBLE);
//
//        TextView textView_07_02 = new TextView(this);
//        RelativeLayout.LayoutParams textView_07_02_params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
//                DensityUtil.dip2px(this,20));
//        textView_07_02.setText("说明");
//        textView_07_02.setTextSize(12);
//        textView_07_02.setTextColor(Color.parseColor("#7d7d8e"));
//        textView_07_02.setGravity(Gravity.CENTER_VERTICAL);
//        int textView_07_02_id=View.generateViewId();
//        textView_07_02.setId(textView_07_02_id);
//        textView_07_02_params.addRule(RelativeLayout.RIGHT_OF,textView_07_01_id);
//        textView_07_02.setLayoutParams(textView_06_02_params);
//
//
//        EditText editText_07 = new EditText(this);
//        RelativeLayout.LayoutParams editText_07_params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
//                DensityUtil.dip2px(this,20));
//        editText_07_params.setMargins(DensityUtil.dip2px(this,30),0,0,0);
//        editText_07_params.addRule(RelativeLayout.RIGHT_OF,textView_07_02_id);
//        editText_07.setTextSize(12);
//        editText_07.setTextColor(Color.parseColor("#262728"));
//        editText_07.setGravity(Gravity.CENTER_VERTICAL);
//        editText_07.setLayoutParams(editText_07_params);
//        editText_07.setSingleLine();
//        //把textview和eidttex添加到相对布局
//        relativeLayout_07.addView(textView_07_01);
//        relativeLayout_07.addView(textView_07_02);
//        relativeLayout_07.addView(editText_07);
//        //添加第二个相对布局
//        linearLayout_04.addView(relativeLayout_07);
//        //把线性布局添加到父相对布局中
//        relativeLayout_02.addView(linearLayout_04);
//
//        //添加删除按钮
//        ImageView imageView_delete= new ImageView(this);
//        RelativeLayout.LayoutParams imageView_delete_params = new RelativeLayout.LayoutParams( DensityUtil.dip2px(this,25),
//                DensityUtil.dip2px(this,25));
//        imageView_delete_params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,relativeLayout_02_id);//删除按钮添加到父布局的右边
//        imageView_delete_params.rightMargin=DensityUtil.dip2px(this,16);
//        imageView_delete_params.topMargin=DensityUtil.dip2px(this,10);
//        imageView_delete.setLayoutParams(imageView_delete_params);
//        imageView_delete.setBackgroundResource(R.mipmap.delete_round);
//        imageView_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(GiftSetActivity.this, "删除一条新的数据", Toast.LENGTH_SHORT).show();
//            }
//        });
//       //把删除按钮添加到父相对布局
//        relativeLayout_02.addView(imageView_delete);
//
//        //把第二层相对布局添加到父布局中
//        linearLayout_01.addView(relativeLayout_02);
        //添加到负责添加布局的线性布局中


        final View view = LayoutInflater.from(this).inflate(R.layout.item_lv_activity_gift_set, null);

        ImageView iv_delete_activity_gift = (ImageView) view.findViewById(R.id.iv_delete_activity_gift);

        iv_delete_activity_gift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(GiftSetActivity.this, "点击删除了！", Toast.LENGTH_SHORT).show();

                ll_additem.removeView(view);
            }
        });

        ll_additem.addView(view);

    }

//        class MyAdapter extends BaseAdapter {
//
//            Context mContext;
//            ArrayList<String> mList;
//            View view;
//
//            public MyAdapter(Context context, ArrayList<String> list) {
//
//                this.mContext = context;
//                this.mList = list;
//            }
//
//            @Override
//            public int getCount() {
//                return mList.size();
//            }
//
//            @Override
//            public Object getItem(int position) {
//                return mList.get(position);
//            }
//
//            @Override
//            public long getItemId(int position) {
//                return position;
//            }
//
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                View view = LayoutInflater.from(mContext).inflate(R.layout.item_lv_activity_gift_set, parent, false);
//                ViewHolder viewHolder = new ViewHolder();
//                EditText et_name_activity_gift_set = (EditText) view.findViewById(R.id.et_name_activity_gift_set);
//                EditText et_moneny_activity_gift_set = (EditText) view.findViewById(R.id.et_moneny_activity_gift_set);
//                final EditText et_shuoming_activity_gift_set = (EditText) view.findViewById(R.id.et_shuoming_activity_gift_set);
//                et_shuoming_activity_gift_set.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//                        et_shuoming_activity_gift_set.setText(s);
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//
//                    }
//                });
//
//                ImageView iv_delete_activity_gift = (ImageView) view.findViewById(R.id.iv_delete_activity_gift);
//                    iv_delete_activity_gift.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Toast.makeText(GiftSetActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//
//
//                return view;
//
//            }
//        }

//    class ViewHolder {
//
//            EditText et_name_activity_gift_set, et_moneny_activity_gift_set, et_shuoming_activity_gift_set;
//            ImageView iv_delete_activity_gift;
//
//        }


}
