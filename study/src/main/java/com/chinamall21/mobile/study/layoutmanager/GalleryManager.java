package com.chinamall21.mobile.study.layoutmanager;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;

/**
 * desc：
 * author：Created by xusong on 2018/12/18 17:06.
 */


public class GalleryManager extends RecyclerView.LayoutManager {
    private int mTotalWidth;
    private int mTotalMoveX;
    private SparseArray<Rect> mRectSparseArray = new SparseArray<>();
    private SparseBooleanArray mAttachItems = new SparseBooleanArray();
    private int mStartX;
    private int mItemWidth;
    private boolean mInit;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() == 0 || mInit) {
            return;
        }
        detachAndScrapAttachedViews(recycler);
        mRectSparseArray.clear();
        mAttachItems.clear();
        View view = recycler.getViewForPosition(0);
        measureChildWithMargins(view, 0, 0);
        mItemWidth = getDecoratedMeasuredWidth(view);
        int itemHeight = getDecoratedMeasuredHeight(view);
        int temp = 0;
        int visible = getHorizontalSpace() / (mItemWidth / 2);
        mStartX = getHorizontalSpace() / 2 - mItemWidth / 2;

        for (int i = 0; i < getItemCount(); i++) {
            mRectSparseArray.append(i, new Rect(temp + mStartX, 0, mItemWidth + temp + mStartX, itemHeight));
            mAttachItems.put(i, false);
            temp += mItemWidth / 2;
        }
        for (int i = 0; i <= visible; i++) {
            Rect rect = mRectSparseArray.get(i);
            View child = recycler.getViewForPosition(i);
            addView(child);
            measureChildWithMargins(child, 0, 0);
            layoutDecorated(child, rect.left, rect.top, rect.right, rect.bottom);
        }
        mTotalWidth = Math.max(temp + mStartX, getHorizontalSpace());
        mInit = true;
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
        } else if (travel + mTotalMoveX > getMaxOffset() ) {
            travel = getMaxOffset() - mTotalMoveX;
        }
        mTotalMoveX += travel;
        Rect visibleRect = getVisibleRect();
        //回收
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View view = getChildAt(i);
            int position = getPosition(view);
            Rect rect = mRectSparseArray.get(position);
            if (Rect.intersects(visibleRect, rect)) {
                layoutDecorated(view, rect.left - mTotalMoveX, rect.top,
                        rect.right - mTotalMoveX, rect.bottom);
                handleChildView(view, rect.left - mStartX - mTotalMoveX);
                mAttachItems.put(position, true);
            } else {
                removeAndRecycleView(view, recycler);
                mAttachItems.put(position, false);
            }
        }

        int next = getPosition(getChildAt(getChildCount() - 1));
        int last = getPosition(getChildAt(0));

        if (travel > 0) {
            for (int i = next; i < getItemCount(); i++) {
                Rect rect = mRectSparseArray.get(i);
                if (Rect.intersects(visibleRect, rect) && !mAttachItems.get(i)) {
                    View child = recycler.getViewForPosition(i);
                    addView(child);
                    measureChildWithMargins(child, 0, 0);
                    layoutDecorated(child, rect.left - mTotalMoveX, rect.top, rect.right - mTotalMoveX, rect.bottom);
                    mAttachItems.put(i, true);
                    handleChildView(child, rect.left - mStartX - mTotalMoveX);
                }
            }
        } else {
            for (int i = last; i >= 0; i--) {
                Rect rect = mRectSparseArray.get(i);
                if (Rect.intersects(visibleRect, rect) && !mAttachItems.get(i)) {
                    View child = recycler.getViewForPosition(i);
                    addView(child, 0);
                    measureChildWithMargins(child, 0, 0);
                    layoutDecorated(child, rect.left - mTotalMoveX, rect.top, rect.right - mTotalMoveX, rect.bottom);
                    mAttachItems.put(i, true);
                    handleChildView(child, rect.left - mStartX - mTotalMoveX);
                }
            }
        }
        return travel;
    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    private int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    private Rect getVisibleRect() {
        return new Rect(mTotalMoveX, 0,
                mTotalMoveX + getHorizontalSpace(), getHeight());
    }


    public int getCenterPosition() {
        int pos = (mTotalMoveX / getIntervalWidth());
        int more = (mTotalMoveX % getIntervalWidth());
        if (more > getIntervalWidth() * 0.5f) pos++;
        return pos;
    }

    public int getFirstVisiblePos() {
        if (getItemCount() == 0) {
            return 0;
        }
        View view = getChildAt(0);
        return getPosition(view);
    }

    private int getIntervalWidth() {
        return mItemWidth / 2;
    }

    private void handleChildView(View child, int moveX) {
        float radio = computeScale(moveX);

        child.setScaleX(radio);
        child.setScaleY(radio);
    }

    private float computeScale(int x) {
        float scale = 1 - Math.abs(x * 1.0f / (5f * getIntervalWidth()));
        if (scale < 0) scale = 0;
        if (scale > 1) scale = 1;
        return scale;
    }

    private int getMaxOffset() {
        return (getItemCount() - 1) * getIntervalWidth();
    }
}
