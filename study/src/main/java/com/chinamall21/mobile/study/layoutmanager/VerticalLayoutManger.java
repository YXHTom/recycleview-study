package com.chinamall21.mobile.study.layoutmanager;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * desc：没有回收
 * author：Created by xusong on 2018/12/14 14:49.
 */


public class VerticalLayoutManger extends RecyclerView.LayoutManager {
    //总共itemview的高度
    private int mTotalHeight;

    private int mTotalMoveY;

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

        //摆放可见的itemview
        int temp = 0;
        for (int i = 0; i < getItemCount(); i++) {
            View child = recycler.getViewForPosition(i);
            measureChildWithMargins(child, 0, 0);
            int itemWidth = getDecoratedMeasuredWidth(child);
            int itemHeight = getDecoratedMeasuredHeight(child);
            addView(child);
            measureChildWithMargins(child, 0, 0);
            layoutDecoratedWithMargins(child, getPaddingLeft(), temp, itemWidth, temp + itemHeight);
            temp += itemHeight;
        }

        mTotalHeight = Math.max(getVerticalSpace(), temp);
    }

    //dy>0 👆 dy<0👇
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() == 0) {
            return dy;
        }
        int travel = dy;
        //限定拖动范围
        if (mTotalMoveY + travel < 0) {
            travel = -mTotalMoveY;
        } else if (mTotalMoveY + travel > mTotalHeight - getVerticalSpace()) {
            travel = mTotalHeight - getVerticalSpace() - mTotalMoveY;
        }
        mTotalMoveY += travel;
        offsetChildrenVertical(-travel);
        return travel;
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    private int getVerticalSpace() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }


}
