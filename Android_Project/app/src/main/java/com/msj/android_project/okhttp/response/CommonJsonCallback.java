package com.msj.android_project.okhttp.response;

import android.os.Handler;
import android.os.Looper;

import com.msj.android_project.okhttp.ResponseEntityToModule;
import com.msj.android_project.okhttp.exception.OkHttpException;
import com.msj.android_project.okhttp.listener.DisposeDataHandle;
import com.msj.android_project.okhttp.listener.DisposeDataListener;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 处理json回调
 */
public class CommonJsonCallback implements Callback {

    protected final String RESULT_CODE = "code";
    protected final int RESULT_CODE_VALUE = 1;
    protected final String ERROR_MSG = "msg";
    protected final String EMPTY_MSG = "";

    protected final int NETWORK_ERROE = -1; // the network relative error
    protected final int JSON_ERROE = -2;   // thi json relative error
    protected final int OTHNER_ERROE = -3; // the unknow error


    private DisposeDataListener mListener;
    private Handler deieverHandler;
    private Class<?> mClass;

    public CommonJsonCallback(DisposeDataHandle handle) {
        mListener = handle.mDisposeDataListener;
        mClass = handle.aClass;
        deieverHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onFailure(Call call, final IOException e) {
        deieverHandler.post(new Runnable() {
            @Override
            public void run() {
             mListener.onFailure(e);
            }
        });
    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        final String result = response.body().string();

        deieverHandler.post(new Runnable() {
            @Override
            public void run() {
                handleResponse(result);
            }
        });
    }

    private void handleResponse(String result) {

        if (result == null || result.equals("")){
            mListener.onFailure(new OkHttpException(NETWORK_ERROE,EMPTY_MSG));
        }

        try {
            JSONObject object = new JSONObject(result);
            if (object.has(RESULT_CODE)){
                if (object.optInt(RESULT_CODE) == RESULT_CODE_VALUE){
                    if (mClass == null){
                        mListener.onSuccess(object);
                    }else{
                        Object obj = ResponseEntityToModule.parseJsonObjectToModule(object,mClass);
                        if (obj == null){
                            mListener.onFailure(new OkHttpException(NETWORK_ERROE,EMPTY_MSG));
                        }else{
                            mListener.onSuccess(obj);
                        }
                    }
                }else{
                    mListener.onFailure(new OkHttpException(NETWORK_ERROE,EMPTY_MSG));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            mListener.onFailure(new OkHttpException(OTHNER_ERROE,e.getMessage()));
        }
    }
}
