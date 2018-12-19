package com.chinamall21.mobile.study.layoutmanager;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * desc：流失布局
 * author：Created by xusong on 2018/12/14 14:49.
 */
public class FlowLayoutMangerRecycled extends RecyclerView.LayoutManager {
    //总共itemview的高度
    private int mTotalHeight;
    private int mTotalMoveY;
    //默认margin值
    private final int mDefaultMargin = 15;
    private SparseArray<Rect> mRectArrays = new SparseArray<>();

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
        View v = recycler.getViewForPosition(0);
        measureChildWithMargins(v, 0, 0);
        int itemHeight = getDecoratedMeasuredHeight(v);
        //当前屏幕可见条数
        int visible = 0;
        for (int i = 0; i < getItemCount(); i++) {
            View view = recycler.getViewForPosition(i);
            measureChildWithMargins(view, 0, 0);
            int itemWidth = getDecoratedMeasuredWidth(view);

            //超出屏幕宽度换行
            if (tempWidth + itemWidth >= getHorizontalSpace()) {
                tempWidth = mDefaultMargin;
                tempHeight += itemHeight + mDefaultMargin;
            }
            mRectArrays.put(i, new Rect(tempWidth, tempHeight, itemWidth + tempWidth, tempHeight + itemHeight));
            tempWidth += itemWidth + mDefaultMargin;
            if (tempHeight > getVerticalSpace() && visible == 0) {
                visible = i;
            }
        }

        for (int i = 0; i < visible; i++) {
            View child = recycler.getViewForPosition(i);
            Rect rect = mRectArrays.get(i);
            addView(child);
            measureChildWithMargins(child, 0, 0);
            layoutDecorated(child, rect.left, rect.top, rect.right, rect.bottom);
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

        for (int i = getChildCount() - 1; i >= 0; i--) {
            View view = getChildAt(i);
            if (getDecoratedBottom(view) - travel < 0) {
                removeAndRecycleView(view, recycler);
            } else if (getDecoratedTop(view) - travel > getVerticalSpace()) {
                removeAndRecycleView(view, recycler);
            }
        }
        Rect visibleRect = getVisibleRect(travel);
        int next = getPosition(getChildAt(getChildCount() - 1)) + 1;
        int last = getPosition(getChildAt(0)) - 1;
        if (travel > 0) {
            for (int i = next; i < getItemCount(); i++) {
                Rect rect = mRectArrays.get(i);
                if (Rect.intersects(visibleRect, rect)) {
                    View child = recycler.getViewForPosition(i);
                    addView(child);
                    measureChildWithMargins(child, 0, 0);
                    layoutDecoratedWithMargins(child, rect.left, rect.top - mTotalMoveY, rect.right, rect.bottom - mTotalMoveY);
                }
            }
        } else {
            for (int i = last; i >= 0; i--) {
                Rect rect = mRectArrays.get(i);
                if (Rect.intersects(visibleRect, rect)) {
                    View child = recycler.getViewForPosition(i);
                    addView(child,0);
                    measureChildWithMargins(child, 0, 0);
                    layoutDecoratedWithMargins(child, rect.left, rect.top - mTotalMoveY, rect.right, rect.bottom - mTotalMoveY);
                }
            }
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

    private Rect getVisibleRect(int travel) {
        return new Rect(0, mTotalMoveY + travel,
                getWidth(), mTotalMoveY + travel + getVerticalSpace());
    }
}
