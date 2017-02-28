package com.tianyi.chulaibar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianyi.chulaibar.R;
import com.tianyi.chulaibar.bean.ActivityListBean;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 2016/9/30 0030.
 */
public class ShouYeListViewAdapter extends BaseAdapter {
    private Context mContext;
    private  List<ActivityListBean.ResultBean.ListBean> mList;

    public ShouYeListViewAdapter(){


    }

    public ShouYeListViewAdapter(Context context,List mList){

         this.mContext= context;
         this.mList=mList;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lv_shouye, parent, false);

            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title_item_lv_shouye);
            viewHolder.tv_nikename = (TextView) convertView.findViewById(R.id.tv_nikename_item_lv_shouye);
            viewHolder.tv_city = (TextView) convertView.findViewById(R.id.tv_city);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.iv_hudong = (ImageView) convertView.findViewById(R.id.iv_item_lv_shouye);
            viewHolder.iv_touxiang = (ImageView) convertView.findViewById(R.id.iv_touxiang_item_lv_shouye);
            viewHolder.tv_divider = (ImageView) convertView.findViewById(R.id.iv_footer_divider);

            convertView.setTag(viewHolder);

        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        ActivityListBean.ResultBean.ListBean  listBean= mList.get(position);


        if (listBean.getActivityName().length()<=40){
            viewHolder.tv_title.setText(listBean.getActivityName());
        }else {

            viewHolder.tv_title.setText(listBean.getActivityName().substring(0,37)+"...");
        }

       // viewHolder.tv_title.setText(listBean.getActivityName());

        viewHolder.tv_city.setText(listBean.getCityName());
        viewHolder.tv_time.setText(listBean.getStartDate());
        viewHolder.tv_nikename.setText(listBean.getNickname());

        return convertView;
    }

    class ViewHolder{

        ImageView iv_hudong,iv_touxiang,tv_divider;
        TextView tv_title,tv_nikename,tv_city,tv_time;

    }

}
