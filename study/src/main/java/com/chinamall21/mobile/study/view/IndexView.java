package com.chinamall21.mobile.study.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.chinamall21.mobile.study.utils.SizeUtils;

import java.util.Arrays;
import java.util.List;

/**
 * desc：
 * author：Created by xusong on 2018/9/18 15:41.
 */


public class IndexView extends View {

    private String[] mChars = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
            "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private Paint mPaint;
    private List<String> mList;

    public IndexView(Context context) {
        this(context, null);
    }

    public IndexView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(SizeUtils.sp2px(context, 16));
        mList = Arrays.asList(mChars);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        for (int i = 0; i < mList.size(); i++) {
            float textWidth = mPaint.measureText(mList.get(i));
            canvas.drawText(mList.get(i), width / 2 - textWidth / 2, (height / mList.size()) * (i + 1), mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int index = (int) (event.getY() / (getMeasuredHeight() / mList.size()));
            if (index < mList.size()) {
                String s = mList.get(index);
                if (mOnIndexSelectListener != null) {
                    mOnIndexSelectListener.onSelected(s);
                }
            }

            return true;
        }
        return super.onTouchEvent(event);

    }

    public interface OnIndexSelectListener {
        void onSelected(String s);
    }

    private OnIndexSelectListener mOnIndexSelectListener;

    public void setOnIndexSelectListener(OnIndexSelectListener onIndexSelectListener) {
        mOnIndexSelectListener = onIndexSelectListener;

    }
}
