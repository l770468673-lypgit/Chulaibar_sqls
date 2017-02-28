package com.tianyi.chulaibar.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tianyi.chulaibar.R;
import com.tianyi.chulaibar.base.BaseActivity;
import com.tianyi.chulaibar.fragment.FaBuFragment;
import com.tianyi.chulaibar.util.Constant;
import com.tianyi.chulaibar.util.L;
import com.tianyi.chulaibar.util.OkHttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PosterSetActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "PosterSetActivity";
    private Uri imageUri;
    private BufferedOutputStream mBos;
    private RelativeLayout rl_xiangce_activity_posterset, rl_camera_activity_posterset, rl_local_activity_posterset;
    private String mResult;
    private Bitmap mBitmap;
    private Bitmap mBitmap1;
    private File mOutputImage;

    private TextView haibaodengurl;

    @Override
    protected int getContentResid() {
        return R.layout.activity_poster_set;
    }

    @Override
    protected void init() {
        //初始化相册，相机和本地获取海报布局对象
        rl_xiangce_activity_posterset = (RelativeLayout) findViewById(R.id.rl_xiangce_activity_posterset);
        rl_xiangce_activity_posterset.setOnClickListener(this);

        rl_camera_activity_posterset = (RelativeLayout) findViewById(R.id.rl_camera_activity_posterset);
        rl_camera_activity_posterset.setOnClickListener(this);

        rl_local_activity_posterset = (RelativeLayout) findViewById(R.id.rl_local_activity_posterset);
        rl_local_activity_posterset.setOnClickListener(this);
        haibaodengurl = (TextView) findViewById(R.id.haibaodengurl);


        mOutputImage = new File(Environment.getExternalStorageDirectory(), "haibao.jpg");
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.rl_xiangce_activity_posterset://从相册选取
                try {
                    if (mOutputImage.exists()) {
                        mOutputImage.delete();
                    }
                    mOutputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Intent intentxiangce = new Intent("android.intent.action.GET_CONTENT");
                intentxiangce.setType("image/*");
                startActivityForResult(intentxiangce, Constant.CODE.TAKE_CAMEAR_XIANGCE_HAIBAO);

                break;

            case R.id.rl_camera_activity_posterset://调用系统相机

                 /* 创建一个File对象，用来存储摄像头拍下的照片 File类构造方法接收两个参数 ①SD卡路径 通过Environment对象的getExternalStorageDirectory()方法获取sd的根目录
                 * ② 文件名称*/
                //  File outputImage = new File(Environment.getExternalStorageDirectory(), "output_image.jpg");
                try {
                    if (mOutputImage.exists()) {//判断文件是否存在，弱存在就删除。
                        mOutputImage.delete();
                    }
                    mOutputImage.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                   /* 调用Uri的fromFile()方法将File对象转化成Uri对象。 */
                imageUri = Uri.fromFile(mOutputImage);
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");//设置隐形Intent启动的匹配条件,IMAGE_CAPTURE表示启动相机。
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//调用Intent的putExtra()方法设置拍下照片储存的路径，跟File储存的路径一样。
                startActivityForResult(intent, Constant.CODE.TAKE_PHOTO_XIANGJI_HAIBAO);//startActivityForResult方式启动活动。即启动相机 参数1传入Intent对象，参数2传入一个用来返回的自定义值。在onActivityResult中做判断

                break;

            //
            case R.id.rl_local_activity_posterset:

                Intent modelIntent = new Intent(this, GetPosterFromModelActivity.class);

                startActivity(modelIntent);

                break;

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Constant.CODE.TAKE_PHOTO_XIANGJI_HAIBAO:
                if (resultCode == -1) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, Constant.CODE.CROP_PHOTO_ONE_HAIBAO);
                }
                break;
            case Constant.CODE.CROP_PHOTO_ONE_HAIBAO:

                if (resultCode == -1) {
                    //
                    try {
                        mBitmap1 = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "haibao.jpg"))));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    ladDatetoCamear();
                    L.d(TAG, "  xiangji  this pic name is " + mResult);

                    finish();
                }

                break;

            case Constant.CODE.TAKE_CAMEAR_XIANGCE_HAIBAO:
                Log.d("MainActivity", "???????: " + resultCode);
                if (resultCode == -1) {
                    String imagePath = getPath(this, data.getData()); //
                    Uri imageUri = Uri.fromFile(new File(imagePath));
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("crop", true);
                    intent.putExtra("scale", true);
                    intent.putExtra("return-data", false);

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "haibao.jpg")));

                    startActivityForResult(intent, Constant.CODE.SET_CAMEAR_VIEW_HAIBAO);
                }
                break;
            case Constant.CODE.SET_CAMEAR_VIEW_HAIBAO:
                if (resultCode == -1) {
                    try {
                        mBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "haibao.jpg"))));
                        ladDatetoCamear();
                        L.d(TAG, "  xiangce mMypic is " + mResult);
                        finish();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;

//            case Constant.CODE.SET_HAIBAOI_VIEW_request:
//
//                if (resultCode == -1) {
//                    Intent intent = getIntent();
//                    Bundle extras = intent.getExtras();
//                    String mAttachPath = extras.getString("mAttachPath");
//
//
//                    //  startActivityForResult();
//
//                }
//
//
//                break;


            default:
                break;

        }


    }

    public void ladDatetoCamear() {

        File file = new File(Environment.getExternalStorageDirectory(), "haibao.jpg");//将要保存图片的路径
        String url = Constant.URL.TIJIAOTUPIANURL;

        OkHttpUtil.downpic(url, file, new OkHttpUtil.OnDownDataListener() {

            @Override
            public void onResponse(String url, String json) throws JSONException {

                JSONObject object = new JSONObject(json);
                mResult = object.getString("result");
                if (mResult != null) {
                    L.d(TAG, "result is  " + mResult.toString());
                    haibaodengurl.setText(mResult);

                    Message msg = new Message();
                    msg.what = 98;

                    Bundle b = new Bundle();
                    b.putString("mResult", mResult);
                    msg.setData(b);

                    L.d(TAG,"haibao is the" + mResult);
                    FaBuFragment.mHandlersmain.sendMessage(msg);

                }
              //  return mResult;

            }

            @Override
            public void onFailure(String url, String error) {

            }
        });


    }

    @TargetApi(19)
    public static String getPath(Activity context, Uri imageUri) {
        if (context == null || imageUri == null)
            return null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imageUri)) {
            if (isExternalStorageDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(imageUri)) {
                String id = DocumentsContract.getDocumentId(imageUri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } // MediaStore (and general)
        else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(imageUri))
                return imageUri.getLastPathSegment();
            return getDataColumn(context, imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

}
