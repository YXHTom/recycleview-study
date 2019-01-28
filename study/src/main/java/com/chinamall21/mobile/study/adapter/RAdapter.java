package com.chinamall21.mobile.study.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.chinamall21.mobile.study.R;
import com.chinamall21.mobile.study.bean.TestBean;

import java.util.ArrayList;
import java.util.List;

/**
 * desc：
 * author：Created by xusong on 2018/10/11 11:46.
 */


public class RAdapter extends RecyclerView.Adapter {

    private List<TestBean> mDatas;
    private List<Integer> mPositionList;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private View mHeaderView;

    public RAdapter(Context context, List<TestBean> imgLists, RecyclerView recyclerView) {
        mContext = context;
        mDatas = imgLists;
        mPositionList = new ArrayList<>();
        mRecyclerView = recyclerView;
        initHeader();
    }

    private void initHeader() {}

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cb, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.setData(position);
        }
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public List<TestBean> getData() {
        return mDatas;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;
        private CheckBox mCheckBox;

        public ViewHolder(View itemView) {
            super(itemView);
            //mImageView = itemView.findViewById(R.id.item_iv);
            mTextView = itemView.findViewById(R.id.item_title);
            mCheckBox = itemView.findViewById(R.id.item_cb);

        }

        public void setData(final int position) {
//          Glide.with(mContext).load(mDatas.get(position % mDatas.size())).into(mImageView);
            mTextView.setText(mDatas.get(position).getTitle());
            mCheckBox.setTag(position);
            if (mPositionList.contains(position)) {
                mCheckBox.setChecked(true);
            } else {
                mCheckBox.setChecked(false);
            }
            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked && !mPositionList.contains(mCheckBox.getTag())) {
                        mPositionList.add((Integer) mCheckBox.getTag());
                        mDatas.get(position).setStatus(1);
                    } else if (!isChecked && mPositionList.contains(mCheckBox.getTag())) {
                        mPositionList.remove(mCheckBox.getTag());
                        mDatas.get(position).setStatus(0);
                    }
                 }
            });
        }
    }

}
