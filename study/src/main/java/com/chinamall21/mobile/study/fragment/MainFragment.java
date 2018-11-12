package com.chinamall21.mobile.study.fragment;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.chinamall21.mobile.study.R;
import com.chinamall21.mobile.study.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * desc：
 * author：Created by xusong on 2018/11/5 16:56.
 */


public class MainFragment extends BaseFragment {

    private String[] mStrings = {"简介", "评论", "更多"};
    private List<Fragment> mFragments = new ArrayList<>();
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    public static BaseFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }


    @Override
    protected void initView(View rootView) {
        mViewPager = rootView.findViewById(R.id.viewpager);
        mTabLayout = rootView.findViewById(R.id.tabs);
        mFragments.add(TestFragment.newInstance(mStrings[0]));
        mFragments.add(TestFragment.newInstance(mStrings[1]));
        mFragments.add(TestFragment.newInstance(mStrings[2]));
        mViewPager.setAdapter(new MyAdapter(mFragments, getFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main;
    }

    class MyAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragments;

        MyAdapter(List<Fragment> fragments, FragmentManager fm) {
            super(fm);
            mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mStrings[position];
        }
    }

}
