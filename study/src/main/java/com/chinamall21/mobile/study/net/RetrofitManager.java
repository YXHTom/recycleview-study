package com.chinamall21.mobile.study.net;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * desc：
 * author：Created by xusong on 2018/11/22 12:02.
 */


public class RetrofitManager {
    private static Retrofit retrofit;

    static {
// 第2部分：在创建Retrofit实例时通过.baseUrl()设置
         retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/") //设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                .build();

    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }

}
