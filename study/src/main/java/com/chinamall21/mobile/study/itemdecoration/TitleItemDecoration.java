package com.chinamall21.mobile.study.itemdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chinamall21.mobile.study.R;
import com.chinamall21.mobile.study.bean.DataBean;
import com.chinamall21.mobile.study.utils.LogUtils;
import com.chinamall21.mobile.study.utils.SizeUtils;

import java.util.List;

/**
 * desc：
 * author：Created by xusong on 2018/9/17 12:59.
 */


public class TitleItemDecoration extends RecyclerView.ItemDecoration {

    private Paint mPaint;
    private List<DataBean.DatasBean> mDatas;
    private int mTitleHeight;
    private int mTitleFontSize;
    private Context mContext;
    private int mPosition;


    public TitleItemDecoration(List<DataBean.DatasBean> list, Context context) {
        mDatas = list;
        mContext = context;
        mTitleHeight = SizeUtils.dp2px(context, 50);
        mTitleFontSize = SizeUtils.sp2px(context, 16);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(mTitleFontSize);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = (parent.getLayoutManager()).getPosition(view);

        if (position == 0) {
            outRect.set(0, mTitleHeight, 0, 0);
        } else if (position != -1 && mDatas.get(position).getPinyin().charAt(0) !=
                mDatas.get(position - 1).getPinyin().charAt(0)) {
            outRect.set(0, mTitleHeight, 0, 0);
        } else {
            outRect.set(0, 0, 0, 0);
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            mPaint.setColor(mContext.getResources().getColor(R.color.gray));
            View child = parent.getChildAt(i);
            int position = (parent.getLayoutManager()).getPosition(child);
            int right = parent.getMeasuredWidth();
            int top = child.getBottom();
            int bottom = top + 5;
            c.drawRect(0, top, right, bottom, mPaint);

            if (position == 0 || mDatas.get(position).getPinyin().charAt(0) !=
                    mDatas.get(position - 1).getPinyin().charAt(0)) {
                String text = mDatas.get(position).getPinyin().charAt(0) + "";
                //画背景
                c.drawRect(0, child.getTop() - mTitleHeight, parent.getMeasuredWidth(), child.getTop(), mPaint);
                //画字体
                mPaint.setColor(Color.WHITE);

                Rect rect = new Rect();
                mPaint.getTextBounds(text, 0, text.length(), rect);
                c.drawText(text, SizeUtils.dp2px(mContext, 10), child.getTop() - (mTitleHeight / 2 - rect.height() / 2), mPaint);
            }

        }

    }


    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int firstPosition = ((LinearLayoutManager) parent.getLayoutManager()).findFirstVisibleItemPosition();

        mPaint.setColor(mContext.getResources().getColor(R.color.gray));
        String text = mDatas.get(firstPosition).getPinyin().charAt(0) + "";
        View child = parent.findViewHolderForLayoutPosition(firstPosition).itemView;
        if (firstPosition == 0 || mDatas.get(firstPosition).getPinyin().charAt(0) !=
                mDatas.get(firstPosition + 1).getPinyin().charAt(0)) {

            LogUtils.LogE("child.getTop() = "+child.getTop());

            if (child.getHeight() + child.getTop() < mTitleHeight) {

                c.translate(0, child.getHeight() + child.getTop() - mTitleHeight);
            }
        }
        //画背景
        c.drawRect(0, 0, parent.getMeasuredWidth(), mTitleHeight, mPaint);
        //画字体
        mPaint.setColor(Color.WHITE);

        Rect rect = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), rect);

        c.drawText(text, SizeUtils.dp2px(mContext, 10), mTitleHeight / 2 + rect.height() / 2, mPaint);


    }

}
