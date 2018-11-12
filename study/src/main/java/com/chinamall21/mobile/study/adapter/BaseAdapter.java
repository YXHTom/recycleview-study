package com.chinamall21.mobile.study.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * desc：
 * author：Created by xusong on 2018/11/8 15:48.
 */


public abstract class BaseAdapter<T> extends RecyclerView.Adapter {
    private List<T> mDatas;

    public BaseAdapter(List<T> datas) {
        this.mDatas = datas;
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public void setDatas(List<T> datas) {
        mDatas = datas;
        notifyDataSetChanged();


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getItemLayout(), parent, false);
        return new MyHolder(view);
    }

    protected abstract int getItemLayout();

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.setData(position);
    }

    @Override
    public int getItemCount() {
        if (mDatas != null) {
            return mDatas.size();
        }
        return 0;

    }

    class MyHolder extends RecyclerView.ViewHolder {

        public MyHolder(View itemView) {
            super(itemView);
        }

        public void setData(int position) {
            convert(itemView,position);
        }
    }

    public abstract void convert(View itemView, int position);

}
