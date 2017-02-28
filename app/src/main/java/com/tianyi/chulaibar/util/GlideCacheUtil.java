package com.tianyi.chulaibar.util;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;

import com.bumptech.glide.Glide;

import java.io.File;
import java.math.BigDecimal;

/**
 * Glide缓存工具类
 * Created by Trojx on 2016/10/10 0010.
 */

public class GlideCacheUtil {
    private static volatile GlideCacheUtil inst;

    private GlideCacheUtil() {
    }

    public static GlideCacheUtil getInstance() {
        if (inst == null) {
            synchronized (GlideCacheUtil.class) {
                if (inst == null) {
                    inst = new GlideCacheUtil();
                }
            }
        }
        return inst;
    }

    /**
     * 清除图片磁盘缓存
     */
    public boolean clearImageDiskCache(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context).clearDiskCache();
                    }
                }).start();
                return true;
            } else {
                Glide.get(context).clearDiskCache();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 清除图片内存缓存
     */
    public boolean clearImageMemoryCache(Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(context).clearMemory();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 清除图片所有缓存
     */
    public boolean clearImageAllCache(Context context) {
        String ImageExternalCacheDir = context.getExternalCacheDir().getAbsolutePath();
        String ImageInternalCacheDir = context.getCacheDir().getAbsolutePath();

        if (deleteFolderFile(ImageExternalCacheDir, true)
                && deleteFolderFile(ImageInternalCacheDir, true))
            return true;
        else
            return false;
    }

    /**
     * 获取缓存大小 程序内部缓存和外部缓存
     *
     * @return CacheSize
     */
    public String getCacheSize(Context context) {
        try {
            return getFormatSize(getFolderSize(new File(context.getCacheDir().getAbsolutePath()))
                    + getFolderSize(new File(context.getExternalCacheDir().getAbsolutePath())));
        } catch (Exception e) {
            e.printStackTrace();
            return "0.0Byte";
        }
    }

    /**
     * 获取指定文件夹内所有文件大小的和
     *
     * @param file file
     * @return size
     * @throws Exception
     */
    private long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 删除指定目录下的文件，这里用于缓存的删除
     *
     * @param filePath       filePath
     * @param deleteThisPath deleteThisPath
     */
    private boolean deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {
                    File files[] = file.listFiles();
                    for (File file1 : files) {
                        deleteFolderFile(file1.getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {
                        file.delete();
                        return true;
                    } else {
                        if (file.listFiles().length == 0) {
                            file.delete();
                            return true;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * 格式化单位
     *
     * @param size size
     * @return size
     */
    private static String getFormatSize(double size) {

        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }
}