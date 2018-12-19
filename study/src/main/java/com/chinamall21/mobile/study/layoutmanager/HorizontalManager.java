package com.chinamall21.mobile.study.layoutmanager;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * desc：
 * author：Created by xusong on 2018/12/18 17:06.
 */


public class HorizontalManager extends RecyclerView.LayoutManager {
    private int mTotalWidth;
    private int mTotalMoveX;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() == 0) {
            return;
        }
        detachAndScrapAttachedViews(recycler);
        View view = recycler.getViewForPosition(0);
        measureChildWithMargins(view, 0, 0);
        int itemWidth = getDecoratedMeasuredWidth(view);
        int itemHeight = getDecoratedMeasuredHeight(view);
        int temp = 0;
        for (int i = 0; i < getItemCount(); i++) {
            View child = recycler.getViewForPosition(i);
            addView(child);
            measureChildWithMargins(child, 0, 0);
            layoutDecorated(child, temp, 0, itemWidth + temp, itemHeight);
            temp += itemWidth;
        }
        mTotalWidth = Math.max(temp, getHorizontalSpace());
    }

    //dx>0👈 dx<0👉
    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() == 0) {
            return dx;
        }
        int travel = dx;
        if (travel + mTotalMoveX < 0) {
            travel = -mTotalMoveX;
        } else if (travel + mTotalMoveX > mTotalWidth - getHorizontalSpace()) {
            travel = mTotalWidth - getHorizontalSpace() - mTotalMoveX;
        }

        mTotalMoveX += travel;
        offsetChildrenHorizontal(-travel);
        return travel;
    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    private int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }
}
