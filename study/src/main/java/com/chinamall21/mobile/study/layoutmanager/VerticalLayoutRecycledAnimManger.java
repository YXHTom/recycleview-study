package com.chinamall21.mobile.study.layoutmanager;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;

/**
 * desc：回收的第二种方法
 * author：Created by xusong on 2018/12/14 14:49.
 */

public class VerticalLayoutRecycledAnimManger extends RecyclerView.LayoutManager {
    //存储所有item的位置
    private SparseArray<Rect> mRectArray = new SparseArray<>();
    //是否在当前屏幕的itemView
    private SparseBooleanArray mAttachItems = new SparseBooleanArray();
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
        mRectArray.clear();
        mAttachItems.clear();

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
            mAttachItems.put(i, false);
            temp += itemHeight;
        }
        //摆放可见的itemview
        for (int i = 0; i <= visible; i++) {
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

        mTotalMoveY += travel;
        //获取当前可见的屏幕区域
        Rect visibleRect = getVisibleRect();
        //回收不在屏幕内的itemview
        for (int i = getChildCount()-1; i >= 0; i--) {
            View view = getChildAt(i);
            int position = getPosition(view);
            Rect rect = mRectArray.get(position);
            if (Rect.intersects(visibleRect, rect)) {
                layoutDecoratedWithMargins(view, rect.left, rect.top - mTotalMoveY, rect.right, rect.bottom - mTotalMoveY);
                mAttachItems.put(position, true);
                view.setRotationY(view.getRotationY() + 1);
            } else {
                removeAndRecycleView(view, recycler);
                mAttachItems.put(position, false);
            }
        }
        int next = getPosition(getChildAt(getChildCount() - 1));
        int last = getPosition(getChildAt(0));
        //移动时将回收的itemview从缓存中取出来
        if (travel > 0) {
            //上滑即将出来的条目的索引
            for (int i = next; i < getItemCount(); i++) {
                insertView(recycler, visibleRect, i, false);
            }
        } else {
            //下滑即将出来的条目的索引
            for (int i = last; i >= 0; i--) {
                insertView(recycler, visibleRect, i, true);
            }
        }
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
        if (Rect.intersects(visibleRect, rect) && !mAttachItems.get(pos)) {
            View child = recycler.getViewForPosition(pos);
            if (flag) {
                addView(child, 0);
            } else {
                addView(child);
            }
            measureChildWithMargins(child, 0, 0);
            layoutDecoratedWithMargins(child, rect.left, rect.top - mTotalMoveY,
                    rect.right, rect.bottom - mTotalMoveY);
            child.setRotationY(child.getRotationY() + 1);
            mAttachItems.put(pos, true);
        }
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    private int getVerticalSpace() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }

    private Rect getVisibleRect() {
        return new Rect(getPaddingLeft(), mTotalMoveY,
                getWidth(), mTotalMoveY + getVerticalSpace());
    }


}
