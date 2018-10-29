package com.chinamall21.mobile.study.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * desc：
 * author：Created by xusong on 2018/9/7 16:31.
 */


public class MyBitmapUtils {

    private NetWorkCacheUtils mNetWorkCacheUtils;

    private SdCardCacheUtils mSdCardCacheUtils;

    private MemoryCacheUtils mMemoryCacheUtils;

    private Bitmap mBitmap;

    public MyBitmapUtils() {
        mSdCardCacheUtils = new SdCardCacheUtils();
        mMemoryCacheUtils = new MemoryCacheUtils();
        mNetWorkCacheUtils = new NetWorkCacheUtils(mSdCardCacheUtils, mMemoryCacheUtils);
    }

    public void loadImage(ImageView imageView, String url) {
        //先从内存中拿
        mBitmap = mMemoryCacheUtils.getImage(url);
        if (mBitmap != null) {
            imageView.setImageBitmap(mBitmap);
            return ;
        }
        //从sd卡
        mBitmap = mSdCardCacheUtils.getImage(url);
        if (mBitmap != null) {
            imageView.setImageBitmap(mBitmap);
            return;
        }
        //从网络
        mNetWorkCacheUtils.getImage(imageView,url);

    }
}
