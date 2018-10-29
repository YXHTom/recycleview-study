package com.chinamall21.mobile.study.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * desc：
 * author：Created by xusong on 2018/9/17 14:59.
 */


public class SizeUtils {

    public static int dp2px(Context context, int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, int sp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }
}
