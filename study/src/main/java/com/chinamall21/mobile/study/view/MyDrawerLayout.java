package com.chinamall21.mobile.study.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * desc：
 * author：Created by xusong on 2018/11/7 14:59.
 */


public class MyDrawerLayout extends ViewGroup {

    private View mMenu;
    private View mContent;
    private ViewDragHelper mDragHelper;

    public MyDrawerLayout(Context context) {
        this(context, null);
    }

    public MyDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {

            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                return child == mMenu;
            }

            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                if (releasedChild == mMenu) {
                    if (mMenu.getLeft() >= -mMenu.getMeasuredWidth() / 2) {
                        mDragHelper.settleCapturedViewAt(0, 0);
                    } else {
                        mDragHelper.settleCapturedViewAt(-mMenu.getMeasuredWidth(), 0);

                    }
                    invalidate();
                }
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                mDragHelper.captureChildView(mMenu, pointerId);
            }

            @Override
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
                if (child == mMenu) {
                    if (left <= -mMenu.getMeasuredWidth()) {
                        left = -mMenu.getMeasuredWidth();
                    } else if (left >= 0) {
                        left = 0;
                    }
                }
                return left;
            }

            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {

                return super.clampViewPositionVertical(child, top, dy);
            }
        });
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    public void computeScroll() {
        if(mDragHelper.continueSettling(true)) {
            invalidate();
        }
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMenu = getChildAt(1);
        mContent = getChildAt(0);
        mMenu.measure(widthMeasureSpec, heightMeasureSpec);
        mContent.measure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mContent.layout(0, 0, getWidth(), getBottom());
        mMenu.layout(-900, 0, 0, getBottom());
    }
}
