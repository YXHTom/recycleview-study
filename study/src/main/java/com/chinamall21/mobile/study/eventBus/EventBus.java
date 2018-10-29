package com.chinamall21.mobile.study.eventBus;

/**
 * desc：
 * author：Created by xusong on 2018/10/10 15:55.
 */


public class EventBus {

    private static EventBus mInstance;

    public static EventBus getDefault() {
        if (mInstance == null) {
            synchronized (EventBus.class) {
                if (mInstance == null) {
                    mInstance = new EventBus();
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
