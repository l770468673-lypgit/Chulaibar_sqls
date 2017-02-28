package com.tianyi.chulaibar.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Ken on 2016/3/7.
 * 下载工具类
 */
public class DownUtil {


    /**
     * 线程池
     */
    private static ExecutorService executorService = Executors.newFixedThreadPool(5);

    private static Handler handler = new Handler();

    /**
     * 根据url返回byte数组
     * @param url
     * @return
     */
    private static byte[] requestUrl(String url){
        try {
            URL ul = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) ul.openConnection();
          //  connection.setRequestMethod("GET");
            connection.setRequestMethod("POST");
            connection.setReadTimeout(5000);
            connection.setDoInput(true);

            InputStream in = connection.getInputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int len = 0;
            byte[] buffer = new byte[1024];
            while((len = in.read(buffer)) != -1){
                out.write(buffer, 0, len);
            }

            in.close();

            return out.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 请求JSON
     * @return
     */
    private static String requestJSON(String url){
        byte[] bytes = requestUrl(url);
        if(bytes != null){
            return new String(bytes);
        }

        return null;
    }

    /**
     * 请求图片
     * @param url
     * @return
     */
    public static Bitmap requestImg(String url){
        byte[] bytes = requestUrl(url);
        if(bytes != null){
           return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }

        return null;
    }

    /**
     * 下载JSON
    */
    public static void downJSON(final String url, final OnDownListener onDownListener){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                //得到下载的json
                final String json = requestJSON(url);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(onDownListener != null){
                            try {
                                onDownListener.onDownSucc(url, json);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
    }

    /**
     * 下载图片
     */
    public static void downBitmap(final String url, final OnDownListener onDownListener){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                //得到下载的json
                final Bitmap bitmap = requestImg(url);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (onDownListener != null) {
                            try {
                                onDownListener.onDownSucc(url, bitmap);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
    }

    /**
     * 下载成功的接口回调
     */
    public interface OnDownListener{
        void onDownSucc(String url, Object obj) throws JSONException;
    }
}
