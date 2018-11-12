package com.chinamall21.mobile.study.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.chinamall21.mobile.study.utils.LogUtils;

import java.lang.ref.WeakReference;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * desc：
 * author：Created by xusong on 2018/9/8 15:45.
 */


public class MyBannerManger extends LinearLayoutManager {

    private RecyclerView mRecyclerView;
    private final InterValHander mHander;
    private static final int INTERVAL = 3000;
    private boolean mIsLayoutComplete = false;
    private static int position = 1;
    private LinearSnapHelper mSnapHelper;

    public MyBannerManger(Context context, RecyclerView recyclerView) {
        super(context);
        setOrientation(HORIZONTAL);
        mRecyclerView = recyclerView;
        mHander = new InterValHander(this);
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()  == MotionEvent.ACTION_DOWN){
                    mHander.removeCallbacksAndMessages(null);
                }else if(event.getAction()  == MotionEvent.ACTION_UP){
                    mHander.sendEmptyMessageDelayed(position,INTERVAL);
                }
                return false;
            }
        });
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
        mSnapHelper = new LinearSnapHelper();
        mSnapHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        if (!mIsLayoutComplete) {
            mHander.sendEmptyMessageDelayed(position,INTERVAL);
        }

        mIsLayoutComplete = true;
    }

    private RecyclerView getRecyclerView(){
        return mRecyclerView;
    }

    static class InterValHander extends Handler {

        private WeakReference<MyBannerManger> mWeakReference;
        private MyBannerManger mBannerManger;

        public InterValHander(MyBannerManger myBannerManger) {
            mWeakReference = new WeakReference<>(myBannerManger);
            mBannerManger = myBannerManger;
        }

        @Override
        public void handleMessage(Message msg) {
            LogUtils.LogE(msg.toString());
            mBannerManger.getRecyclerView().smoothScrollToPosition(position);
            sendEmptyMessageDelayed(0,INTERVAL);
            position++;

        }
    }


    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        LogUtils.LogE("smoothScrollToPosition");
        LinearSmoothScroller smoothScroller =
                new LinearSmoothScroller(recyclerView.getContext()) {
                    // 返回：滑过1px时经历的时间(ms)。
                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        float f = 150f / displayMetrics.densityDpi;
                        LogUtils.LogE("F = "+f);
                        return 0.5f;
                    }
                };

        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }

    @Override
    public void onScrollStateChanged(int state) {
        if(state == SCROLL_STATE_IDLE){
            position = getPosition(mSnapHelper.findSnapView(this))+1;
        }
    }
}