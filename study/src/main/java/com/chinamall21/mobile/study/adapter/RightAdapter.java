package com.chinamall21.mobile.study.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chinamall21.mobile.study.R;
import com.chinamall21.mobile.study.bean.DataBean;
import com.chinamall21.mobile.study.utils.LogUtils;

import java.util.List;

/**
 * desc：
 * author：Created by xusong on 2018/10/17 15:10.
 */


public class RightAdapter extends RecyclerView.Adapter {

    private List<DataBean.DatasBean> mDatas;
    private Context mContext;
    private static final int TITLE = 1;
    private static final int CONTENT = 2;

    public RightAdapter(List<DataBean.DatasBean> datas, Context context) {
        mDatas = datas;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == CONTENT) {
            TextView textView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_goods, parent, false);

            MyHolder holder = new MyHolder(textView);
            return holder;
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_header, parent, false);
            TitleHolder holder = new TitleHolder(view);
            return holder;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mDatas.get(position).getLabel() != null) {
            TitleHolder myHolder = (TitleHolder) holder;
            myHolder.setData(position);
        } else {
            MyHolder myHolder = (MyHolder) holder;
            myHolder.setData(position);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas.get(position).getLabel() != null) {
            return TITLE;
        } else {
            return CONTENT;
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        public MyHolder(View itemView) {
            super(itemView);
        }

        public void setData(final int position) {
            ((TextView) itemView).setText(mDatas.get(position).getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtils.LogE("position= " + position);

                }
            });
        }
    }

    class TitleHolder extends RecyclerView.ViewHolder {

        public TitleHolder(View itemView) {
            super(itemView);
        }

        public void setData(int position) {
            ((TextView) itemView).setText(mDatas.get(position).getLabel());
        }
    }
}
