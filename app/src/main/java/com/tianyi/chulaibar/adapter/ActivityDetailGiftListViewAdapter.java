package com.tianyi.chulaibar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianyi.chulaibar.R;
import com.tianyi.chulaibar.bean.ActivityDetailBean;
import com.tianyi.chulaibar.bean.ActivityListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/30 0030.
 */
public class ActivityDetailGiftListViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<ActivityDetailBean.ResultBean.GiftListBean> mList;

    public ActivityDetailGiftListViewAdapter(){
    }

    public ActivityDetailGiftListViewAdapter(Context context, List<ActivityDetailBean.ResultBean.GiftListBean> giftListBeanList){

         this.mContext= context;
         this.mList=giftListBeanList;
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

        ViewHolder viewHolder;

        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lv_gift_activity_detai, parent, false);

            viewHolder.tv_gift_name_item_lv_detail = (TextView) convertView.findViewById(R.id.tv_gift_name_item_lv_detail);
            viewHolder.tv_gift_numName_item_lv_activity_detail = (TextView) convertView.findViewById(R.id.tv_gift_numName_item_lv_activity_detail);
            viewHolder.tv_gift_num_item_lv_activity_detail = (TextView) convertView.findViewById(R.id.tv_gift_num_item_lv_activity_detail);
            viewHolder.tv_gift_description_item_lv_activity_detail = (TextView) convertView.findViewById(R.id.tv_gift_description_item_lv_activity_detail);

            convertView.setTag(viewHolder);

        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_gift_name_item_lv_detail.setText( mList.get(position).getGiftName());
        viewHolder.tv_gift_num_item_lv_activity_detail.setText( ""+mList.get(position).getGiftNumber());
        viewHolder.tv_gift_description_item_lv_activity_detail.setText( mList.get(position).getGiftRemark());


        return convertView;
    }

    class ViewHolder{

        TextView tv_gift_name_item_lv_detail,tv_gift_numName_item_lv_activity_detail,
                tv_gift_num_item_lv_activity_detail,tv_gift_description_item_lv_activity_detail;

    }

}
