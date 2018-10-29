package com.chinamall21.mobile.study;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chinamall21.mobile.study.adapter.LeftAdapter;
import com.chinamall21.mobile.study.adapter.RightAdapter;
import com.chinamall21.mobile.study.bean.DataBean;
import com.chinamall21.mobile.study.bean.TypeBean;
import com.chinamall21.mobile.study.itemdecoration.HeaderItemDecoration;
import com.chinamall21.mobile.study.utils.LogUtils;
import com.chinamall21.mobile.study.utils.SizeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * desc：
 * author：Created by xusong on 2018/10/17 15:02.
 */


public class TestActivity extends AppCompatActivity implements HeaderItemDecoration.OnHeaderChangedListener {

    private String[] mBrands = {"品牌", "服饰", "箱包", "鞋靴", "配饰",
            "美妆护肤", "3C配件", "运动户外",
            "家居日用", "成人情趣", "电器", "潮流热卖"};

    private RecyclerView mRvLeft;
    private RecyclerView mRvRight;
    private List<TypeBean> mTypeBeanList;
    private List<DataBean.DatasBean> mDatasBeanList;
    private GridLayoutManager mGridLayoutManager;
    private List<Integer> mItemCountList2;
    private int mItemHeight;

    private int mHeight;
    private String mTag;
    private LeftAdapter mLeftAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mRvLeft = findViewById(R.id.rv_left);
        mRvRight = findViewById(R.id.rv_right);
        mRvLeft.setLayoutManager(new LinearLayoutManager(this));
        mGridLayoutManager = new GridLayoutManager(this, 3);
        mItemHeight = SizeUtils.dp2px(this, 60);
        mRvRight.setLayoutManager(mGridLayoutManager);

        initLeftData();
        initRightData();

    }

    private void initLeftData() {
        mTypeBeanList = new ArrayList<>();
        for (int i = 0; i < mBrands.length; i++) {
            TypeBean typeBean = new TypeBean();
            typeBean.setContent(mBrands[i]);
            if (i == 0) {
                typeBean.setType(2);
            }
            mTypeBeanList.add(typeBean);
        }

        mLeftAdapter = new LeftAdapter(mTypeBeanList, this);
        mRvLeft.setAdapter(mLeftAdapter);

        mLeftAdapter.setTypeClickListener(new LeftAdapter.TypeClickListener() {
            @Override
            public void typeChange(int position) {
                if (mHeight == 0) {
                    mHeight = mRvRight.getHeight();
                    LogUtils.LogE("height = " + mHeight);
                }

                mTag = mTypeBeanList.get(position).getContent();
                int first = mGridLayoutManager.findFirstVisibleItemPosition();
                int last = mGridLayoutManager.findLastVisibleItemPosition();
                int pos = mItemCountList2.get(position);
                if (pos < first) {
                    LogUtils.LogE("0");
                    mRvRight.smoothScrollToPosition(pos);
                } else if (pos >= first && pos < last) {
                    int top = mRvRight.getChildAt(pos - first).getTop();
                    LogUtils.LogE("top = " + top);
                    mRvRight.smoothScrollBy(0, top);


                } else if (pos >= last) {
                    LogUtils.LogE("2");
                    mRvRight.smoothScrollToPosition(pos);

                }

            }
        });

        mRvRight.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int last = mGridLayoutManager.findLastVisibleItemPosition();
                    if (mDatasBeanList.get(last).getLabel() != null && mDatasBeanList.get(last).getLabel().equals(mTag)) {
                        mRvRight.smoothScrollBy(0, mHeight - mItemHeight);
                    }
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            }
        });

    }

    private void initRightData() {
        mDatasBeanList = new ArrayList<>();

        mItemCountList2 = new ArrayList<>();
        for (int i = 0; i < mBrands.length; i++) {

            int a = new Random().nextInt(30) + 1;
            mItemCountList2.add(mDatasBeanList.size());
            for (int j = 0; j <= a; j++) {
                DataBean.DatasBean bean = new DataBean.DatasBean();
                if (j == 0) {
                    bean.setLabel(mBrands[i]);
                }
                bean.setName(mBrands[i] + "" + j);
                bean.setPinyin(mBrands[i]);
                mDatasBeanList.add(bean);
            }
        }

        mRvRight.addItemDecoration(new HeaderItemDecoration(this, mDatasBeanList, this));
        RightAdapter adapter = new RightAdapter(mDatasBeanList, this);
        mRvRight.setAdapter(adapter);
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mDatasBeanList.get(position).getLabel() == null ? 1 : 3;
            }
        });

        LogUtils.LogE(mItemCountList2.toString());
    }

    @Override
    public void onHeaderChange(String title) {
        mLeftAdapter.dataChanged(title);
    }
}
