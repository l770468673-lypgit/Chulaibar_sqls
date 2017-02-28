package com.tianyi.chulaibar.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianyi.chulaibar.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/22 0022.
 */

public class MarkCityAdapter extends BaseAdapter {
    private List<Map<String, String>> address_list;
    private Context context;
    public MarkCityAdapter(Context context,List<Map<String, String>> address_list) {
        this.address_list = address_list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return address_list.size();
    }

    @Override
    public Object getItem(int position) {
        return address_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (holder ==null){
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.address_item,null);
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.address = (TextView) convertView.findViewById(R.id.address);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(address_list.get(position).get("title"));
        holder.address.setText(address_list.get(position).get("address"));
        return convertView;
    }
    class ViewHolder{
        private ImageView icon;
        private TextView title,address;
    }
}
