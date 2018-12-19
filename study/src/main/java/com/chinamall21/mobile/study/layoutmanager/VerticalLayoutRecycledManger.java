package com.chinamall21.mobile.study.layoutmanager;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * desc：回收的第一种方法
 * author：Created by xusong on 2018/12/14 14:49.
 */


public class VerticalLayoutRecycledManger extends RecyclerView.LayoutManager {
    //存储所有item的位置
    private SparseArray<Rect> mRectArray = new SparseArray<>();
    //总共itemview的高度
    private int mTotalHeight;
    //总共移动的距离
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
        detachAndScrapAttachedViews(recycler);
        View view = recycler.getViewForPosition(0);
        measureChildWithMargins(view, 0, 0);
        int itemWidth = getDecoratedMeasuredWidth(view);
        int itemHeight = getDecoratedMeasuredHeight(view);
        //屏幕上可见的itemView个数
        int visible = getVerticalSpace() / itemHeight;

        int temp = 0;
        for (int i = 0; i < getItemCount(); i++) {
            Rect rect = new Rect(getPaddingLeft(), temp, itemWidth, itemHeight + temp);
            mRectArray.put(i, rect);
            temp += itemHeight;
        }
        //摆放可见的itemview
        for (int i = 0; i < visible; i++) {
            Rect rect = mRectArray.get(i);
            View child = recycler.getViewForPosition(i);
            addView(child);
            measureChildWithMargins(child, 0, 0);
            layoutDecorated(child, rect.left, rect.top, rect.right, rect.bottom);
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
        if (travel + mTotalMoveY < 0) {
            travel = -mTotalMoveY;
        } else if (travel + mTotalMoveY > mTotalHeight - getVerticalSpace()) {
            travel = mTotalHeight - getVerticalSpace() - mTotalMoveY;
        }
        //回收不在屏幕内的itemview
        for (int i = getChildCount()-1; i >=0; i--) {
            View view = getChildAt(i);
            //下滑回收上面移出屏幕的itemview
            if (getDecoratedBottom(view) - travel < 0) {
                removeAndRecycleView(view, recycler);
                //上滑回收下面移出屏幕的itemview
            } else if (getDecoratedTop(view) - travel > getVerticalSpace()) {
                removeAndRecycleView(view, recycler);
            }
        }
        //获取当前可见的屏幕区域
        Rect visibleRect = getVisibleRect(travel);
        //移动时将回收的itemview从缓存中取出来
        if (travel > 0) {
            //上滑即将出来的条目的索引
            int next = getPosition(getChildAt(getChildCount() - 1)) + 1;
            for (int i = next; i < getItemCount(); i++) {
                insertView(recycler, visibleRect, i, false);
            }
        } else {
            //下滑即将出来的条目的索引
            int last = getPosition(getChildAt(0)) - 1;
            for (int i = last; i >= 0; i--) {
                insertView(recycler, visibleRect, i, true);
            }
        }

        mTotalMoveY += travel;
        offsetChildrenVertical(-travel);
        return travel;
    }

    /**
     * @param recycler
     * @param visibleRect
     * @param pos
     * @param flag
     */
    private void insertView(RecyclerView.Recycler recycler, Rect visibleRect, int pos, boolean flag) {
        Rect rect = mRectArray.get(pos);
        if (Rect.intersects(visibleRect, rect)) {
            View child = recycler.getViewForPosition(pos);
            if (flag) {
                addView(child, 0);
            } else {
                addView(child);
            }
            measureChildWithMargins(child, 0, 0);
            layoutDecoratedWithMargins(child, rect.left, rect.top - mTotalMoveY,
                    rect.right, rect.bottom - mTotalMoveY);
        }
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    private int getVerticalSpace() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }

    private Rect getVisibleRect(int travel) {
        return new Rect(getPaddingLeft(), mTotalMoveY + travel,
                getWidth(), mTotalMoveY + travel + getVerticalSpace());
    }


}
