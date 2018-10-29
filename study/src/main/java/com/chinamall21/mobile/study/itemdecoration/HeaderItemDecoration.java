package com.chinamall21.mobile.study.itemdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chinamall21.mobile.study.R;
import com.chinamall21.mobile.study.bean.DataBean;
import com.chinamall21.mobile.study.utils.SizeUtils;

import java.util.List;

/**
 * desc：
 * author：Created by xusong on 2018/10/18 17:25.
 */


public class HeaderItemDecoration extends RecyclerView.ItemDecoration {

    private List<DataBean.DatasBean> mDatas;
    private LayoutInflater mLayoutInflater;
    private int mHeaderHeight;
    private int mPosition;
    private String mLastHeader;

    public HeaderItemDecoration(Context context, List<DataBean.DatasBean> datas,OnHeaderChangedListener headerChangedListener) {
        mDatas = datas;
        mLayoutInflater = LayoutInflater.from(context);
        mHeaderHeight = SizeUtils.dp2px(context, 60);
        mHeaderChangedListener = headerChangedListener;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        TextView topTitleView = (TextView) mLayoutInflater.inflate(R.layout.item_header, parent, false);
        int position = layoutManager.findFirstVisibleItemPosition();

        if (mDatas.get(position).getLabel() != null) {
            mPosition = position;
        }

        String header = mDatas.get(position).getPinyin();

        if (mLastHeader != null && !mLastHeader.equals(header) && mHeaderChangedListener != null) {
            //头部title改变的监听
            mHeaderChangedListener.onHeaderChange(header);
        }
        topTitleView.setText(header);
        mLastHeader = header;
        if (position != 0 && mDatas.get(position + 1).getLabel() != null || mDatas.get(position + 2).getLabel() != null || mDatas.get(position + 3).getLabel() != null) {

            int diffy = layoutManager.findViewByPosition(position).getTop();
            c.translate(0, diffy);
        }

        //依次调用 measure,layout,draw方法，将复杂头部显示在屏幕上
        topTitleView.measure(View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(mHeaderHeight, View.MeasureSpec.EXACTLY));
        topTitleView.layout(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getPaddingLeft() + topTitleView.getMeasuredWidth(), parent.getPaddingTop() + topTitleView.getMeasuredHeight());
        topTitleView.draw(c);

        //绘制title结束
//        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
//        int position = layoutManager.findFirstVisibleItemPosition();
//        if (position != 0 && (position + 1 < mDatas.size() && mDatas.get(position + 1).isTitle())) {
//
//            int diffy = layoutManager.findViewByPosition(position).getTop();
//            c.translate(0,diffy);
//        }
//
//        c.drawRect(0, 0, parent.getRight(), mHeaderHeight, mPaint);
//        String text = mDatas.get(position).getPinyin().charAt(0) + "";
//
//        Rect rect = new Rect();
//        mTextPaint.getTextBounds(text, 0, text.length(), rect);
//        c.drawText(text, parent.getWidth() / 2,
//                mHeaderHeight / 2 + rect.height() / 2, mTextPaint);

    }

    public interface OnHeaderChangedListener {
        void onHeaderChange(String title);
    }

    private OnHeaderChangedListener mHeaderChangedListener;

}
