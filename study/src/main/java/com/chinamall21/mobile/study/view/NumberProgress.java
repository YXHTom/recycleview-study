package com.chinamall21.mobile.study.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.chinamall21.mobile.study.R;

/**
 * desc：
 * author：Created by xusong on 2018/12/26 16:02.
 */
public class NumberProgress extends View {
    private Paint mPaint;
    private String text="0%";
    private Rect mTextRect;
    private final int START = 100;
    private final int END = 980;
    private final int HEIGHT = 50;
    private final float itemWidth = 8.8f;
    private float mCurrentWidth = START;

    public NumberProgress(Context context) {
        super(context);
    }

    public NumberProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NumberProgress);
        int color = typedArray.getColor(R.styleable.NumberProgress_progress_color, Color.BLACK);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
        mPaint.setStrokeWidth(8);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setTextSize(40);
        mTextRect = new Rect();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });

    }

    public void start(){
        ValueAnimator va = ValueAnimator.ofInt(0, 100);
        va.setDuration(3000);
        va.start();
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                text = value + "%";
                mCurrentWidth = value * itemWidth+START ;
                invalidate();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(),100);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.getTextBounds(text, 0, text.length(), mTextRect);
        if (mCurrentWidth <= END - mTextRect.width()) {
            canvas.drawLine(START, HEIGHT, mCurrentWidth, HEIGHT, mPaint);
            canvas.drawText(text, mCurrentWidth, HEIGHT + mTextRect.height() / 2, mPaint);
            canvas.drawLine(mCurrentWidth+mTextRect.width(), HEIGHT, END, HEIGHT, mPaint);
        } else {
            canvas.drawLine(START, HEIGHT, END - mTextRect.width(), HEIGHT, mPaint);
            canvas.drawText(text, END - mTextRect.width(), HEIGHT + mTextRect.height() / 2, mPaint);
        }

    }
}
