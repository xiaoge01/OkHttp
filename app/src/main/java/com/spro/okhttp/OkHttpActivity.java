package com.spro.okhttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpActivity extends AppCompatActivity {

    private Call mNewCall;
    private OkHttpClient mOkHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn)
    public void onClick() {
        // 创建OkHttp的拦截器并且设置打印级别
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // 初始化（构造器模式）
        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        // 请求的构建
        Request request = new Request.Builder()
                .get()// Get请求的方式
                .url("http://www.baidu.com")
                .header("Accept-Charset", "utf-8")
                .header("X-type", "json")
                .build();

        mNewCall = mOkHttpClient.newCall(request);
        mNewCall.enqueue(new Callback() {

            // onFailure：请求失败，后台线程
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("TAG", "请求失败");
            }

            // onResponse：请求成功，后台线程，只要响应码：1XX--5XX
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i("TAG", "请求成功:" + result);
            }
        });
    }

}
