package com.chinamall21.mobile.study.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamall21.mobile.study.R;

import java.util.List;

/**
 * desc：
 * author：Created by xusong on 2018/11/5 12:03.
 */


public class CommonAdapter extends BaseAdapter<String> {
    private int mResId = R.layout.item_tv_flow;
    private int[] mIcons = {R.mipmap.beauty1, R.mipmap.beauty2, R.mipmap.beauty3,
            R.mipmap.beauty4, R.mipmap.beauty5, R.mipmap.beauty6, R.mipmap.beauty7, R.mipmap.beauty8,
            R.mipmap.beauty9, R.mipmap.beauty10, R.mipmap.beauty11, R.mipmap.beauty12, R.mipmap.beauty13};

    public CommonAdapter(List<String> datas, int resId) {
        super(datas);
        mResId = resId;
    }

    @Override
    protected int getItemLayout() {
        return mResId;
    }

    @Override
    public void convert(final View itemView, final int position) {
        if (mResId == R.layout.item_gallery) {
            TextView tv = itemView.findViewById(R.id.item_title);
            ImageView iv = itemView.findViewById(R.id.item_iv);
            tv.setText(getDatas().get(position));
            iv.setImageResource(mIcons[position % mIcons.length]);
        } else {
            TextView tv = itemView.findViewById(R.id.tv);
            tv.setText(getDatas().get(position));

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "click " + position, Toast.LENGTH_SHORT)
                            .show();
                }
            });
        }


    }


}
