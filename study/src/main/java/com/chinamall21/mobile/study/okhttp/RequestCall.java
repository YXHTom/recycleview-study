package com.chinamall21.mobile.study.okhttp;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * desc：对OkHttpRequest的封装，对外提供更多的接口：cancel(),readTimeOut()...
 * author：Created by xusong on 2018/12/6 14:17.
 */


public class RequestCall {
    private OKHttpRequest okHttpRequest;
    private Request request;
    private Call call;

    private long readTimeOut;
    private long writeTimeOut;
    private long connTimeOut;
    private OkHttpClient clone;

    public RequestCall(OKHttpRequest request) {
        this.okHttpRequest = request;
    }

    public RequestCall readTimeOut(long readTimeOut) {
        this.readTimeOut = readTimeOut;
        return this;
    }

    public RequestCall writeTimeOut(long writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
        return this;
    }

    public RequestCall connTimeOut(long connTimeOut) {
        this.connTimeOut = connTimeOut;
        return this;
    }

    public Call buildCall(Callback callback) {
        request = generateRequest(callback);

        if (readTimeOut > 0 || writeTimeOut > 0 || connTimeOut > 0) {
            readTimeOut = readTimeOut > 0 ? readTimeOut : OkHttpUtils.DEFAULT_MILLISECONDS;
            writeTimeOut = writeTimeOut > 0 ? writeTimeOut : OkHttpUtils.DEFAULT_MILLISECONDS;
            connTimeOut = connTimeOut > 0 ? connTimeOut : OkHttpUtils.DEFAULT_MILLISECONDS;

            clone = OkHttpUtils.getInstance().getOkHttpClient().newBuilder()
                    .readTimeout(readTimeOut, TimeUnit.MILLISECONDS)
                    .writeTimeout(writeTimeOut, TimeUnit.MILLISECONDS)
                    .connectTimeout(connTimeOut, TimeUnit.MILLISECONDS)
                    .build();

            call = clone.newCall(request);
        } else {
            call = OkHttpUtils.getInstance().getOkHttpClient().newCall(request);
        }
        return call;
    }

    private Request generateRequest(Callback callback) {
        return okHttpRequest.generateRequest(callback);
    }
    public void execute(Callback callback) {
        buildCall(callback);

        if (callback != null) {
            callback.onBefore(request, getOkHttpRequest().getId());
        }

        //OkHttpUtils.getInstance().execute(this, callback);
    }

    public OKHttpRequest getOkHttpRequest() {
        return okHttpRequest;
    }
}
