package com.chinamall21.mobile.study.okhttp;

import java.util.Map;

import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * desc：
 * author：Created by xusong on 2018/12/6 14:42.
 */
public abstract class OKHttpRequest {
    protected String url;
    protected Object tag;
    protected int id;
    protected Map<String, String> params;
    protected Map<String, String> headers;
    protected Request.Builder mBuilder = new Request.Builder();

    public OKHttpRequest(String url, Object tag, int id, Map<String, String> params, Map<String, String> headers) {
        this.url = url;
        this.tag = tag;
        this.id = id;
        this.params = params;
        this.headers = headers;
        if (url == null) {
            throw new NullPointerException("url can not be null");
        }
        initBuilder();
    }

    private void initBuilder() {
        mBuilder.url(url).tag(tag);
        appendHeaders();
    }

    protected abstract RequestBody buildRequestBody();

    protected RequestBody wrapRequestBody(RequestBody requestBody, final Callback callback) {
        return requestBody;
    }

    protected abstract Request buildRequest(RequestBody requestBody);

    public RequestCall build() {
        return new RequestCall(this);
    }

    public Request generateRequest(Callback callback) {
        RequestBody requestBody = buildRequestBody();
        RequestBody wrappedRequestBody = wrapRequestBody(requestBody, callback);
        Request request = buildRequest(wrappedRequestBody);
        return request;
    }

    protected void appendHeaders() {
        Headers.Builder headersBuilder = new Headers.Builder();
        if(headers==null || headers.isEmpty()){
            return;
        }

        for(String key : headers.keySet()){
            headersBuilder.add(key,headers.get(key));
        }
        mBuilder.headers(headersBuilder.build());
    }

    public int getId() {
        return id;
    }
}
