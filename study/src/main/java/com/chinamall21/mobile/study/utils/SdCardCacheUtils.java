package com.chinamall21.mobile.study.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * desc：
 * author：Created by xusong on 2018/9/7 16:31.
 */


public class SdCardCacheUtils {

    public Bitmap getImage(String url) {
        File file = new File(Environment.getExternalStorageDirectory(), url);
        if (file.exists()) {
            LogUtils.LogE("从sd卡读取到了.." + file.getAbsolutePath());
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        }
        return null;
    }


    public void save(String url, Bitmap bitmap) {
        File file = new File(Environment.getExternalStorageDirectory(), url);
        //我们首先得到他的符文剑
        File parentFile = file.getParentFile();
        //查看是否存在，如果不存在就创建
        if (!parentFile.exists()) {
            parentFile.mkdirs(); //创建文件夹
        }
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
            LogUtils.LogE("保存sdka成功");
        } catch (FileNotFoundException e) {
            LogUtils.LogE("保存失败FileNotFoundException");
            e.printStackTrace();
        }
    }
}
