package com.msj.android_project.updater.net;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpNetManager implements NetManager {

    private static OkHttpClient mOkHttpClient;

    private static Handler handler = new Handler(Looper.getMainLooper());

    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(15, TimeUnit.SECONDS);
        mOkHttpClient = builder.build();
        // https 自签名 握手错误
        //  builder.sslSocketFactory()
    }

    @Override
    public void get(String url, NetCallback callback,Object tag) {
        // requestbuilder ---> Request ----> Call ----> execute/enqueue
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url).get().tag(tag).build();
        Call call = mOkHttpClient.newCall(request);
        // 同步操作
//      Response response = call.execute();

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 此时为非UI线程
                // 主线程返回数据
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.failed(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final String string = response.body().string();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("GET: response >>>>", string);
                            callback.success(string);
                        }
                    });
                } catch (Throwable e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.failed(e);
                        }
                    });
                }
            }
        });

    }

    @Override
    public void download(String url, File targetFile, NetDownLoadCallBack downLoadCallBack,Object tag) {

        if (targetFile.exists()) {
            targetFile.getParentFile().mkdirs();
        }

        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url).get().tag(tag).build();
        Call call = mOkHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                downLoadCallBack.failed(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;

                OutputStream os = null;
                try {
                    long totalLen = response.body().contentLength();

                    is = response.body().byteStream();
                    os = new FileOutputStream(targetFile);

                    byte[] buffer = new byte[8 * 1024];
                    long curLen = 0;

                    int bufferLen = 0;

                    while (!call.isCanceled() && (bufferLen = is.read(buffer)) != -1) {
                        os.write(buffer, 0, bufferLen);
                        os.flush();
                        curLen += bufferLen;

                        final long finalCurLen = curLen;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.i("下载: progress >>>>", (int) (finalCurLen * 1.0f / totalLen * 100)+"");
                                downLoadCallBack.progress((int) (finalCurLen * 1.0f / totalLen * 100));
                            }
                        });
                    }
                    if (call.isCanceled()){
                        return;
                    }
                    try {
                        targetFile.setExecutable(true, false);
                        targetFile.setReadable(true, false);
                        targetFile.setWritable(true, false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            downLoadCallBack.success(targetFile);
                        }
                    });
                } catch (Throwable e) {
                    e.printStackTrace();
                    if (call.isCanceled()){
                        return;
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            downLoadCallBack.failed(e);
                        }
                    });
                } finally {
                    if (is != null) {
                        is.close();
                    }
                    if (os != null) {
                        os.close();
                    }
                }
            }
        });

    }

    @Override
    public void cancel(Object tag) {
        List<Call> calls = mOkHttpClient.dispatcher().queuedCalls();
        if (calls != null){
            for (Call call :calls) {
                if (tag.equals(call.request().tag())){
                    call.cancel();
                }
            }
        }

        List<Call> calls1 = mOkHttpClient.dispatcher().runningCalls();
        if (calls1 != null){
            for (Call call :calls1) {
                if (tag.equals(call.request().tag())){
                    call.cancel();
                }
            }
        }
    }
}
