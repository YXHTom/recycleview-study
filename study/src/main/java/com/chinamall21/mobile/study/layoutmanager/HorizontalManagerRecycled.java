package com.chinamall21.mobile.study.layoutmanager;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * desc：
 * author：Created by xusong on 2018/12/18 17:06.
 */


public class HorizontalManagerRecycled extends RecyclerView.LayoutManager {
    private int mTotalWidth;
    private int mTotalMoveX;
    private SparseArray<Rect> mRectSparseArray = new SparseArray<>();

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
        int visible = getHorizontalSpace() / itemWidth;
        for (int i = 0; i < getItemCount(); i++) {
            mRectSparseArray.append(i, new Rect(temp, 0, itemWidth + temp, itemHeight));
            temp += itemWidth;
        }
        for (int i = 0; i <= visible; i++) {
            Rect rect = mRectSparseArray.get(i);
            View child = recycler.getViewForPosition(i);
            addView(child);
            measureChildWithMargins(child, 0, 0);
            layoutDecorated(child, rect.left, rect.top, rect.right, rect.bottom);
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
        //回收
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View view = getChildAt(i);
            if (getDecoratedRight(view) - travel < 0) {
                removeAndRecycleView(view, recycler);
            } else if (getDecoratedLeft(view) - travel > getHorizontalSpace()) {
                removeAndRecycleView(view, recycler);
            }
        }
        int next = getPosition(getChildAt(getChildCount() - 1)) + 1;
        int last = getPosition(getChildAt(0)) - 1;
        Rect visibleRect = getVisibleRect(travel);
        if (travel > 0) {
            for (int i = next; i < getItemCount(); i++) {
                Rect rect = mRectSparseArray.get(i);
                if (Rect.intersects(visibleRect, rect)) {
                    View child = recycler.getViewForPosition(i);
                    addView(child);
                    measureChildWithMargins(child, 0, 0);
                    layoutDecorated(child, rect.left - mTotalMoveX, rect.top, rect.right - mTotalMoveX, rect.bottom);
                }
            }
        } else {
            for (int i = last; i >= 0; i--) {
                Rect rect = mRectSparseArray.get(i);
                if (Rect.intersects(visibleRect, rect)) {
                    View child = recycler.getViewForPosition(i);
                    addView(child,0);
                    measureChildWithMargins(child, 0, 0);
                    layoutDecorated(child, rect.left - mTotalMoveX, rect.top, rect.right - mTotalMoveX, rect.bottom);
                }
            }
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

    private Rect getVisibleRect(int travel) {
        return new Rect(mTotalMoveX + travel, 0,
                mTotalMoveX + getHorizontalSpace() + travel, getHeight());
    }
}
