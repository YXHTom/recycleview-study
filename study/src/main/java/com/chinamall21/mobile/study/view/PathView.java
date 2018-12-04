package com.chinamall21.mobile.study.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * desc：
 * author：Created by xusong on 2018/11/22 16:12.
 */


public class PathView extends View {

    private Paint mPaint;
    private int mCurrentX;
    private Path mPath;
    private int mItemWidth = 300;

    public PathView(Context context) {
        this(context, null);
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.GREEN);
        mPath = new Path();
        ValueAnimator va = ValueAnimator.ofInt(0, mItemWidth);
        va.setDuration(1500);
        va.setRepeatCount(ValueAnimator.INFINITE);
        va.setInterpolator(new LinearInterpolator());
        va.start();
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                mCurrentX = value;
                invalidate();
            }
        });
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(getWidth()/2,getHeight()/2,400,mPaint);

        mPath.reset();
        mPath.moveTo(mCurrentX , getHeight()/2);
        for (int i = 0; i <= getWidth() ; i += mItemWidth) {
            mPath.rQuadTo(mItemWidth / 4, -70, mItemWidth / 2, 0);
            mPath.rQuadTo(mItemWidth / 4, 70, mItemWidth / 2, 0);
        }
        mPath.lineTo(getWidth(),getHeight());
        mPath.lineTo(0,getHeight());

        canvas.drawPath(mPath, mPaint);


    }

}
