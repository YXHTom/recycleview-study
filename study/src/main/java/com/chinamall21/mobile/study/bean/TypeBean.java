package com.chinamall21.mobile.study.bean;

/**
 * desc：
 * author：Created by xusong on 2018/9/17 12:33.
 */


public class TypeBean {

    private int type;
    private String content;

    public int getType() {
        return type;
    }

    public void setType(int title) {
        this.type = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "TypeBean{" +
                "type='" + type + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
