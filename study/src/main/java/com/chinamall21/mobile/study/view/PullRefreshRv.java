package com.chinamall21.mobile.study.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.chinamall21.mobile.study.utils.LogUtils;

/**
 * desc：
 * author：Created by xusong on 2018/11/7 18:25.
 */


public class PullRefreshRv extends RecyclerView {

    private View mHeader;
    private int mHeaderHeight;

    public PullRefreshRv(Context context) {
        this(context, null);
    }

    public PullRefreshRv(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullRefreshRv(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        if (((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition() == 0) {
            if (isEnableRefresh()) {
                LogUtils.LogE("释放刷新");
            } else {
                LogUtils.LogE("下拉刷新");
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeader = getChildAt(0);
        mHeaderHeight = mHeader.getMeasuredHeight();
        //scrollBy(0, mHeaderHeight);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_UP) {
            if (isEnableRefresh()) {
                LogUtils.LogE("up释放刷新");

            } else {
                LogUtils.LogE("up下拉刷新");
                int y = (int) mHeader.getY();
                LogUtils.LogE("y ="+y);
                scrollBy(0, (int) -mHeader.getY());
            }

        }
        return super.onTouchEvent(e);
    }


    private boolean isEnableRefresh() {
        if(mHeader.getY() >= -mHeaderHeight / 2) {
            return true;
        } else
            return false;
    }

}
