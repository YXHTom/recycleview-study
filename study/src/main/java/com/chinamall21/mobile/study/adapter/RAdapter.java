package com.chinamall21.mobile.study.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

    public static final int HEADER = 1;
    public static final int NORMAL = 2;
    private List<TestBean> mDatas;
    private List<Integer> mPositionList;

    private Context mContext;
    private RecyclerView mRecyclerView;
    private View mHeaderView;
    private int mHeaderHeight;
    private TextView mTips;
    private ProgressBar mProgressBar;


    public RAdapter(Context context, List<TestBean> imgLists, RecyclerView recyclerView) {
        mContext = context;
        mDatas = imgLists;
        mPositionList = new ArrayList<>();
        mRecyclerView = recyclerView;
        initHeader();
    }

    private void initHeader() {
        mHeaderView = LayoutInflater.from(mContext).inflate(R.layout.rv_header, mRecyclerView, false);
        mTips = mHeaderView.findViewById(R.id.tv_tip);
        mProgressBar = mHeaderView.findViewById(R.id.pb);

//        mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                mHeaderHeight = mHeaderView.getHeight();
//
//                mHeaderView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                mRecyclerView.scrollBy(0,mHeaderHeight);
//            }
//        });
//
//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                if (((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition() == 0) {
//                    View view = mRecyclerView.getChildAt(0);
//                    if (view.getY() >= -mHeaderHeight / 2) {
//                        LogUtils.LogE("释放刷新");
//                        mTips.setText("释放刷新");
//                    }else {
//                        LogUtils.LogE("下拉刷新");
//                        mTips.setText("下拉刷新");
//                    }
//                }
//            }
//        });


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER) {
            HeaderViewHolder viewHolder = new HeaderViewHolder(mHeaderView);
            return viewHolder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cb, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.setData(position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER;
        } else {
            return NORMAL;
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
        private ImageView mImageView;
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

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }


}
