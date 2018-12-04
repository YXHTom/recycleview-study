package com.chinamall21.mobile.study.fragment;

import android.view.View;

import com.chinamall21.mobile.study.R;
import com.chinamall21.mobile.study.api.ApiService;
import com.chinamall21.mobile.study.base.BaseFragment;
import com.chinamall21.mobile.study.net.RetrofitManager;
import com.chinamall21.mobile.study.utils.LogUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * desc：
 * author：Created by xusong on 2018/11/7 12:20.
 */


public class RetrofitFragment extends BaseFragment {

    public static RetrofitFragment newInstance() {
        RetrofitFragment fragment = new RetrofitFragment();
        return fragment;
    }

    @Override
    protected void initView(View rootView) {
        rootView.findViewById(R.id.post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiService service = RetrofitManager.getRetrofit().create(ApiService.class);
                Call<ResponseBody> call = service.getCall("你好,世界");
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            LogUtils.LogE("onResponse =" + response.body().string());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        LogUtils.LogE("onFailure =" + t.toString());
                    }
                });
            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_retrofit;
    }
}
