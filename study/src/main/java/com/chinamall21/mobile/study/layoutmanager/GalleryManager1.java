package com.chinamall21.mobile.study.layoutmanager;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;

/**
 * desc：
 * author：Created by xusong on 2019/1/28 12:38.
 */


public class GalleryManager1 extends RecyclerView.LayoutManager {

    private SparseArray<Rect> mSparseArray = new SparseArray<>();
    private SparseBooleanArray mBooleanArray = new SparseBooleanArray();
    private int mItemWidth;
    //条目开始展示的位置
    private int mStartX;
    private int mTotalMoveX;
    private boolean hasLayoutChild;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() == 0 || hasLayoutChild) {
            return;
        }
        detachAndScrapAttachedViews(recycler);
        View view = recycler.getViewForPosition(0);
        measureChildWithMargins(view, 0, 0);
        mItemWidth = getDecoratedMeasuredWidth(view);
        int itemHeight = getDecoratedMeasuredHeight(view);
        int temp = 0;
        mStartX = getWidth()/2 - getItemShowWidth();

        for (int i = 0; i < getItemCount(); i++) {
            Rect rect = new Rect(mStartX + temp, 0, mStartX + temp + mItemWidth, itemHeight);
            mSparseArray.put(i, rect);
            mBooleanArray.put(i, false);
            temp += getItemShowWidth();
        }

        int visible = getWidth() / getItemShowWidth();

        for (int i = 0; i < visible; i++) {
            Rect rect = mSparseArray.get(i);
            View child = recycler.getViewForPosition(i);
            addView(child);
            measureChildWithMargins(child, 0, 0);
            layoutDecoratedWithMargins(child, rect.left, rect.top, rect.right, rect.bottom);
        }
        hasLayoutChild = true;
    }

    //dx>0👈 dx<0👉
    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() == 0) {
            return dx;
        }
        int travel = dx;
        if (mTotalMoveX + travel < 0) {
            travel = -mTotalMoveX;
        } else if (mTotalMoveX + travel > getTotalWidth()) {
            travel = getTotalWidth() - mTotalMoveX;
        }
        mTotalMoveX += travel;
        Rect visibleArea = getVisibleArea();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int position = getPosition(child);
            Rect rect = mSparseArray.get(position);
            if (Rect.intersects(visibleArea, rect)) {
                layoutDecorated(child, rect.left - mTotalMoveX, rect.top,
                        rect.right - mTotalMoveX, rect.bottom);
                mBooleanArray.put(position, true);
                handleChildView(child,rect.left - mStartX - mTotalMoveX);
            } else {
                removeAndRecycleView(child, recycler);
                mBooleanArray.put(position, false);
            }
        }
        int nextPos = getPosition(getChildAt(getChildCount() - 1)) + 1;
        int lastPos = getPosition(getChildAt(0)) - 1;

        if (travel > 0) {
            for (int i = nextPos; i < getItemCount(); i++) {
                Rect rect = mSparseArray.get(i);
                if (Rect.intersects(visibleArea, rect) && !mBooleanArray.get(i)) {
                    insertView(recycler, i, false);
                }
            }
        } else {
            for (int i = lastPos; i >= 0; i--) {
                Rect rect = mSparseArray.get(i);
                if (Rect.intersects(visibleArea, rect) && !mBooleanArray.get(i)) {
                    insertView(recycler, i, true);
                }
            }
        }
        return travel;
    }


    private void insertView(RecyclerView.Recycler recycler, int i, boolean flag) {
        View child = recycler.getViewForPosition(i);
        Rect rect = mSparseArray.get(i);
        if (flag) {
            addView(child, 0);
        } else {
            addView(child);
        }
        measureChildWithMargins(child, 0, 0);
        mBooleanArray.put(i, true);
        handleChildView(child,rect.left - mStartX - mTotalMoveX);
        layoutDecoratedWithMargins(child, rect.left, rect.top, rect.right, rect.bottom);
    }


    public int getCenterPos() {
        int pos = (mTotalMoveX / getItemShowWidth());
        int more = (mTotalMoveX % getItemShowWidth());
        if (more > getItemShowWidth() * 0.5f) pos++;
        return pos;

    }

    public int getFistvisiblePos() {
        if(getItemCount()==0){
            return 0;
        }
        return getPosition(getChildAt(0));
    }

    private void handleChildView(View child, int moveX) {
        float radio = computeScale(moveX);
        float rotation = computeRotationY(moveX);
        child.setScaleX(radio);
        child.setScaleY(radio);
        child.setRotationY(rotation);
    }

    private float computeScale(int x) {
        float scale = 1 - Math.abs(x * 1.0f / (6f * getItemShowWidth()));

        if (scale < 0) scale = 0;
        if (scale > 1) scale = 1;
        return scale;
    }
    /**
     * 最大Y轴旋转度数
     */
    private float M_MAX_ROTATION_Y = 30.0f;
    private float computeRotationY(int x) {
        float rotationY;
        rotationY = -M_MAX_ROTATION_Y * x / getItemShowWidth();
        if (Math.abs(rotationY) > M_MAX_ROTATION_Y) {
            if (rotationY > 0) {
                rotationY = M_MAX_ROTATION_Y;
            } else {
                rotationY = -M_MAX_ROTATION_Y;
            }
        }
        return rotationY;
    }

    public double calculateDistance(int velocityX,double distance) {
        int extra = mTotalMoveX % getItemShowWidth();
        double realDistance;
        if (distance < getItemShowWidth()) {
            realDistance = getItemShowWidth() - extra;
        }else {
            realDistance = distance - distance % getItemShowWidth() - extra;
        }
        return realDistance;
    }



    /**
     * 获取可见屏幕区域
     *
     * @return
     */
    private Rect getVisibleArea() {
        return new Rect(mTotalMoveX, 0, mTotalMoveX + getWidth(), getHeight());
    }

    private int getTotalWidth() {
        return (getItemCount() - 1) * getItemShowWidth();
    }

    private int getItemShowWidth() {
        return mItemWidth / 2;
    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

}
