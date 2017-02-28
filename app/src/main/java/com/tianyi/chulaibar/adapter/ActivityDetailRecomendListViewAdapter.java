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

import java.util.List;

/**
 * Created by Administrator on 2016/9/30 0030.
 */
public class ActivityDetailRecomendListViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<ActivityDetailBean.ResultBean.RecommendListBean> mList;

    public ActivityDetailRecomendListViewAdapter(){
    }

    public ActivityDetailRecomendListViewAdapter(Context context, List<ActivityDetailBean.ResultBean.RecommendListBean> recommendList){

         this.mContext= context;
         this.mList=recommendList;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lv_tuijian_activity_detail, parent, false);

            viewHolder.tv_title_item_lv_recommend_activity_detail = (TextView) convertView.findViewById(R.id.tv_title_item_lv_recommend_activity_detail);
            viewHolder.tv_nikename_item_lv_recommend_activity_detail = (TextView) convertView.findViewById(R.id.tv_nikename_item_lv_recommend_activity_detail);
            viewHolder.tv_address_item_lv_recommend_activity_detail = (TextView) convertView.findViewById(R.id.tv_address_item_lv_recommend_activity_detail);
            viewHolder.tv_time_item_lv_recommend_activity_detail = (TextView) convertView.findViewById(R.id.tv_time_item_lv_recommend_activity_detail);

            viewHolder.iv_item_lv_recommend_activity_detail = (ImageView) convertView.findViewById(R.id.iv_item_lv_recommend_activity_detail);
            viewHolder.iv_touxiang_item_lv_recommend_activity_detail = (ImageView) convertView.findViewById(R.id.iv_touxiang_item_lv_recommend_activity_detail);
            viewHolder.iv_address_lv_recommend_activity_detail = (ImageView) convertView.findViewById(R.id.iv_address_lv_recommend_activity_detail);
            viewHolder.iv_time_lv_recommend_activity_detail = (ImageView) convertView.findViewById(R.id.iv_time_lv_recommend_activity_detail);

            convertView.setTag(viewHolder);

        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        //设置文字内容
        if (mList.get(position).getActivityName().length()<=25){
            viewHolder.tv_title_item_lv_recommend_activity_detail.
                    setText( mList.get(position).getActivityName());
        }else {

            viewHolder.tv_title_item_lv_recommend_activity_detail.
                    setText( mList.get(position).getActivityName().substring(0,25)+"...");
        }



     //   viewHolder.tv_title_item_lv_recommend_activity_detail.setText( mList.get(position).getActivityName());

        viewHolder.tv_address_item_lv_recommend_activity_detail.
                setText( mList.get(position).getProvinceName()+mList.get(position).getCityName());
        viewHolder.tv_time_item_lv_recommend_activity_detail.setText( mList.get(position).getStartTime());

        //设置图片




        return convertView;
    }

    class ViewHolder{

        TextView tv_title_item_lv_recommend_activity_detail,tv_nikename_item_lv_recommend_activity_detail,
                tv_address_item_lv_recommend_activity_detail,tv_time_item_lv_recommend_activity_detail;

        ImageView iv_item_lv_recommend_activity_detail,iv_touxiang_item_lv_recommend_activity_detail,
                iv_address_lv_recommend_activity_detail,iv_time_lv_recommend_activity_detail;


    }

}
