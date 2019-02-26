package com.chinamall21.mobile.study.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * desc：
 * author：Created by xusong on 2018/12/24 18:04.
 */


public class CircleBitmapTransForm extends BitmapTransformation {

    public CircleBitmapTransForm(Context context) {
        super(context);
    }

    public CircleBitmapTransForm(BitmapPool bitmapPool) {
        super(bitmapPool);
    }

    @Override
    public String getId() {
        return "com.example.glidetest.CircleCrop";
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        int min = Math.min(toTransform.getWidth(), toTransform.getHeight());
        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        /**
         * 产生一个同样大小的画布
         */
        Canvas canvas = new Canvas(target);
        /**
         * 首先绘制圆形
         */
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        /**
         * 使用SRC_IN
         */
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        /**
         * 绘制图片
         */
        canvas.drawBitmap(toTransform, 0, 0, paint);
        return target;
    }


}
