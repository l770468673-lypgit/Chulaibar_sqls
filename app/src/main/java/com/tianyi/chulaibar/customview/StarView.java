package com.tianyi.chulaibar.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tianyi.chulaibar.R;

/**
 * Created by Administrator on 2016/11/16 0016.
 */
public class StarView extends LinearLayout implements View.OnTouchListener,GestureDetector.OnGestureListener{

    private int mImageWidth = 20;  //图片设置默认的宽度
    private int mImageHeight = 20; //图片设置默认的高度
    private int mLinerLayoutWidth=60;//图片父布局默认的宽度
    private int mLinerLayoutHeight=60;//图片父布局默认的宽度
    private int mDefaultImageId = R.mipmap.star_normal;
    private int mClickImageId =  R.mipmap.star_selected;
    private int mMargin = 5;   //图片之间默认的margin
    private int mStarNum = 5;  //星星默认的个数
    private int mStarChoose = 3;  //默认默认是三颗星
    private boolean isClick = true;
    private String tag;
    private OnStarItemClickListener mStarItemClickListener;
    private GestureDetector mDetector;
    private int horizontalDistance;

    public StarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context, attrs);
        initGestureDetector();
    }
    public StarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context, attrs);
        initGestureDetector();
    }

    private void initGestureDetector() {
        mDetector = new GestureDetector(this);
    }
    /**
     * 初始化数据
     *
     * @param context
     * @param attrs
     */
    private void initData(Context context, AttributeSet attrs) {

        int starWidth=this.getWidth();
        horizontalDistance=starWidth/5;

        this.setOrientation(HORIZONTAL);  //设置水平
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.StarView, 0, 0);
        int n = a.getIndexCount();

        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.StarView_mImageWidth:
                    mImageWidth = (int) a.getDimension(attr, mImageWidth);
                    break;
                case R.styleable.StarView_mImageHeight:
                    mImageHeight = (int) a.getDimension(attr, mImageHeight);
                    break;
                case R.styleable.StarView_mDefaultImageId:
                    mDefaultImageId = a.getResourceId(attr, mDefaultImageId);
                    break;
                case R.styleable.StarView_mClickImageId:
                    mClickImageId = a.getResourceId(attr, mClickImageId);
                    break;
                case R.styleable.StarView_mMargin:
                    mMargin = (int) a.getDimension(attr, mMargin);
                    break;
                case R.styleable.StarView_mStarNum:
                    mStarNum = a.getInt(attr, mStarNum);
                    break;
                case R.styleable.StarView_mStarChoose:
                    mStarChoose = a.getInt(attr, mStarChoose);
                    break;
            }
        }
        a.recycle();

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setStarNum(mStarNum);  //设置个数
    }

    /**
     * 设置星星数量
     *
     * @param number
     */
    public void setStarNum(int number) {
        if (number <= 0) {
            try {
                throw new Exception("设置的数据不能小于等于零");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.removeAllViews(); //清空所有view
        for (int i = 0; i < number; i++) {
            //设置imageview 的父布局
            LinearLayout linearLayout = new LinearLayout(getContext());
            final  LayoutParams layoutParams_parent = new LayoutParams(mLinerLayoutWidth, mLinerLayoutHeight);
            layoutParams_parent.leftMargin=mMargin;
            layoutParams_parent.rightMargin=mMargin;
            linearLayout.setLayoutParams(layoutParams_parent);
            linearLayout.setBackgroundColor(Color.TRANSPARENT);
            linearLayout.setGravity(Gravity.CENTER);
            //设置Imageview
            ImageView imageView = new ImageView(getContext());
            final LayoutParams layoutParams = new LayoutParams(mImageWidth, mImageHeight);
//            layoutParams.leftMargin = mMargin;
//            layoutParams.rightMargin = mMargin;
            imageView.setLayoutParams(layoutParams);

            linearLayout.addView(imageView);
            this.addView(linearLayout);

            imageView.setImageResource(mDefaultImageId);
            setStarOnClick(linearLayout, i);


//            ImageView imageView = new ImageView(getContext());
//            final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mImageWidth, mImageHeight);
//            layoutParams.leftMargin = mMargin;
//            layoutParams.rightMargin = mMargin;
//            imageView.setLayoutParams(layoutParams);
//            this.addView(imageView);
//            imageView.setImageResource(mDefaultImageId);
//            setStarOnClick(imageView, i);
        }
        setCurrentChoose(mStarChoose);  //设置当前选择
    }


    /**
     * 设置点击事件
     *
     * @param linearLayout
     * @param i
     */

    private void setStarOnClick(final LinearLayout linearLayout, final int i) {
        if (linearLayout != null) {
            linearLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    resetDefaultImage();
                    setCurrentChoose(i+1);
                    if (mStarItemClickListener != null) {
                        //  mStarItemClickListener.onItemClick(imageView, i);
                        mStarItemClickListener.onItemClick(linearLayout, i,tag);
                    }
                }
            });
        }
    }



//    private void setStarOnClick(final ImageView imageView, final int i) {
//        if (imageView != null) {
//            imageView.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    resetDefaultImage();
//                    setCurrentChoose(i+1);
//                    if (mStarItemClickListener != null) {
//                      //  mStarItemClickListener.onItemClick(imageView, i);
//                        mStarItemClickListener.onItemClick(imageView, i,tag);
//                    }
//                }
//            });
//        }
//    }

    /**
     * 设置当前选择
     *
     * @param index
     */
    private void setCurrentChoose(int index) {
        if(isClick){
            for (int i = 0; i < index; i++) {

//                ImageView imageView = (ImageView) getChildAt(i);
//                imageView.setImageResource(mClickImageId);

                LinearLayout linearLayout = (LinearLayout) getChildAt(i);
               ImageView imageView= (ImageView) linearLayout.getChildAt(0);
                imageView.setImageResource(mClickImageId);

            }
        }
    }

    /**
     * 重置默认为默认的图片
     */
    private void resetDefaultImage() {
        int cNum = getChildCount();
        for (int i = 0; i < cNum; i++) {

//            ImageView imageView = (ImageView) getChildAt(i);
//            imageView.setImageResource(mDefaultImageId);

            LinearLayout linearLayout = (LinearLayout) getChildAt(i);
            ImageView imageView= (ImageView) linearLayout.getChildAt(0);
            imageView.setImageResource(mDefaultImageId);

        }
    }

    public int getImageWidth() {
        return mImageWidth;
    }

    public void setImageWidth(int mImageWidht) {
        this.mImageWidth = mImageWidht;
    }

    public int getImageHeight() {
        return mImageHeight;
    }

    public void setImageHeight(int mImageHeight) {
        this.mImageHeight = mImageHeight;
    }

    public int getDefaultImageId() {
        return mDefaultImageId;
    }

    public void setDefaultImageId(int resouceId) {
        this.mDefaultImageId = mDefaultImageId;
    }

    public int getClickImageId() {
        return mClickImageId;
    }

    public void setClickImageId(int mClickImageId) {
        this.mClickImageId = mClickImageId;
    }

    public OnStarItemClickListener getStarItemClickListener() {
        return mStarItemClickListener;
    }

    public void setmStarItemClickListener(OnStarItemClickListener mStarItemClickListener) {
        this.mStarItemClickListener = mStarItemClickListener;
    }

    public void setmStarItemClickListener(OnStarItemClickListener mStarItemClickListener,String tag) {
        this.mStarItemClickListener = mStarItemClickListener;
        this.tag=tag;
    }


    /**
     * 手势识别的监听事件
     */

    @Override
    public boolean onDown(MotionEvent e) {


        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        Log.d("pingan", "onScroll" + distanceX + "distanceY:" + distanceY);

//        float startX = e1.getX();
//        float startY = e1.getY();
//        float endX = e2.getX();
//        float endY = e1.getY();
        int index;

        if (distanceX < -horizontalDistance) {
            Log.d("pingan", "向右手势");
            if(distanceX%horizontalDistance==0){
                index= (int) (distanceX/horizontalDistance);

            }else {
                index= (int) (distanceX/horizontalDistance)+1;
            }

            setCurrentChoose(index);

        } else if (distanceX > horizontalDistance){

            Log.d("pingan", "向左手势");


        }else if (distanceY <- horizontalDistance) {
            Log.d("pingan", "向下手势");


        } else if (distanceY > horizontalDistance ) {

            Log.d("pingan", "向上手势");


        }

        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {


        float startX = e1.getX();
        float startY = e1.getY();
        float endX = e2.getX();
        float endY = e1.getY();

        float distanceX=startX-endX;
        float distanceY=startY-endY;

        int index;

        if (distanceX < -horizontalDistance) { //向右滑动
            Log.d("pingan", "向右手势");
            if(Math.abs(distanceX)%horizontalDistance==0){
                index= (int) (Math.abs(distanceX)/horizontalDistance);

            }else {
                index= (int) (Math.abs(distanceX)/horizontalDistance)+1;
            }

            setCurrentChoose(index);

        } else if (distanceX > horizontalDistance){

            Log.d("pingan", "向左手势");


        }else if (distanceY <- horizontalDistance) {
            Log.d("pingan", "向下手势");


        } else if (distanceY > horizontalDistance ) {

            Log.d("pingan", "向上手势");


        }
        return true;
    }
    /**
     * 屏幕的touch事件
     */

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        this.mDetector.onTouchEvent(event);

        return true;
    }
    /**
     * 星星点击事件
     */
    public interface OnStarItemClickListener {
       //  void onItemClick(View view,  int pos);

         void onItemClick(View view, int pos, String tag);
    }


}
