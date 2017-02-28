package com.tianyi.chulaibar.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianyi.chulaibar.R;
import com.tianyi.chulaibar.bean.ActivityListBean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/30 0030.
 */
public class FaXianListViewAdapter extends BaseAdapter {
    private Context mContext;
    private List mList;

    public FaXianListViewAdapter(){
    }

    public FaXianListViewAdapter(Context context, List mList){

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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lv_faxian, parent, false);

            viewHolder.iv_item_lv_faxian = (ImageView) convertView.findViewById(R.id.iv_item_lv_faxian);
            viewHolder.iv_address_lv_faxian = (ImageView) convertView.findViewById(R.id.iv_address_lv_faxian);
            viewHolder.iv_time_lv_faxian = (ImageView) convertView.findViewById(R.id.iv_time_lv_faxian);

            viewHolder.tv_title_item_lv_faxain = (TextView) convertView.findViewById(R.id.tv_title_item_lv_faxain);
            viewHolder.tv_address_lv_faxian = (TextView) convertView.findViewById(R.id.tv_address_lv_faxian);
            viewHolder.tv_time_lv_faxian = (TextView) convertView.findViewById(R.id.tv_time_lv_faxian);
            viewHolder.tv_look_faxain = (TextView) convertView.findViewById(R.id.tv_look_faxain);
            viewHolder.tv_lookNumber_faxain = (TextView) convertView.findViewById(R.id.tv_lookNumber_faxain);
            viewHolder.tv_baoming_faxain = (TextView) convertView.findViewById(R.id.tv_baoming_faxain);
            viewHolder.tv_baomingNumber_faxain = (TextView) convertView.findViewById(R.id.tv_baomingNumber_faxain);

            convertView.setTag(viewHolder);

        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }


        if (((ActivityListBean.ResultBean.ListBean)mList.get(position)).getActivityName().length()<=20){
            viewHolder.tv_title_item_lv_faxain.setText(((ActivityListBean.ResultBean.ListBean)mList
                    .get(position)).getActivityName());
        }else {
            viewHolder.tv_title_item_lv_faxain.setText(((ActivityListBean.ResultBean.ListBean)mList
                    .get(position)).getActivityName().substring(0,17)+"...");
        }

        viewHolder.tv_address_lv_faxian.setText(((ActivityListBean.ResultBean.ListBean)mList.get(position)).getProvinceName()+((ActivityListBean.ResultBean.ListBean)mList.get(position)).getCityName());
        viewHolder.tv_time_lv_faxian.setText(((ActivityListBean.ResultBean.ListBean)mList.get(position)).getStartDate()+"");
        viewHolder.tv_lookNumber_faxain.setText(((ActivityListBean.ResultBean.ListBean)mList.get(position)).getClick()+"");
        viewHolder.tv_baomingNumber_faxain.setText(((ActivityListBean.ResultBean.ListBean)mList.get(position)).getParticipation()+"");
        return convertView;
    }

    class ViewHolder{

        ImageView iv_item_lv_faxian,iv_address_lv_faxian,iv_time_lv_faxian;
        TextView tv_title_item_lv_faxain,tv_address_lv_faxian,tv_time_lv_faxian,tv_look_faxain,
                tv_lookNumber_faxain,tv_baoming_faxain,tv_baomingNumber_faxain;

    }

}
