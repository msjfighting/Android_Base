package com.msj.android_project.okhttp.listener;

/**
 *
 */
public class DisposeDataHandle {


    public DisposeDataListener mDisposeDataListener;

    public Class<?> aClass = null;

    public String mSource = null;

    public DisposeDataHandle(DisposeDataListener disposeDataListener) {
        mDisposeDataListener = disposeDataListener;
    }

    public DisposeDataHandle(DisposeDataListener disposeDataListener, Class<?> aClass) {
        mDisposeDataListener = disposeDataListener;
        this.aClass = aClass;
    }

    public DisposeDataHandle(DisposeDataListener mDisposeDataListener, String mSource) {
        this.mDisposeDataListener = mDisposeDataListener;
        this.mSource = mSource;
    }
}
