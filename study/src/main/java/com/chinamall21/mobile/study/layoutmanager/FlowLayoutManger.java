package com.chinamall21.mobile.study.layoutmanager;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * desc：流失布局
 * author：Created by xusong on 2018/12/14 14:49.
 */
public class FlowLayoutManger extends RecyclerView.LayoutManager {
    //总共itemview的高度
    private int mTotalHeight;

    private int mTotalMoveY;
    //默认margin值
    private final int mDefaultMargin = 15;

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
        int tempHeight = mDefaultMargin;
        int tempWidth = mDefaultMargin;
        for (int i = 0; i < getItemCount(); i++) {
            View view = recycler.getViewForPosition(i);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            int itemWidth = getDecoratedMeasuredWidth(view);
            int itemHeight = getDecoratedMeasuredHeight(view);
            //超出屏幕宽度换行
            if (tempWidth + itemWidth >= getHorizontalSpace()) {
                tempWidth = mDefaultMargin;
                tempHeight += itemHeight + mDefaultMargin;
            }
            layoutDecoratedWithMargins(view, tempWidth, tempHeight, itemWidth + tempWidth, tempHeight + itemHeight);
            tempWidth += itemWidth + mDefaultMargin;

        }
        mTotalHeight = Math.max(tempHeight, getVerticalSpace());
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

    private int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }
}
