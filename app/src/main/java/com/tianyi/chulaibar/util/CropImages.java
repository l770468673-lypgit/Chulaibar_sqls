package com.tianyi.chulaibar.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import com.tianyi.chulaibar.activity.MainActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Administrator on 2016/10/28 0028.
 */
public class CropImages {

    public final static int ACTIVITY_RESULT_CAMERA = 0001;//选择 拍照 的返回码
    public final static int ACTIVITY_RESULT_ALBUM = 0002;//选择 相册 的返回码

    public Uri photoUri;// 图片路径的URI
    private Uri tempUri;
    public File picFile;// 图片文件
    private Context context;
    private Fragment fragment;
    // 构造方法
    public  CropImages() {
        super();
    }
    public  CropImages(Fragment fragment) {
        super();
        this.fragment = fragment;
    }

    public  CropImages(Fragment fragment,Context context) {
        super();
        this.fragment = fragment;
        this.context = context;
    }

    // 选择拍照的方式
    public  void byCamera() {
        try {
            // 创建文件夹
            File uploadFileDir = new File(
                    Environment.getExternalStorageDirectory(), "/icon");

            if (!uploadFileDir.exists()) {
                uploadFileDir.mkdirs();
            }
            // 创建图片，以当前系统时间命名
            picFile = new File(uploadFileDir,
                    SystemClock.currentThreadTimeMillis() + ".png");
            if (!picFile.exists()) {
                picFile.createNewFile();
            }
            // 获取到图片路径
            tempUri = Uri.fromFile(picFile);

            // 启动Camera的Intent，传入图片路径作为存储路径
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
            //启动Intent
//            ((MainActivity) context).startActivityForResult(cameraIntent,
//                    ACTIVITY_RESULT_CAMERA);

            fragment.startActivityForResult(cameraIntent,
                    ACTIVITY_RESULT_CAMERA);


            System.out.println("-->tempUri : " + tempUri.toString()
                    + "-->path:" + tempUri.getPath());
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 选择相册的方式
    public void byAlbum() {
        try {
            // 创建文件夹
            File pictureFileDir = new File(
                    Environment.getExternalStorageDirectory(), "/icon");
            if (!pictureFileDir.exists()) {
                pictureFileDir.mkdirs();
            }
            // 创建图片，以当前系统时间命名
            picFile = new File(pictureFileDir,
                    SystemClock.currentThreadTimeMillis() + ".png");
            if (!picFile.exists()) {
                picFile.createNewFile();
            }
            // 获取到图片路径
            tempUri = Uri.fromFile(picFile);

            // 获得剪辑图片的Intent
            final Intent intent = cutImageByAlbumIntent();
//            ((MainActivity) context).startActivityForResult(intent,
//                    ACTIVITY_RESULT_ALBUM);
            fragment.startActivityForResult(intent,
                    ACTIVITY_RESULT_ALBUM);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 调用图片剪辑程序的Intent
    private Intent cutImageByAlbumIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        //设定宽高比
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //设定剪裁图片宽高
//        intent.putExtra("outputX", 50);
//        intent.putExtra("outputY", 50);

        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);





        intent.putExtra("noFaceDetection", true);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        return intent;
    }

    //通过相机拍照后进行剪辑
    public void cutImageByCamera() {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(tempUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
//        intent.putExtra("outputX", 50);
//        intent.putExtra("outputY", 50);

        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);

        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        fragment.startActivityForResult(intent,
                ACTIVITY_RESULT_ALBUM);
    }

    // 对图片进行编码成Bitmap
    public Bitmap decodeBitmap() {
        Bitmap bitmap = null;
        try {
            if (tempUri != null) {
                photoUri = tempUri;
                bitmap = BitmapFactory.decodeStream(
                        context.getContentResolver().openInputStream(photoUri));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

}
