package com.chinamall21.mobile.study.fragment;

import android.view.View;
import android.widget.TextView;

import com.chinamall21.mobile.study.R;
import com.chinamall21.mobile.study.base.BaseFragment;
import com.chinamall21.mobile.study.utils.LogUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * desc：
 * author：Created by xusong on 2018/11/7 12:20.
 */

public class OKHttpFragment extends BaseFragment {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    private TextView mTv;

    public static OKHttpFragment newInstance() {
        OKHttpFragment fragment = new OKHttpFragment();
        return fragment;
    }

    @Override
    protected void initView(View rootView) {
        mTv = rootView.findViewById(R.id.tv);
    }

    @Override
    public void loadData() {
        //getRequest();
        //postRequest();

        //synGet();

        get();
        //postPushString();
    }

    private void postPushString() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                String postBody = ""
                        + "Releases\n"
                        + "--------\n"
                        + "\n"
                        + " * _1.0_ May 6, 2013\n"
                        + " * _1.1_ June 15, 2013\n"
                        + " * _1.2_ August 11, 2013\n";
                Request request = new Request.Builder().url("https://api.github.com/markdown/raw")
                        .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody)).build();
                try {
                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful()) {
                        throw new RuntimeException("IOException");
                    }
                    LogUtils.LogE(response.isSuccessful() + "");
                    LogUtils.LogE(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 异步请求
     */
    private void get() {
        Request request = new Request.Builder().url("http://publicobject.com/helloworld.txt").build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.LogE(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }

    /**
     * 同步get请求只能在子线程
     */
    private void synGet() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder().url("http://publicobject.com/helloworld.txt").build();
                try {
                    Response response = new OkHttpClient().newCall(request).execute();
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);
                    }
                    Headers headers = response.headers();
                    for (int i = 0; i < headers.size(); i++) {
                        LogUtils.LogE(headers.name(i) + " value =" + headers.value(i));
                    }
                    LogUtils.LogE(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void postRequest() {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody responseBody = RequestBody.create(JSON, "username:zhy");
        Request request = new Request.Builder().url("https://github.com/hongyangAndroid").post(responseBody).build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.LogE(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtils.LogE(response.isSuccessful() + "");
                LogUtils.LogE("onResponese" + response.toString());
            }
        });
    }

    private void getRequest() {
        //创建OkhttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //创建一个Request
        Request request = new Request.Builder()
                .url("https://github.com/hongyangAndroid").build();
        //newCall
        Call call = okHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                LogUtils.LogE(Thread.currentThread().getName());
                LogUtils.LogE(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtils.LogE(response.toString());
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_drag;
    }
}
