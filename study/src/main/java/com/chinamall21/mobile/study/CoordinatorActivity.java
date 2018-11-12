package com.chinamall21.mobile.study;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chinamall21.mobile.study.fragment.TestFragment;
import com.chinamall21.mobile.study.utils.LogUtils;
import com.chinamall21.mobile.study.view.MyIndictor;

import java.util.ArrayList;
import java.util.List;

/**
 * desc：
 * author：Created by xusong on 2018/11/2 11:14.
 */


public class CoordinatorActivity extends AppCompatActivity {

    MyIndictor mIndictor;
    ViewPager mViewPager;
    RelativeLayout mRelativeLayout;
    private String[] mStrings = {"简介", "评论", "更多"};
    private List<Fragment> mFragments = new ArrayList<>();
    TextView mTextView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycoordin);

        mTextView = findViewById(R.id.tv);


        mIndictor = findViewById(R.id.indictor);
        mViewPager = findViewById(R.id.viewpager);
        mRelativeLayout = findViewById(R.id.rl);
        mIndictor.setTitles(mStrings);
        mFragments.add(TestFragment.newInstance(mStrings[0]));
        mFragments.add(TestFragment.newInstance(mStrings[1]));
        mFragments.add(TestFragment.newInstance(mStrings[2]));
        mViewPager.setAdapter(new MyAdapter(mFragments, getSupportFragmentManager()));

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                mIndictor.onScroll(position,positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mIndictor.setOnTabChangeListener(new MyIndictor.onTabChangeListener() {
            @Override
            public void onTabChange(int position) {
                mViewPager.setCurrentItem(position);
            }
        });

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("data","vacation");
                intent.putExtras(bundle);
                startActivityForResult(intent,23);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String back = data.getStringExtra("back");
        LogUtils.LogE("onActivityResult requestCode = "+requestCode
                +" resultCode = "+resultCode +" data ="+back);
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
    }
}
