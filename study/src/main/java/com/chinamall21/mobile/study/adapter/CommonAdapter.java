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

    public CommonAdapter(List<String> datas) {
        super(datas);
    }

    @Override
    protected int getItemLayout() {
        return R.layout.item_tv;
    }

    @Override
    public void convert(View itemView, int position) {
        ((TextView)itemView).setText(getDatas().get(position));
    }


}
