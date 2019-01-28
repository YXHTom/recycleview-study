package com.chinamall21.mobile.study.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.chinamall21.mobile.study.R;

/**
 * desc：
 * author：Created by xusong on 2018/11/21 17:44.
 */


public class CircleImageView extends android.support.v7.widget.AppCompatImageView {
    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = 0;
        int height = 0;
        if (widthMode == MeasureSpec.AT_MOST) {
            width = 100;
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            height = 100;
        }
        setMeasuredDimension(width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Bitmap mSrc = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.see),
                getWidth(), getHeight(), false);
        canvas.drawBitmap(createCircleImage(mSrc, getWidth()), 0, 0, null);
    }

    /**
     * 根据原图和变长绘制圆形图片
     * @param source
     * @param min
     * @return
     */
    private Bitmap createCircleImage(Bitmap source, int min) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
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
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }


}
