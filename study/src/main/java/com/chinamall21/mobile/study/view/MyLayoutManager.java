package com.chinamall21.mobile.study.view;


import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;

/**
 * desc：
 * author：Created by xusong on 2018/11/14 15:28.
 */

public class MyLayoutManager extends RecyclerView.LayoutManager {

    private SparseArray<Rect> mSparseArray = new SparseArray<>();
    private SparseBooleanArray mHasAttachArray = new SparseBooleanArray();
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
        mHasAttachArray.clear();
        mSparseArray.clear();

        detachAndScrapAttachedViews(recycler);
        int temp = 0;
        View view = recycler.getViewForPosition(0);
        measureChildWithMargins(view, 0, 0);
        int itemWidth = getDecoratedMeasuredWidth(view);
        int itemHeight = getDecoratedMeasuredHeight(view);

        int visibleCount = getVerticalSpace() / itemHeight;

        for (int i = 0; i < getItemCount(); i++) {
            Rect rect = new Rect(getPaddingLeft(), temp, itemWidth, temp + itemHeight);
            mSparseArray.put(i, rect);
            mHasAttachArray.put(i, false);
            temp += itemHeight;
        }
        for (int i = 0; i < visibleCount; i++) {
            View child = recycler.getViewForPosition(i);
            addView(child);
            measureChildWithMargins(child, 0, 0);
            Rect rect = mSparseArray.get(i);
            layoutDecoratedWithMargins(child, rect.left, rect.top, rect.right, rect.bottom);
        }
        mTotalHeight = Math.max(temp, getVerticalSpace());

    }

    //👆>0 👇<0
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getChildCount() <= 0) {
            return dy;
        }
        int travel = dy;
        //限定拖动范围
        if (mTotalMoveY + dy < 0) {
            travel = -mTotalMoveY;
        } else if (mTotalMoveY + dy > mTotalHeight - getVerticalSpace()) {
            //如果滑动到最底部
            travel = mTotalHeight - getVerticalSpace() - mTotalMoveY;
        }
        mTotalMoveY += travel;
        Rect getvisibleRect = getvisibleRect();

        //回收itemview
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int position = getPosition(child);
            Rect rect = mSparseArray.get(position);
            if (Rect.intersects(getvisibleRect, rect)) {
                layoutDecoratedWithMargins(child, rect.left, rect.top - mTotalMoveY,
                        rect.right, rect.bottom - mTotalMoveY);
                child.setRotationX(child.getRotationX() + 1);
                mHasAttachArray.put(position, true);
            } else {
                removeAndRecycleView(child, recycler);
                mHasAttachArray.put(position, false);
            }
//            if (travel > 0 && getDecoratedBottom(view) - travel < 0) {
//                removeAndRecycleView(view, recycler);
//            } else if (travel < 0 && getDecoratedTop(view) - travel > getVerticalSpace()) {
//                removeAndRecycleView(view, recycler);
//            }
        }

        //recycler中取缓存的itemview

        int first = getPosition(getChildAt(0));
        int last = getPosition(getChildAt(getChildCount() - 1));
        //detachAndScrapAttachedViews(recycler);

        if (travel > 0) {
            for (int i = first; i < getItemCount(); i++) {
                insertView(getvisibleRect, i, recycler, false);
            }
        } else {
            for (int i = last; i >= 0; i--) {
                insertView(getvisibleRect, i, recycler, true);
            }
        }


//        if (travel > 0) {
//            int next = getPosition(getChildAt(getChildCount() - 1)) + 1;
//            for (int i = next; i < getItemCount(); i++) {
//                Rect rect = mSparseArray.get(i);
//
//                if (Rect.intersects(getvisibleRect, rect)) {
//                    View child = recycler.getViewForPosition(i);
//                    addView(child);
//                    measureChildWithMargins(child, 0, 0);
//                    layoutDecoratedWithMargins(child, rect.left,
//                            rect.top - mTotalMoveY, rect.right, rect.bottom - mTotalMoveY);
//                }
//
//            }
//        }else {
//            int last = getPosition(getChildAt(0)) - 1;
//            for (int i = last; i >= 0; i--) {
//                Rect rect = mSparseArray.get(i);
//
//                if (Rect.intersects(getvisibleRect, rect)) {
//                    View child = recycler.getViewForPosition(i);
//                    addView(child,0);
//                    measureChildWithMargins(child, 0, 0);
//                    layoutDecoratedWithMargins(child, rect.left,
//                            rect.top - mTotalMoveY, rect.right, rect.bottom - mTotalMoveY);
//                }
//            }
//        }
//
//        mTotalMoveY += travel;
//        offsetChildrenVertical(-travel);

        return travel;
    }

    private void insertView(Rect visibleRect, int pos, RecyclerView.Recycler recycler, boolean flag) {
        Rect rect = mSparseArray.get(pos);
        if (Rect.intersects(visibleRect, rect) && !mHasAttachArray.get(pos)) {
            View child = recycler.getViewForPosition(pos);
            if (flag) {
                addView(child, 0);
            } else {
                addView(child);
            }

            measureChildWithMargins(child, 0, 0);
            layoutDecoratedWithMargins(child, rect.left, rect.top - mTotalMoveY, rect.right, rect.bottom - mTotalMoveY);
            child.setRotationX(child.getRotationX() + 1);
            mHasAttachArray.put(pos,true);
        }
    }

    //获取当前屏幕可见区域
    private Rect getvisibleRect(int travel) {

        return new Rect(0, mTotalMoveY + travel, getWidth(),
                mTotalMoveY + getVerticalSpace() + travel);
    }

    //获取当前屏幕可见区域
    private Rect getvisibleRect() {

        return new Rect(0, mTotalMoveY, getWidth(),
                mTotalMoveY + getVerticalSpace());
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    private int getVerticalSpace() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }
}
