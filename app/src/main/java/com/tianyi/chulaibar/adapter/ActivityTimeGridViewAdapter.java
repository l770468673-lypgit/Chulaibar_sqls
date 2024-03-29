package com.tianyi.chulaibar.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tianyi.chulaibar.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2016/10/21 0021.
 */
public class ActivityTimeGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private  String[] mData;
    public int now=0;//当前选中的pos
    private String time="全部";
    //指定操作的文件名称
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;
    public ActivityTimeGridViewAdapter(Context context, String[] data){

        mContext = context;
        mData = data;

        //获取sp，存储cityId
        sp=mContext.getSharedPreferences("gridview",MODE_PRIVATE);
        edit = sp.edit(); //编辑文件

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

                            //保存当前点击的time
                            time=mData[now];
                            edit.putString("time",time);   //根据键值对添加数据
                            edit.commit();	//保存数据信息

                        }
                    }
                });

        return convertView;

    }

   class ViewHolder{

         TextView textView;

   }

}
