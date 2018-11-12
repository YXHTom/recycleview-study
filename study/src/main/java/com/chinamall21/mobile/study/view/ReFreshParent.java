package com.chinamall21.mobile.study.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Scroller;
import android.widget.TextView;

import com.chinamall21.mobile.study.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * desc：
 * author：Created by xusong on 2018/11/8 16:10.
 */


public class ReFreshParent extends ViewGroup implements NestedScrollingParent {

    private boolean isMeasured;
    //刷新的头部局
    private View mHeader;
    //RecycleView列表页
    private RecyclerView mContent;
    //头部局的高度
    private int mHeaderHeight;
    private Scroller mScroller;
    //进度条
    private ProgressBar mProgress;
    //刷新提示
    private TextView mTvTip;
    //指示箭头
    private ImageView mArrow;
    //上次刷新的时间
    private long mLastTime;
    private TextView mTvTime;
    private ValueAnimator mDownAnim;
    private ValueAnimator mUpAnim;

    private enum STATUS {
        //刷新中,下拉刷新，释放刷新
        REFRESHING, PULLREFRESH, RELEASEREFRESH
    }

    //当前刷新状态
    private STATUS mStatus = STATUS.PULLREFRESH;
    //上次刷新的状态
    private STATUS mLastStatus;

    public ReFreshParent(Context context) {
        this(context, null);
    }

    public ReFreshParent(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
        View view = LayoutInflater.from(context).inflate(R.layout.rv_header, this, false);
        mProgress = view.findViewById(R.id.pb);
        mTvTip = view.findViewById(R.id.tv_tip);
        mArrow = view.findViewById(R.id.iv_arrow);
        mTvTime = view.findViewById(R.id.tv_time);
        addView(view);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mHeader.layout(0, -mHeaderHeight, getWidth(), 0);
        mContent.layout(0, 0, getWidth(), getBottom());
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes) {
        //当前状态不是正在刷新中当前布局就可以滑动
        return mStatus != STATUS.REFRESHING;

    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed) {
        //上滑头部局逐渐隐藏
        boolean hiddenRefresh = dy > 0 && getScrollY() <= 0;
        //下滑头部局逐渐出现
        boolean showRefresh = dy < 0 && getScrollY() >= -mHeaderHeight && !ViewCompat.canScrollVertically(target, -1);

        if (getScrollY() >= -mHeaderHeight / 2) {
            mStatus = STATUS.PULLREFRESH;
        } else {
            mStatus = STATUS.RELEASEREFRESH;
        }
        refreshByStatus(mStatus);

        if (hiddenRefresh || showRefresh) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!isMeasured) {
            mHeader = getChildAt(0);
            mContent = (RecyclerView) getChildAt(1);
            measureChildren(widthMeasureSpec, heightMeasureSpec);
            //获取头部局的高度
            mHeaderHeight = mHeader.getMeasuredHeight();
            isMeasured = true;
            //recycleView手抬起时的监听
            mContent.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {

                        if (getScrollY() <= -mHeaderHeight / 2) {
                            mStatus = STATUS.REFRESHING;
                            refreshByStatus(mStatus);
                        } else {
                            scrollTo(0, 0);
                        }

                    }
                    return false;
                }
            });
        }
    }

    /**
     * 根据状态去改变
     */
    private void refreshByStatus(STATUS status) {
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(mLastTime));
        switch (status) {
            case PULLREFRESH:
                if (status != mLastStatus) {
                    if (mDownAnim == null) {
                        mDownAnim = ValueAnimator.ofFloat(180, 0);
                        mDownAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                mArrow.setRotation((Float) animation.getAnimatedValue());
                            }
                        });
                    }

                    mDownAnim.start();
                    mTvTip.setText("下拉刷新");
                    if (mLastTime != 0) {
                        mTvTime.setText("上次刷新时间:" + format);
                    }
                }

                break;
            case RELEASEREFRESH:
                if (status != mLastStatus) {
                    if (mUpAnim == null) {
                        mUpAnim = ValueAnimator.ofFloat(0, 180);
                        mUpAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                mArrow.setRotation((Float) animation.getAnimatedValue());
                            }
                        });
                    }

                    mUpAnim.start();
                    mTvTip.setText("释放刷新");
                    if (mLastTime != 0) {
                        mTvTime.setText("上次刷新时间:" + format);
                    }
                }

                break;
            case REFRESHING:
                if (status != mLastStatus) {
                    mArrow.setVisibility(INVISIBLE);
                    mProgress.setVisibility(VISIBLE);

                    mTvTip.setText("正在刷新");
                    scrollTo(0, -mHeaderHeight);
                    //模拟刷新时请求网络耗时
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mArrow.setVisibility(VISIBLE);
                            mProgress.setVisibility(INVISIBLE);
                            scrollTo(0, 0);
                            mStatus = STATUS.PULLREFRESH;
                            mLastTime = System.currentTimeMillis();

                            //刷新完毕
                            if (mRefreshCompleteListener != null)
                                mRefreshCompleteListener.refreshed();


                        }
                    }, 2000);
                }
                break;
        }
        mLastStatus = status;
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y <= -mHeaderHeight) {
            y = -mHeaderHeight;
        } else if (y >= 0) {
            y = 0;
        }
        super.scrollTo(x, y);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }

    public interface RefreshCompleteListener {
        void refreshed();
    }

    private RefreshCompleteListener mRefreshCompleteListener;

    public void setRefreshCompleteListener(RefreshCompleteListener refreshCompleteListener) {
        mRefreshCompleteListener = refreshCompleteListener;
    }
}
