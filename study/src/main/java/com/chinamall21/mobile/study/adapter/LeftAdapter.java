package com.chinamall21.mobile.study.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chinamall21.mobile.study.R;
import com.chinamall21.mobile.study.bean.TypeBean;
import com.chinamall21.mobile.study.utils.LogUtils;

import java.util.List;

/**
 * desc：
 * author：Created by xusong on 2018/10/17 15:10.
 */


public class LeftAdapter extends RecyclerView.Adapter {
    private List<TypeBean> mDatas;
    private Context mContext;


    public LeftAdapter(List<TypeBean> datas, Context context) {
        mDatas = datas;
        mContext = context;
        LogUtils.LogE(mDatas.toString());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView textView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_brand, parent, false);
        MyHolder holder = new MyHolder(textView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.setData(position);
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
            ((TextView) itemView).setText(mDatas.get(position).getContent());
            if (mDatas.get(position).getType() == 2) {
                itemView.setSelected(true);
            } else {
                itemView.setSelected(false);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDatas.get(position).getType() == 2) {
                        return;
                    }
                    for (int i = 0; i < mDatas.size(); i++) {
                        mDatas.get(i).setType(0);
                    }

                    if (mTypeClickListener != null) {
                        mDatas.get(position).setType(2);
                        notifyDataSetChanged();
                        mTypeClickListener.typeChange(position);
                    }
                }
            });
        }
    }

    public interface TypeClickListener {
        void typeChange(int position);
    }

    private TypeClickListener mTypeClickListener;

    public void setTypeClickListener(TypeClickListener typeClickListener) {
        mTypeClickListener = typeClickListener;
    }

    public void dataChanged(String label) {
        for (int i = 0; i < mDatas.size(); i++) {
            if(label.equals(mDatas.get(i).getContent())){
                mDatas.get(i).setType(2);
            }else {
                mDatas.get(i).setType(0);
            }
        }
        notifyDataSetChanged();
    }


}
