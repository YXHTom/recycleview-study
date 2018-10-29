package com.chinamall21.mobile.study.itemdecoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

/**
 * desc：
 * author：Created by xusong on 2018/9/29 12:15.
 */


public class StickyItemDecoration extends RecyclerView.ItemDecoration {

    private int decorationHeight = 100;
    private OnTagListener onTagListener;
    private final Paint bgPaint;
    private final Paint textPaint;

    public StickyItemDecoration(OnTagListener onTagListener) {
        this.onTagListener = onTagListener;
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(Color.parseColor("#3F51B5"));
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.parseColor("#ffffff"));
        textPaint.setTextSize(45);
    }


    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int itemCount = state.getItemCount();
        int childCount = parent.getChildCount();
        int left = parent.getLeft() + parent.getPaddingLeft();
        int right = parent.getRight() + parent.getPaddingRight();
        int viewLeft;
        String currentTag = null;
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            viewLeft = childView.getLeft() + childView.getPaddingLeft();
            int position = parent.getChildAdapterPosition(childView);
            if (TextUtils.equals(currentTag, onTagListener.getGroupName(position))) {
                continue;
            }
            currentTag = onTagListener.getGroupName(position);
            int viewBottom = childView.getBottom();
            int top = Math.max(decorationHeight, childView.getTop());
            if (position + 1 < itemCount) {
                if (!TextUtils.equals(currentTag, onTagListener.getGroupName(position + 1)) && viewBottom < top) {
                    top = viewBottom;
                }
            }
            c.drawRect(left, top - decorationHeight, right, top, bgPaint);
            Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
            float baseLine = top - (decorationHeight - (fontMetrics.bottom - fontMetrics.top)) / 2 - fontMetrics.bottom;
            c.drawText(currentTag.toUpperCase(), viewLeft, baseLine, textPaint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        if (position == 0 || onTagListener.isGroupFirst(position)) {
            outRect.top = decorationHeight;
        }
    }

    public interface OnTagListener {
        String getGroupName(int position);

        boolean isGroupFirst(int position);
    }
}



