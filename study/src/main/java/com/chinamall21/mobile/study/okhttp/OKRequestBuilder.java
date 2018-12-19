package com.chinamall21.mobile.study.okhttp;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * desc：
 * author：Created by xusong on 2018/12/6 12:56.
 */


public abstract class OKRequestBuilder<T extends OKRequestBuilder> {

    protected String url;
    protected Object tag;
    protected int id;
    protected Map<String,String> mParams;
    protected Map<String,String> mHeaders;

    public T id(int id){
        this.id = id;
        return (T) this;
    }

    public T url(String url){
        this.url = url;
        return (T) this;
    }

    public T tag(Object tag){
        this.tag = tag;
        return (T) this;
    }

    public T headers(Map<String,String> headers){
        mHeaders = headers;
        return (T)this;
    }

    public T addHeaders(String key,String value){
        if(mHeaders ==null){
            mHeaders = new LinkedHashMap<>();
        }
        mHeaders.put(key,value);
        return (T)this;
    }

}
