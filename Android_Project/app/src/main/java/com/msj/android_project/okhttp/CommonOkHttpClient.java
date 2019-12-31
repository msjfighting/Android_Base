package com.msj.android_project.okhttp;

import com.msj.android_project.okhttp.listener.DisposeDataHandle;
import com.msj.android_project.okhttp.response.CommonFileCallback;
import com.msj.android_project.okhttp.response.CommonJsonCallback;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 用来发送请求的工具类
 */
public class CommonOkHttpClient {

    private static final int TIME_OUT = 30;
    private static OkHttpClient mOkHttpClient;


    static {
        OkHttpClient.Builder okhttpBuilder = new OkHttpClient.Builder();

        okhttpBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });

        okhttpBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        okhttpBuilder.readTimeout(TIME_OUT,TimeUnit.SECONDS);
        okhttpBuilder.writeTimeout(TIME_OUT,TimeUnit.SECONDS);

        okhttpBuilder.followRedirects(true);
//        okhttpBuilder.sslSocketFactory()

        mOkHttpClient = okhttpBuilder.build();
    }


    public static Call post(Request request, DisposeDataHandle handle){
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
        return call;
    }

    public static Call get(Request request, DisposeDataHandle handle){
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
        return call;
    }

//    public static void download(Request request, DisposeDataHandle handle){
//        Call call = mOkHttpClient.newCall(request);
//        call.enqueue(new CommonFileCallback(handle));
//    }


    public static Call downloadFile(Request request, DisposeDataHandle handle) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonFileCallback(handle));
        return call;
    }
}
