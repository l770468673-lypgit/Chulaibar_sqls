package com.tianyi.chulaibar.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.tianyi.chulaibar.R;
import com.tianyi.chulaibar.adapter.Myadapter;
import com.tianyi.chulaibar.bean.Bean_MoBanPic;
import com.tianyi.chulaibar.fragment.FaBuFragment;
import com.tianyi.chulaibar.util.Constant;
import com.tianyi.chulaibar.util.OkHttpUtil;

import org.json.JSONException;

import java.util.List;

public class GetPosterFromModelActivity extends Activity implements View.OnClickListener {

    public RecyclerView haibaorecycle;
    private String mImageurl;
    private ImageView iv_bigPicture;

    private String mAttachPath;
    //    private HorizontalScrollView horizontalScrollView;
    private LinearLayout ll_pictures;
    //  private List<ImageView> list_pictures;
    private int[] pictures = {R.mipmap.holder, R.mipmap.holder, R.mipmap.holder, R.mipmap.holder,
            R.mipmap.holder, R.mipmap.holder, R.mipmap.holder, R.mipmap.holder};
    private TextView tv_choosePoster;
    private List<String> mMmaplist;
    private List<Bean_MoBanPic.AttachmentListBean> mMMaplist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_poster_from_model);
        init();

        loadDatas();


    }

    public void init() {
        iv_bigPicture = (ImageView) findViewById(R.id.iv_bigPicture);
        haibaorecycle = (RecyclerView) findViewById(R.id.haibaorecycle);
        // ll_pictures = (LinearLayout) findViewById(R.id.ll_pictures);

        tv_choosePoster = (TextView) findViewById(R.id.tv_choosePoster);
        tv_choosePoster.setOnClickListener(this);


        LinearLayoutManager message = new LinearLayoutManager(this);
        message.setOrientation(LinearLayoutManager.HORIZONTAL);
        haibaorecycle.setLayoutManager(message);


    }

    public List<Bean_MoBanPic.AttachmentListBean> getMaplist(String json) {

        Bean_MoBanPic bean_moBanPic = new Gson().fromJson(json, Bean_MoBanPic.class);
        List<Bean_MoBanPic.AttachmentListBean> attachmentList = bean_moBanPic.getAttachmentList();

        return attachmentList;
    }


    public void loadDatas() {
        // Constant.URL.HUOQUMOBANTUPIAN
        OkHttpUtil.downJSON(Constant.URL.HUOQUMOBANTUPIAN, new OkHttpUtil.OnDownDataListener() {


            @Override
            public void onResponse(String url, String json) throws JSONException {

                // huoqu  的 所末班 的数据
                mMMaplist = getMaplist(json);
                // mMmaplisturl = new ArrayList<>();
                for (int i = 0; i < mMMaplist.size(); i++) {
                    Bean_MoBanPic.AttachmentListBean attachmentListBean = mMMaplist.get(i);
                    //    String attachPath = attachmentListBean.getAttachPath();
                    //     mMmaplist.add(attachPath);
                }

                final Myadapter adapter = new Myadapter(GetPosterFromModelActivity.this, mMMaplist);
                haibaorecycle.setAdapter(adapter);
                adapter.setOnRecylerClickListener(new Myadapter.OnRecylerClickListener() {


                    @Override
                    public void itemclick(int position) {
                        Bean_MoBanPic.AttachmentListBean attachmentListBean = mMMaplist.get(position);
                        mAttachPath = attachmentListBean.getAttachPath();

                        mImageurl = Constant.URL.IMAGE_HEAD_URL + mAttachPath;
                        Picasso.with(GetPosterFromModelActivity.this).load(mImageurl).into(iv_bigPicture);
                    }
                });
             }

            @Override
            public void onFailure(String url, String error) {

            }
        });

        //        list_pictures = new ArrayList<>();
        //        for (int i = 0; i < pictures.length; i++) {//把网络请求下来的图片存入线性布局中
        //
        //            final ImageView imageview = new ImageView(this);
        //
        //            int width = DensityUtil.dip2px(this, 100);
        //            int height = DensityUtil.dip2px(this, 80);
        //            int margin = DensityUtil.dip2px(this, 5);
        //            //设置布局参数
        //            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height, Gravity.CENTER_VERTICAL);
        //            params.setMargins(margin, margin, margin, margin);
        //
        //            imageview.setLayoutParams(params);
        //            imageview.setBackgroundResource(pictures[i]);
        //
        //            imageview.setOnClickListener(new View.OnClickListener() { //点击图片大背景图展示
        //                @Override
        //                public void onClick(View v) {
        //
        //                    iv_bigPicture.setBackgroundResource(pictures[0]);
        //                    Toast.makeText(GetPosterFromModelActivity.this, "获取图片成功", Toast.LENGTH_SHORT).show();
        //
        //                }
        //            });
        //
        //            ll_pictures.addView(imageview);
        //        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tv_choosePoster://确定键，提交选择的图片的path


                //
                //                Bundle bundle = new Bundle();
                //                bundle.putString("mAttachPath", mAttachPath);
                //
                //                intent.putExtras(bundle);
                Message msg = new Message();
                msg.what = 97;

                Bundle b = new Bundle();
                b.putString("mAttachPath", mAttachPath);
                //    b.putString("text", typeName);
                msg.setData(b);

                FaBuFragment.mHandlersmain.sendMessage(msg);
//                Intent intent = new Intent(this, FaBuFragment.class);
//                startActivity(intent);
                finish();
                Toast.makeText(GetPosterFromModelActivity.this, "提交图片成功", Toast.LENGTH_SHORT).show();


                break;


        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }
}
