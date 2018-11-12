package com.chinamall21.mobile.study.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chinamall21.mobile.study.R;
import com.chinamall21.mobile.study.bean.DataBean;
import com.chinamall21.mobile.study.utils.LogUtils;

import java.util.List;

/**
 * desc：
 * author：Created by xusong on 2018/9/8 11:10.
 */

public class Rvadapter extends RecyclerView.Adapter {

    private List<DataBean.DatasBean> mDatas;
    private Context mContext;

    public Rvadapter(Context context) {
        mContext = context;
    }

    public void setDatas(List<DataBean.DatasBean> datas) {
        mDatas = datas;
        notifyDataSetChanged();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView recyclerView = new RecyclerView(parent.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        ContentHolder holder = new ContentHolder(recyclerView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ContentHolder myHolder = (ContentHolder) holder;
        myHolder.setData(position);
    }


    @Override
    public int getItemCount() {
        if (mDatas != null) {
            return mDatas.size();
        } else
            return 0;
    }

    class ContentHolder extends RecyclerView.ViewHolder {


        ContentHolder(View itemView) {
            super(itemView);


        }

        public void setData(int position) {
            ((RecyclerView) itemView).setAdapter(new MyAdapter(mDatas.get(position)));
        }

        private class MyAdapter extends RecyclerView.Adapter {
            private DataBean.DatasBean datasBean;

            public MyAdapter(DataBean.DatasBean datasBean) {
                this.datasBean = datasBean;
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_view, parent, false);
                MyHolder myHolder = new MyHolder(view);
                return myHolder;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                MyHolder myHolder = (MyHolder) holder;
                myHolder.setData(position);
            }

            @Override
            public int getItemCount() {
                return 3;
            }

            class MyHolder extends RecyclerView.ViewHolder {
                ImageView iv;
                TextView tv;

                public MyHolder(View itemView) {
                    super(itemView);
                    iv = itemView.findViewById(R.id.iv);
                    tv = itemView.findViewById(R.id.tv);
                }

                public void setData(int position) {

                    LogUtils.LogE("pos=" + position);
                    tv.setText(datasBean.getName() + "position" + position);
                    Glide.with(itemView.getContext()).load(datasBean.getImg()).centerCrop().into(iv);
                }
            }
        }
    }


}
