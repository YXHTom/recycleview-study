package com.chinamall21.mobile.study.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * desc：
 * author：Created by xusong on 2018/9/7 16:31.
 */


public class NetWorkCacheUtils {
    private ImageView mImageView;
    private String mUrl;
    private SdCardCacheUtils mSdCardCacheUtils;
    private MemoryCacheUtils mMemoryCacheUtils;

    public NetWorkCacheUtils(SdCardCacheUtils sdCardCacheUtils, MemoryCacheUtils memoryCacheUtils) {
        mSdCardCacheUtils = sdCardCacheUtils;
        mMemoryCacheUtils = memoryCacheUtils;
    }

    public void getImage(ImageView iv, String url) {
        new ImageLoadTask().execute(iv, url);
    }

    /**
     * 异步下载
     * <p/>
     * 第一个泛型 ： 参数类型  对应doInBackground（）
     * 第二个泛型 ： 更新进度   对应onProgressUpdate（）
     * 第三个泛型 ： 返回结果result   对应onPostExecute
     */
    class ImageLoadTask extends AsyncTask<Object, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Object... objects) {
            mImageView = (ImageView) objects[0];
            mUrl = (String) objects[1];
            mImageView.setTag(mUrl);
            Bitmap bitmap = downBitmap(mUrl);
            return bitmap;
        }

        private Bitmap downBitmap(String url) {
            //连接
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) new URL(url).openConnection();

                //设置读取超时
                conn.setReadTimeout(5000);
                //设置请求方法
                conn.setRequestMethod("GET");
                //设置连接超时连接
                conn.setConnectTimeout(5000);
                //连接
                conn.connect();

                //响应码
                int code = conn.getResponseCode();

                if (code == 200) {  //请求正确的响应码是200
                    //得到响应流
                    InputStream inputStream = conn.getInputStream();
                    //得到bitmap对象
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                    return bitmap;
                }
            } catch (IOException e) {

                e.printStackTrace();
            } finally {
                conn.disconnect();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                //得到图片tag值
                String url = (String) mImageView.getTag();
                if(url.equals(mUrl)){
                    LogUtils.LogE("从网络加载成功");
                    mImageView.setImageBitmap(bitmap);
                    //保存打sd卡
                    mSdCardCacheUtils.save(mUrl,bitmap);
                    //保存到内存
                    mMemoryCacheUtils.saveImage(mUrl,bitmap);
                }
            }
        }
    }

}
