package com.tianyi.chulaibar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tianyi.chulaibar.R;
import com.tianyi.chulaibar.bean.Bean_MoBanPic;
import com.tianyi.chulaibar.util.Constant;

import java.util.List;

/**
 * @
 * @类名称: ${}
 * @类描述: ${type_name}
 * @创建人： Lyp
 * @创建时间：${date} ${time}
 * @备注：
 */
public class Myadapter extends RecyclerView.Adapter<Myadapter.MyviewHolder> {

    public List<Bean_MoBanPic.AttachmentListBean> mMaplist;

    public LayoutInflater inflater;
    public  Context mContext;



    public interface OnRecylerClickListener {

          void itemclick(int position);
    }

    public OnRecylerClickListener mlistener;

    public void setOnRecylerClickListener(OnRecylerClickListener listener) {
        this.mlistener = listener;

    }

    public Myadapter(Context context, List<Bean_MoBanPic.AttachmentListBean> list) {

        this.mContext=context;
        this.mMaplist = list;
        inflater = LayoutInflater.from(context);

    }

    // 自定义viewholder

    class MyviewHolder extends RecyclerView.ViewHolder {
        private final ImageView imview;
        public MyviewHolder(View itemView) {
            super(itemView);
            imview = (ImageView) itemView.findViewById(R.id.iv_item);
        }
    }

    //   //创建ViewHolder的方法
    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.recycler_item, null);
        MyviewHolder holder = new MyviewHolder(view);
        return holder;
    }

    //绑定数据
    @Override
    public void onBindViewHolder(MyviewHolder holder, final int position) {

        String attachPath = mMaplist.get(position).getAttachPath();

        String imageurl  = Constant.URL.IMAGE_HEAD_URL + attachPath;
        Picasso.with(mContext).load(imageurl).into(holder.imview);
        //Picasso.with(mContext).load(newBean.getIcon()).into(viewHolder.jingpin_tuijian_iv);

        //// TODO: 2016/12/30   //picasso
      //  holder.imview.setImageBitmap();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlistener.itemclick(position);
            }
        });

    }

    // 返回条目
    @Override
    public int getItemCount() {
        return mMaplist.size();
    }



}
