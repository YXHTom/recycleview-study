package com.chinamall21.mobile.study.view;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.chinamall21.mobile.study.utils.LogUtils;

/**
 * desc：
 * author：Created by xusong on 2018/9/10 14:39.
 */

@SuppressLint("AppCompatCustomView")
public class TextNumber extends TextView {

    public TextNumber(Context context) {
        this(context, null);
    }

    public TextNumber(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTextNumber(final int start, int end) {
        final ValueAnimator va = ValueAnimator.ofInt(start, end);
        va.setDuration(1000);
        va.start();
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                LogUtils.LogE("value = " + value);
                setText(String.valueOf(value));
            }
        });
    }


}
