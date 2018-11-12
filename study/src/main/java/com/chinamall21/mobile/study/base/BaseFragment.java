package com.chinamall21.mobile.study.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * desc：
 * author：Created by xusong on 2018/11/5 16:43.
 */


public abstract class BaseFragment extends Fragment{
    protected View mRootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView != null) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
        } else {

            mRootView = inflater.inflate(getLayoutId(), container, false);
            initView(mRootView);
            loadData();

        }
        return mRootView;
    }
    //添加fragment
    protected void addFragment(BaseFragment fragment) {
        if (null != fragment && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).addFragment(fragment);
        }
    }

    //移除fragment
    protected void removeFragment() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).removeFragment();
        }
    }


    public  void loadData(){}


    protected abstract void initView(View rootView);

    public abstract int getLayoutId();
}
