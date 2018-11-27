package com.chinamall21.mobile.study.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
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





    private boolean isEnableRefresh() {
        if(mHeader.getY() >= -mHeaderHeight / 2) {
            return true;
        } else
            return false;
    }

}
