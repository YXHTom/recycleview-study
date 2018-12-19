package com.chinamall21.mobile.study.layoutmanager;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;

/**
 * desc：流失布局
 * author：Created by xusong on 2018/12/14 14:49.
 */
public class FlowLayoutMangerRecycledAnim extends RecyclerView.LayoutManager {
    //总共itemview的高度
    private int mTotalHeight;
    private int mTotalMoveY;
    //默认margin值
    private final int mDefaultMargin = 15;
    private SparseArray<Rect> mRectArrays = new SparseArray<>();
    private SparseBooleanArray mAttachArrays = new SparseBooleanArray();

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
        mRectArrays.clear();
        mAttachArrays.clear();
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
            mAttachArrays.put(i, false);
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
        if (getChildCount() <= 0) {
            return dy;
        }
        int travel = dy;
        //如果滑动到最顶部
        if (mTotalMoveY + dy < 0) {
            travel = -mTotalMoveY;
        } else if (mTotalMoveY + dy > mTotalHeight - getVerticalSpace()) {
            //如果滑动到最底部
            travel = mTotalHeight - getVerticalSpace() - mTotalMoveY;
        }

        mTotalMoveY += travel;

        Rect visibleRect = getVisibleArea();

        //回收越界子View
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View child = getChildAt(i);
            int position = getPosition(child);
            Rect rect = mRectArrays.get(position);

            if (!Rect.intersects(rect, visibleRect)) {
                removeAndRecycleView(child, recycler);
                mAttachArrays.put(position, false);
            } else {
                layoutDecoratedWithMargins(child, rect.left, rect.top - mTotalMoveY, rect.right, rect.bottom - mTotalMoveY);
                child.setRotationY(child.getRotationY() + 1);
                mAttachArrays.put(position, true);
            }
        }

        View lastView = getChildAt(getChildCount() - 1);
        View firstView = getChildAt(0);
        if (travel >= 0) {
            int minPos = getPosition(firstView);
            for (int i = minPos; i < getItemCount(); i++) {
                insertView(i, visibleRect, recycler, false);
            }
        } else {
            int maxPos = getPosition(lastView);
            for (int i = maxPos; i >= 0; i--) {
                insertView(i, visibleRect, recycler, true);
            }
        }
        return travel;
    }

    private void insertView(int pos, Rect visibleRect, RecyclerView.Recycler recycler, boolean firstPos) {
        Rect rect = mRectArrays.get(pos);
        if (Rect.intersects(visibleRect, rect) && !mAttachArrays.get(pos)) {
            View child = recycler.getViewForPosition(pos);
            if (firstPos) {
                addView(child, 0);
            } else {
                addView(child);
            }
            measureChildWithMargins(child, 0, 0);
            layoutDecoratedWithMargins(child, rect.left, rect.top - mTotalMoveY, rect.right, rect.bottom - mTotalMoveY);

            //在布局item后，修改每个item的旋转度数
            child.setRotationY(child.getRotationY() + 1);
            mAttachArrays.put(pos, true);
        }
    }

    /**
     * 获取可见的区域Rect
     *
     * @return
     */
    private Rect getVisibleArea() {
        Rect result = new Rect(getPaddingLeft(), getPaddingTop() + mTotalMoveY, getWidth() + getPaddingRight(), getVerticalSpace() + mTotalMoveY);
        return result;
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
