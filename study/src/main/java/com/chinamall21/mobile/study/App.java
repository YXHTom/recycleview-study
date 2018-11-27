package com.chinamall21.mobile.study;

import android.app.Application;

/**
 * desc：
 * author：Created by xusong on 2018/10/30 17:24.
 */


public class App extends Application {
    public static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static App getmInstance() {
        return mInstance;
    }
}
