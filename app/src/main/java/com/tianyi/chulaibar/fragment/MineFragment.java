package com.tianyi.chulaibar.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tianyi.chulaibar.R;
import com.tianyi.chulaibar.activity.CanYuActivity;
import com.tianyi.chulaibar.activity.CollectionActivity;
import com.tianyi.chulaibar.activity.ExitActivity;
import com.tianyi.chulaibar.activity.FaBuActivity;
import com.tianyi.chulaibar.activity.GeRenXinXiActivity;
import com.tianyi.chulaibar.activity.HelpActivity;
import com.tianyi.chulaibar.activity.PrizeActivity;
import com.tianyi.chulaibar.activity.SheZhiActivity;
import com.tianyi.chulaibar.activity.XiangCeActivity;
import com.tianyi.chulaibar.base.BaseFragment;
import com.tianyi.chulaibar.util.CropImages;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener{


    private RelativeLayout rl_gerenxinxi_wode,rl_shezhi_wode,rl_fabu_wode,rl_prize_wode,
            rl_canyu_wode,rl_help_wode,rl_shoucang_wode,rl_tuichu_wode,rl_xiangce_wode;

    private final ThreadLocal<CircleImageView> iv_touxiang_wode = new ThreadLocal<>();

    private CropImages cropImages = null;  //剪裁图片的工具类
    private ImageView iv_setting_wode;





    public MineFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentResid() {
        return R.layout.fragment_mine;
    }


    @Override
    protected void init(View view) {


        //修改头像
        iv_touxiang_wode.set((CircleImageView) view.findViewById(R.id.iv_touxiang_wode));
        iv_touxiang_wode.get().setOnClickListener(this);

        iv_setting_wode= (ImageView) view.findViewById(R.id.iv_setting_wode);
        iv_setting_wode.setOnClickListener(this);

        cropImages = new CropImages(this,getActivity());

    }

    @Override
    protected void loadDatas() {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

//            case R.id.rl_gerenxinxi_wode:
//                Intent gerenxinxiIntent = new Intent(getActivity(),GeRenXinXiActivity.class);
//                startActivity(gerenxinxiIntent);
//
//                break;
//            case R.id.rl_shezhi_wode:
//                Intent shezhiIntent = new Intent(getActivity(),SheZhiActivity.class);
//                startActivity(shezhiIntent);
//
//                break;
//            case R.id.rl_fabu_wode:
//                Intent fabuIntent = new Intent(getActivity(),FaBuActivity.class);
//                startActivity(fabuIntent);
//
//                break;
//
//            case R.id.rl_prize_wode:
//                Intent prizeIntent = new Intent(getActivity(),PrizeActivity.class);
//                startActivity(prizeIntent);
//
//                break;
//            case R.id.rl_canyu_wode:
//                Intent canyuIntent = new Intent(getActivity(),CanYuActivity.class);
//                startActivity(canyuIntent);
//
//                break;
//            case R.id.rl_help_wode:
//                Intent helpIntent = new Intent(getActivity(),HelpActivity.class);
//                startActivity(helpIntent);
//
//                break;
//
//            case R.id.rl_shoucang_wode:
//                Intent collectionIntent = new Intent(getActivity(),CollectionActivity.class);
//                startActivity(collectionIntent);
//
//                break;
//
//            case R.id.rl_tuichu_wode:
//                Intent tuichuIntent = new Intent(getActivity(),ExitActivity.class);
//                startActivity(tuichuIntent);
//
//                break;
//
//            case R.id.rl_xiangce_wode:
//                Intent xiangceIntent = new Intent(getActivity(),XiangCeActivity.class);
//                startActivity(xiangceIntent);
//
//                break;
//
            case R.id.iv_touxiang_wode:   //修改头像
               //弹出从相册或者相机选择照片的对话框
                chooseDialog();

                break;

            case R.id.iv_setting_wode:   //设置按钮

                Intent setIntent = new Intent(getActivity(),SheZhiActivity.class);
                startActivity(setIntent);

                break;

        }

    }

    private void chooseDialog() {

        new AlertDialog.Builder(getActivity())//
                .setTitle("选择头像")//

                .setNegativeButton("相册", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        cropImages.byAlbum();

                    }
                })

                .setPositiveButton("拍照", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        String status = Environment.getExternalStorageState();
                        if (status.equals(Environment.MEDIA_MOUNTED)) {// 判断是否存在SD卡
                            cropImages.byCamera();
                        }
                    }
                }).show();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case CropImages.ACTIVITY_RESULT_CAMERA: // 拍照
                try {
                    if (resultCode == -1) {

                        Log.d("bm_icon","拍照外面");

                        cropImages.cutImageByCamera();
                        Bitmap bm_icon = cropImages.decodeBitmap();//不执行
                        Log.d("bm_icon","拍照中间");
                        if (bm_icon != null) {
                            Log.d("bm_icon","拍照里面");
                            iv_touxiang_wode.get().setImageBitmap(bm_icon);
                        }

                    } else {
                        // 因为在无任何操作返回时，系统依然会创建一个文件，这里就是删除那个产生的文件
                        if (cropImages.picFile != null) {
                            cropImages.picFile.delete();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case CropImages.ACTIVITY_RESULT_ALBUM:
                try {
                    if (resultCode == -1) {
                        Log.d("bm_icon","相册外面");
                        Bitmap bm_icon = cropImages.decodeBitmap();   //不执行
                        Log.d("bm_icon","相册中间");
                        if (bm_icon != null) {
                            Log.d("bm_icon","相册里面");

                            iv_touxiang_wode.get().setImageBitmap(bm_icon);
                        }
                    } else {
                        // 因为在无任何操作返回时，系统依然会创建一个文件，这里就是删除那个产生的文件
                        if (cropImages.picFile != null) {
                            cropImages.picFile.delete();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

    }
}
