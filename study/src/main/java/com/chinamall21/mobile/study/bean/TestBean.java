package com.chinamall21.mobile.study.bean;

/**
 * desc：
 * author：Created by xusong on 2018/10/15 18:05.
 */


public class TestBean {
    private String title;
    private int status;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "title='" + title + '\'' +
                ", status=" + status +
                '}';
    }
}
