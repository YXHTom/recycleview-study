package com.chinamall21.mobile.study.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamall21.mobile.study.utils.SizeUtils;

/**
 * desc：
 * author：Created by xusong on 2018/11/2 11:15.
 */


public class MyIndictor extends LinearLayout {

    private String[] mTitles;
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private int mCurrentX;
    private int mTabWidth;

    public MyIndictor(Context context) {
        this(context, null);
    }

    public MyIndictor(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(SizeUtils.dp2px(context, 3));

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mWidth == 0 || mHeight == 0) {
            mWidth = getWidth();
            mHeight = getHeight();
            mTabWidth = mWidth / mTitles.length;
        }
        canvas.drawLine(mCurrentX, mHeight , mCurrentX + mTabWidth, mHeight , mPaint);

    }

    public void setTitles(String[] titles) {
        mTitles = titles;
        drawTitle();
    }

    private void drawTitle() {
        for (int i = 0; i < mTitles.length; i++) {
            TextView tv = new TextView(getContext());
            LayoutParams lp = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            lp.weight = 1;
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(Color.BLACK);
            tv.setText(mTitles[i]);
            tv.setLayoutParams(lp);
            addView(tv);

            final int finalI = i;
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalI == 0) {
                        mCurrentX = 0;
                    } else if (finalI == 1) {
                        mCurrentX = mTabWidth;
                    } else if (finalI == 2) {
                        mCurrentX = mTabWidth * 2;
                    }
                    if (mOnTabChangeListener != null) {
                        mOnTabChangeListener.onTabChange(finalI);
                    }
                    invalidate();
                }
            });
        }
    }


    public void onScroll(int position, int positionOffsetPixels) {
        if (mWidth != 0) {
            mCurrentX = position * mTabWidth + positionOffsetPixels / mTitles.length;
            invalidate();
        }
    }

    public interface onTabChangeListener {
        void onTabChange(int position);
    }

    private onTabChangeListener mOnTabChangeListener;

    public void setOnTabChangeListener(onTabChangeListener onTabChangeListener) {
        mOnTabChangeListener = onTabChangeListener;
    }
}
