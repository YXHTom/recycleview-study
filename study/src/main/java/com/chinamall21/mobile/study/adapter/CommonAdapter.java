package com.chinamall21.mobile.study.adapter;

import android.view.View;
import android.widget.TextView;

import com.chinamall21.mobile.study.R;

import java.util.List;

/**
 * desc：
 * author：Created by xusong on 2018/11/5 12:03.
 */


public class CommonAdapter extends BaseAdapter<String> {
    private int mResId = R.layout.item_tv_flow;

    public CommonAdapter(List<String> datas,int resId) {
        super(datas);
        mResId = resId;
    }

    @Override
    protected int getItemLayout() {
        return mResId;
    }

    @Override
    public void convert(View itemView, int position) {
        ((TextView)itemView).setText(getDatas().get(position));
    }


}
