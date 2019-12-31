package com.msj.android_project.okhttp.listener;

/**
 * 处理回调方法
 */
public interface DisposeDataListener {
    void onSuccess(Object responseObj);

    void onFailure(Object responseObj);

}
