package com.msj.android_project.updater.net;

import java.io.File;

public interface NetDownLoadCallBack {
    void success(File apkFile);
    void progress(int progress);
    void failed(Throwable throwable);
}
