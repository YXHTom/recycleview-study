package com.chinamall21.mobile.study.itemdecoration;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chinamall21.mobile.study.bean.DataBean;
import com.chinamall21.mobile.study.utils.LogUtils;
import com.chinamall21.mobile.study.utils.SizeUtils;

import java.util.List;

/**
 * desc：Nothing is difficult if you put your heart into it.
 * author：Created by xusong on 2018/9/29 14:24.
 */


public class GoodsDecoration extends RecyclerView.ItemDecoration {

    private List<DataBean.DatasBean> mCitys;
    private Activity mContext;
    private Paint mBgPaint;//画背景
    private Paint mTextPaint;//画文字
    private int mTitleHeight;//Title的高度
    private final Paint mPaint;

    public GoodsDecoration(List<DataBean.DatasBean> citys, Activity context) {
        mCitys = citys;
        mContext = context;
        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setColor(Color.GRAY);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(SizeUtils.sp2px(mContext, 20));

        mTitleHeight = SizeUtils.dp2px(mContext, 40);

        mPaint = new Paint();
        mPaint.setColor(Color.GRAY);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = (parent.getLayoutManager()).getPosition(view);
        if (mCitys.get(position).getLabel() != null) {
            //当前条目和上一个条目的第一个拼音不同时需要Title
            outRect.set(0, mTitleHeight, 0, 0);
        }
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            int position = (parent.getLayoutManager()).getPosition(child);

            if ( mCitys.get(position).getLabel() != null) {

                //画背景
                c.drawRect(0, child.getTop() - mTitleHeight, parent.getRight(), child.getTop(), mBgPaint);
                String c1 = String.valueOf(mCitys.get(position).getPinyin());
                Rect rect = new Rect();
                mTextPaint.getTextBounds(c1, 0, 1, rect);
                //画文字
                c.drawText(c1, 30, child.getTop() - (mTitleHeight / 2 - rect.height() / 2), mTextPaint);
            }
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        //第一个可见条目的位置
        int position = ((LinearLayoutManager) parent.getLayoutManager()).findFirstVisibleItemPosition();
        View firstChild = parent.getLayoutManager().findViewByPosition(position);
        View secondChild = parent.getLayoutManager().findViewByPosition(position + 1);
        if (secondChild.getTop() - firstChild.getTop() > firstChild.getHeight() * 2) {
            LogUtils.LogE("zxlm");
            //当第二个title和第一个title重合时移动画板,产生动画效果
            c.translate(0, firstChild.getTop());
        }

        //画背景
        c.drawRect(0, 0, parent.getRight(), mTitleHeight, mBgPaint);
        String c1 = String.valueOf(mCitys.get(position).getPinyin());
        Rect rect = new Rect();
        mTextPaint.getTextBounds(c1, 0, 1, rect);
        //画文字
        c.drawText(c1, 30, mTitleHeight / 2 + rect.height() / 2, mTextPaint);

    }

}



