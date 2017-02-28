package com.tianyi.chulaibar.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tianyi.chulaibar.R;
import com.tianyi.chulaibar.bean.ActivityTypeListBean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/21 0021.
 */
public class GridViewAdapter extends BaseAdapter {

    private Context mContext;
    private  String[] mData;
    private int mType;
    public int now=0;//当前选中的pos


    public  GridViewAdapter(Context context,String[] data,int type){

        mContext = context;
        mData = data;
        mType= type;

    }


    @Override
    public int getCount() {
        return mData.length;
    }

    @Override
    public Object getItem(int position) {
        return mData[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        final ViewHolder viewHolder;

        switch (mType){

            case 0:
                if(convertView==null){
                    viewHolder = new ViewHolder();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gv_faxian,null);
                    viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_gv_faxian);
                    convertView.setTag(viewHolder);
                }else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }

                viewHolder.textView.setTag("city"+position);
                viewHolder.textView.setText(mData[position]);
              //  viewHolder.textView.setBackground(new ColorDrawable(Color.TRANSPARENT));
                viewHolder.textView.setTextColor(Color.parseColor("#999999"));
                viewHolder.textView.setPadding(4,2,4,2);

                if(position==0){           //设置第一个的默认背景和文字颜色
                      viewHolder.textView.setBackgroundResource(R.drawable.selector_item_gridview_faxian);
                    viewHolder.textView.setTextColor(Color.parseColor("#ffffff"));
                }



                viewHolder.textView.setOnClickListener(new View.OnClickListener() {

                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if(now!=position)
                        {
                            TextView tv=(TextView)parent.findViewWithTag("city"+now);

                            if(tv!=null) {
                                //设置没有选中的背景颜色为透明
                                tv.setBackground(new ColorDrawable(Color.TRANSPARENT));
                                tv.setTextColor(Color.parseColor("#999999"));
                            }
                            viewHolder.textView.setBackgroundResource(R.drawable.selector_item_gridview_faxian);
                            viewHolder.textView.setTextColor(Color.parseColor("#ffffff"));
                            now=position;

                        }
                    }
                });



                break;

            case 1:
                if(convertView==null){
                    viewHolder = new ViewHolder();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gv_faxian,null);
                    viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_gv_faxian);
                    convertView.setTag(viewHolder);
                }else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }

                viewHolder.textView.setTag("time"+position);
                viewHolder.textView.setText(mData[position]);
              //  viewHolder.textView.setBackground(new ColorDrawable(Color.TRANSPARENT));
                viewHolder.textView.setTextColor(Color.parseColor("#999999"));
                viewHolder.textView.setPadding(4,2,4,2);
                if(position==0){           //设置第一个的默认背景和文字颜色
                    viewHolder.textView.setBackgroundResource(R.drawable.selector_item_gridview_faxian);
                    viewHolder.textView.setTextColor(Color.parseColor("#ffffff"));
                }


                viewHolder.textView.setOnClickListener(new View.OnClickListener() {

                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if(now!=position) {
                            TextView tv=(TextView)parent.findViewWithTag("time"+now);

                            if(tv!=null) {
                                //设置没有选中的背景颜色为透明
                                tv.setBackground(new ColorDrawable(Color.TRANSPARENT));
                                tv.setTextColor(Color.parseColor("#999999"));
                            }
                            viewHolder.textView.setBackgroundResource(R.drawable.selector_item_gridview_faxian);
                            viewHolder.textView.setTextColor(Color.parseColor("#ffffff"));
                            now=position;

                        }
                    }
                });



                break;

            case 2:
                if(convertView==null){
                    viewHolder = new ViewHolder();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gv_faxian,null);
                    viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_gv_faxian);
                    convertView.setTag(viewHolder);
                }else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }


                viewHolder.textView.setTag("fenlei"+position);
                viewHolder.textView.setText(mData[position]);
               // viewHolder.textView.setBackground(new ColorDrawable(Color.TRANSPARENT));
                viewHolder.textView.setTextColor(Color.parseColor("#999999"));

                viewHolder.textView.setPadding(4,2,4,2);
                if(position==0){           //设置第一个的默认背景和文字颜色
                    viewHolder.textView.setBackgroundResource(R.drawable.selector_item_gridview_faxian);
                    viewHolder.textView.setTextColor(Color.parseColor("#ffffff"));
                }



                viewHolder.textView.setOnClickListener(new View.OnClickListener() {

                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if(now!=position)
                        {
                            TextView tv=(TextView)parent.findViewWithTag("fenlei"+now);

                            if(tv!=null) {
                                //设置没有选中的背景颜色为透明
                                tv.setBackground(new ColorDrawable(Color.TRANSPARENT));
                                tv.setTextColor(Color.parseColor("#999999"));
                            }
                            viewHolder.textView.setBackgroundResource(R.drawable.selector_item_gridview_faxian);
                            viewHolder.textView.setTextColor(Color.parseColor("#ffffff"));
                            now=position;

                        }
                    }
                });
                break;

            case 3:
                if(convertView==null){
                    viewHolder = new ViewHolder();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gv_faxian,null);
                    viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_gv_faxian);
                    convertView.setTag(viewHolder);
                }else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }

                viewHolder.textView.setTag("xingshi"+position);
                viewHolder.textView.setText(mData[position]);
              //  viewHolder.textView.setBackground(new ColorDrawable(Color.TRANSPARENT));
                viewHolder.textView.setTextColor(Color.parseColor("#999999"));
                viewHolder.textView.setPadding(4,2,4,2);
                if(position==0){           //设置第一个的默认背景和文字颜色
                    viewHolder.textView.setBackgroundResource(R.drawable.selector_item_gridview_faxian);
                    viewHolder.textView.setTextColor(Color.parseColor("#ffffff"));
                }


                viewHolder.textView.setOnClickListener(new View.OnClickListener() {

                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if(now!=position)
                        {
                            TextView tv=(TextView)parent.findViewWithTag("xingshi"+now);

                            if(tv!=null) {
                                //设置没有选中的背景颜色为透明
                                tv.setBackground(new ColorDrawable(Color.TRANSPARENT));
                                tv.setTextColor(Color.parseColor("#999999"));
                            }
                            viewHolder.textView.setBackgroundResource(R.drawable.selector_item_gridview_faxian);
                            viewHolder.textView.setTextColor(Color.parseColor("#ffffff"));
                            now=position;

                        }
                    }
                });

                break;
        }

        return convertView;

    }

   class ViewHolder{

         TextView textView;

   }


}
