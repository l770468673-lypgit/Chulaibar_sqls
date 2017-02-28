package com.tianyi.chulaibar.util;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by thinkpad on 2016/7/2.
 */

public class SubmitPictures {


    // 上传代码，第一个参数，为要使用的URL，第二个参数，为表单内容，第三个参数为要上传的文件，可以上传多个文件，这根据需要页定
    public static boolean postCompressed(String actionUrl,
                                         Map<String, String> params, Map<String, String> files)
            throws IOException {

        String BOUNDARY = java.util.UUID.randomUUID().toString();
        String PREFIX = "--", LINEND = "\r\n";
        String MULTIPART_FROM_DATA = "multipart/form-data";
        String CHARSET = "UTF-8";
        URL uri = new URL(actionUrl);
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
        conn.setReadTimeout(5 * 1000);
        conn.setDoInput(true);// 允许输入
        conn.setDoOutput(true);// 允许输出
        conn.setUseCaches(false);
        conn.setRequestMethod("POST"); // Post方式
        conn.setRequestProperty("connection", "keep-alive");
        conn.setRequestProperty("Charsert", "UTF-8");
        conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
                + ";boundary=" + BOUNDARY);
        // 首先组拼文本类型的参数
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(PREFIX);
            sb.append(BOUNDARY);
            sb.append(LINEND);
            sb.append("Content-Disposition: form-data; name=\""
                    + entry.getKey() + "\"" + LINEND);
            sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
            sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
            sb.append(LINEND);
            sb.append(entry.getValue());
            sb.append(LINEND);

        }

        DataOutputStream outStream = null;

        //        try {
        outStream = new DataOutputStream(
                conn.getOutputStream());
        outStream.write(sb.toString().getBytes());
        //        } catch (Exception e) {
        //            Log.i("print", e.getMessage());
        //        }


        // 发送文件数据
        int i = 0;

        if (files != null) {

            Log.i("print", "工具类里files---" + files);

            for (Map.Entry<String, String> file : files.entrySet()) {

                StringBuilder sb1 = new StringBuilder();
                sb1.append(PREFIX);
                sb1.append(BOUNDARY);
                sb1.append(LINEND);
                sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\""
                        + file.getKey() + "\"" + LINEND);
                sb1.append("Content-Type: multipart/form-data; charset="
                        + CHARSET + LINEND);
                sb1.append(LINEND);
                outStream.write(sb1.toString().getBytes());

                Bitmap bm = BitmapFactory.decodeFile(file.getValue());

                bm.compress(Bitmap.CompressFormat.JPEG, 30, outStream);

                outStream.write(LINEND.getBytes());
            }
        }
        // 请求结束标志
        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
        outStream.write(end_data);
        outStream.flush();
        // 得到响应码
        boolean success = conn.getResponseCode() == 200;
        InputStream in = conn.getInputStream();
        InputStreamReader isReader = new InputStreamReader(in);
        BufferedReader bufReader = new BufferedReader(isReader);
        String line = null;
        String data = "getResult=";
        while ((line = bufReader.readLine()) != null) {
            data += line;
        }

        Log.i("print", "这是数据源---" + data.toString());

        outStream.close();
        conn.disconnect();
        return success;


        //        return false;
    }

}
