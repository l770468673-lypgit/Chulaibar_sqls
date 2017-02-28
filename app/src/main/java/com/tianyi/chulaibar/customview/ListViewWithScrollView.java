package com.tianyi.chulaibar.customview;

import android.content.Context;
import android.widget.ListView;

/**
 * Created by Administrator on 2016/12/28 0028.
 */

public class ListViewWithScrollView extends ListView {
    public ListViewWithScrollView(Context context) {
        super(context);
    }


/**   只重写该方法，达到使ListView适应ScrollView的效果   */

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,

                MeasureSpec.AT_MOST);

    }

}

