package com.tianyi.chulaibar.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Administrator on 2016/10/8 0008.
 */
public class ShouYeViewPagerAdapter extends PagerAdapter {

    private List<ImageView> mList;
    private Context mContext;

    public ShouYeViewPagerAdapter(Context context,List<ImageView> pics_list){

        this.mContext=context;
        this.mList=pics_list;

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        if (mList.get(position % mList.size()).getParent() != null) {
            ((ViewPager) mList.get(position % mList.size())
                    .getParent()).removeView(mList.get(position
                    % mList.size()));
        }
        container.addView(
                mList.get(position % mList.size()), 0);

        return mList.get(position % mList.size());


    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView( mList.get(position % mList.size()));

    }


}
