package com.chinamall21.mobile.study.eventBus;

/**
 * desc：
 * author：Created by xusong on 2018/10/10 15:55.
 */


public class MyEventBus {

    private static MyEventBus mInstance;

    public static MyEventBus getDefault() {
        if (mInstance == null) {
            synchronized (MyEventBus.class) {
                if (mInstance == null) {
                    mInstance = new MyEventBus();
                }
            }
        }
        return mInstance;
    }

    /**
     * 注册
     */
    public void register(Object object){

    }

}
