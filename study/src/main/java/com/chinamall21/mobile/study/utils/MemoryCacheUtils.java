package com.chinamall21.mobile.study.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * desc：
 * author：Created by xusong on 2018/9/7 16:31.
 */


public class MemoryCacheUtils {
    private LruCache<String,Bitmap> mLruCache;

    public MemoryCacheUtils() {
        long maxMemory = Runtime.getRuntime().maxMemory();//最大内存  默认是16兆  运行时候的
        LogUtils.LogE("maxMemory = "+maxMemory);
        mLruCache = new LruCache<String,Bitmap>((int) (maxMemory/8)){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                int byteCount = value.getRowBytes() * value.getWidth();
                return byteCount;
            }
        };

    }

    public Bitmap getImage(String url) {
        LogUtils.LogE("从内存中拿");
        return mLruCache.get(url);
    }

    public void saveImage(String url,Bitmap bitmap){
        LogUtils.LogE("存内存");
        mLruCache.put(url,bitmap);

    }


}
