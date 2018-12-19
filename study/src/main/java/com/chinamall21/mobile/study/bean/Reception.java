package com.chinamall21.mobile.study.bean;

/**
 * desc：
 * author：Created by xusong on 2018/11/22 11:48.
 */

public class Reception {
    private String protocol;
    private int code;
    private String message;
    private String url;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Reception{" +
                "protocol='" + protocol + '\'' +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
