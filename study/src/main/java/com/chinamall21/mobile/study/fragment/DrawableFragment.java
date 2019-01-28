package com.chinamall21.mobile.study.fragment;

import android.view.View;

import com.chinamall21.mobile.study.R;
import com.chinamall21.mobile.study.base.BaseFragment;

/**
 * desc：
 * author：Created by xusong on 2018/11/7 12:20.
 */

public class DrawableFragment extends BaseFragment {

    public static DrawableFragment newInstance() {
        DrawableFragment fragment = new DrawableFragment();
        return fragment;
    }

    @Override
    protected void initView(View rootView) {
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_drawable;
    }
}
