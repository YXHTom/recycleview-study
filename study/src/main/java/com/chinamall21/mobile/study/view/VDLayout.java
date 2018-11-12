package com.chinamall21.mobile.study.view;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.chinamall21.mobile.study.utils.LogUtils;

/**
 * desc：
 * author：Created by xusong on 2018/11/7 12:24.
 */


public class VDLayout extends LinearLayout {
    private ViewDragHelper mDragHelper;

    private Point mPoint = new Point();
    private View mView2;
    private View mView3;
    private View mView1;

    public VDLayout(Context context) {
        this(context, null);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mView1 = getChildAt(0);
        mView2 = getChildAt(1);
        mView3 = getChildAt(2);
    }

    public VDLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                return true;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                LogUtils.LogE("left = "+left);
                if (left <= 0) {
                    left = 0;
                } else if (left >= getWidth() - child.getWidth()) {
                    left = getWidth() - child.getWidth();
                }
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                if (top <= 0) {
                    top = 0;
                } else if (top >= getHeight() - child.getHeight()) {
                    top = getHeight() - child.getHeight();
                }

                return top;
            }

            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                if (releasedChild == mView2) {
                    mDragHelper.settleCapturedViewAt(mPoint.x, mPoint.y);
                    invalidate();
                }
            }
            //在边界拖动时回调
            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                mDragHelper.captureChildView(mView3, pointerId);
            }


        });
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_RIGHT);
    }

    @Override
    public void computeScroll() {
        if(mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mPoint.x = mView2.getLeft();
        mPoint.y = mView2.getTop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return mDragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

}
