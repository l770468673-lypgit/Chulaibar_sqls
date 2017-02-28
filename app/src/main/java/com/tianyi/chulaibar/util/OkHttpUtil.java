package com.tianyi.chulaibar.util;

import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;

import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OkHttp的工具类
 */
public class OkHttpUtil {

    private static OkHttpClient okHttpClient;
    private static Handler handler = new Handler();

    public static void initOkHttp() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 下载json
     * @param url
     */
    public static void downJSON(final String url,final OnDownDataListener onDownDataListener) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (onDownDataListener != null) {
                    onDownDataListener.onFailure(url, e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String str = response.body().string();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (onDownDataListener != null) {
                            try {
                                onDownDataListener.onResponse(url, str);
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
     * @param url
     * @param token
     * @param onDownDataListener
     */
    public static void downJSONwithtoken(final String url,String token, final OnDownDataListener onDownDataListener) {
        Request request = new Request.Builder().addHeader("Cookie","jdtk=\""+token+"\"")
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (onDownDataListener != null) {
                    onDownDataListener.onFailure(url, e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String str = response.body().string();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (onDownDataListener != null) {
                            try {
                                onDownDataListener.onResponse(url, str);
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
     * 同步get请求 -- 让子类调用
     *
     * @return
     */
    public static Response downResponse(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            return call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * post提交表单
     */
    public static void postSubmitFormWithToken(
            final String url,
            Map<String, String> params,
            String token,
            final OnDownDataListener onDownDataListener) {

        if(params.size()<=0){
            return;
        }


        FormBody.Builder builder = new FormBody.Builder();
        for (String key : params.keySet()) {
            String value = params.get(key);
            builder.add(key, value);
        }
        FormBody formBody = builder.build();

        final Request request = new Request.Builder()
                .addHeader("Cookie","jdtk=\""+token+"\"")
                .url(url)
                .post(formBody)
                .build();

        okHttpClient.newCall(request)
        .enqueue(new ResponseCallback(onDownDataListener,url));

    }



    /**
     * post 携带文件提交表单
     *
     * @param url 请求地址
     * @param params 请求参数
     * @param token 用户登陆校验码
     * @param onDownDataListener 请求完成的回调
     */
    public static void postFileSubmitFormWithToken(
            final String url,
            Map<String, Object> params,
            String token,
            final OnDownDataListener onDownDataListener) {

        if(params.size()<=0){  return;  }

        MultipartBody.Builder builder=new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        for (String key : params.keySet()) {
            Object value = params.get(key);
            if(value instanceof File){
                File file=(File) value;
                builder.addFormDataPart(key,file.getName(), RequestBody.create(null,file));
                continue;
            }
            builder.addFormDataPart(key, value+"");
        }

        RequestBody formBody = builder.build();

        final Request request = new Request.Builder()
                .addHeader("Cookie","jdtk=\""+token+"\"")
                .url(url)
                .post(formBody)
                .build();

        okHttpClient.newCall(request)
                .enqueue(new ResponseCallback(onDownDataListener,url));

    }



    /**
     * 执行请求完成之后的回调
     */
    public static class ResponseCallback implements Callback {

        private OnDownDataListener cb;
        private String url;

        /**
         *
         * @param cb 请求完成后的回调
         * @param url 请求 URL
         */
        public ResponseCallback(OnDownDataListener cb,String url){
            this.cb=cb;
            this.url=url;
        }

        @Override
        public void onFailure(Call call, IOException e) {
            e.printStackTrace();
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            final String str = response.body().string();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (cb == null) {  return;  }
                    try {
                        cb.onResponse(url, str);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }




    /**
     * post提交字符串请求
     */
    public static void postSubmitString(final String url, String string, final OnDownDataListener onDownDataListener) {
        MediaType mediatype = MediaType.parse("text/x-markdown; charset=utf-8");

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(mediatype, string))
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (onDownDataListener != null) {
                    onDownDataListener.onFailure(url, e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (onDownDataListener != null) {
                            try {
                                onDownDataListener.onResponse(url, str);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
    }

    public interface OnDownDataListener {
        void onResponse(String url, String json) throws JSONException;
        void onFailure(String url, String error);
    }

    /**
     * 保存bitmap到本地文件夹
     * @param bmp
     * @return
     * @throws IOException
     */
    public static boolean saveMyBitmap(Bitmap bmp) throws IOException {
        //判断sdcard是否存在
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            Log.i("print", "SD card 不存在");//或者不可用.
            return false;
        }
        String name = new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";

        //把照片写入文件
        FileOutputStream b = null;
        File file = new File("/sdcard/FishImage/");
        file.mkdirs();// 创建文件夹
        //name为图片的名称加后缀.
        String filePath = "/sdcard/FishImage/" + name;

        try {
            b = new FileOutputStream(filePath);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }


    public static void downpic(final String url, File filefinal, final OnDownDataListener onDownDataListener) {
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), filefinal);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "output_image.jpg", fileBody)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (onDownDataListener != null) {
                    onDownDataListener.onFailure(url, e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (onDownDataListener != null) {
                            try {
                                onDownDataListener.onResponse(url, str);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });

    }

}
