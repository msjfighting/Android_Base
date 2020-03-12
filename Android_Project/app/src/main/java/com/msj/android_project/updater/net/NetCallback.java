package com.msj.android_project.updater.net;

public interface NetCallback {

    void success(String response);

    void failed(Throwable throwable);
}
