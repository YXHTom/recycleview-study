package com.chinamall21.mobile.study.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chinamall21.mobile.study.R;
import com.chinamall21.mobile.study.bean.DataBean;

import java.util.List;

/**
 * desc：
 * author：Created by xusong on 2018/9/8 11:10.
 */

public class Rvadapter extends RecyclerView.Adapter {

    private List<DataBean.DatasBean> mDatas;
    private Context mContext;
    private static final int TITLE = 1;
    private static final int CONTENT = 2;

    public Rvadapter(Context context) {
        mContext = context;
    }

    public void setDatas(List<DataBean.DatasBean> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TITLE){
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_header, parent, false);
            TitleHolder holder = new TitleHolder(view);
            return holder;
        }else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_tv, parent, false);
            ContentHolder holder = new ContentHolder(view);
            return holder;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(mDatas.get(position).isTitle()){
            TitleHolder myHolder = (TitleHolder) holder;
            myHolder.setData(position);
        }else {
            ContentHolder myHolder = (ContentHolder) holder;
            myHolder.setData(position);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas.get(position).isTitle()) {
            return TITLE;
        } else {
            return CONTENT;
        }
    }

    @Override
    public int getItemCount() {
        if (mDatas != null) {
            return mDatas.size();
        } else
            return 0;

    }

    class ContentHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public ContentHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.item_tv);
        }

        public void setData(int position) {
            tv.setText(mDatas.get(position).getName());
        }
    }

    class TitleHolder extends RecyclerView.ViewHolder {

        public TitleHolder(View itemView) {
            super(itemView);
        }

        public void setData(int position) {
            ((TextView) itemView).setText(mDatas.get(position).getPinyin().charAt(0)+"");
        }
    }

}
