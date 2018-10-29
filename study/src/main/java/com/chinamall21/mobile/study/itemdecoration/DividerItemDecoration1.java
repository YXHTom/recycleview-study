package com.chinamall21.mobile.study.itemdecoration;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chinamall21.mobile.study.R;

/**
 * desc：
 * author：Created by xusong on 2018/9/14 15:54.
 */


public class DividerItemDecoration1 extends RecyclerView.ItemDecoration {
    private Paint mPaint;
    private Bitmap mBitmap;

    public DividerItemDecoration1(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.GREEN);
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.eleme);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, 10);
    }

//    @Override
//    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        super.onDraw(c, parent, state);
//        int childCount = parent.getChildCount();
//
//        for (int i = 0; i < childCount; i++) {
//            View child = parent.getChildAt(i);
//            int left = 0;
//            int right = parent.getMeasuredWidth();
//            int top = child.getBottom();
//            int bottom = top + 10;
//            c.drawRect(left, top, right, bottom, mPaint);
//        }
//    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        // 获取Item的总数
        int childCount = parent.getChildCount();
        // 遍历Item
        for ( int i = 0; i < childCount; i++ ) {
//            // 获取每个Item的位置
//            View view = parent.getChildAt(i);
//            int index = parent.getChildAdapterPosition(view);
//
//            // 设置绘制内容的坐标(ItemView的左边界,ItemView的上边界)
//            // ItemView的左边界 = RecyclerView 的左边界 = paddingLeft距离 后的位置
//            final int left = parent.getWidth()/2;
//            // ItemView的上边界
//            float top = view.getTop();
//
//            // 第1个ItemView不绘制
//            if ( index == 0 ) {
//                continue;
//            }
//            // 通过Canvas绘制角标
//            c.drawBitmap(mBitmap,left,top,mPaint);

            View child = parent.getChildAt(i);
            int left = 0;
            int right = parent.getMeasuredWidth();
            int top = child.getTop();
            int bottom = top + 10;
            c.drawRect(left, top, right, bottom, mPaint);
        }


    }
}
