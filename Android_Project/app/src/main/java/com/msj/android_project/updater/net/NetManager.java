package com.msj.android_project.updater.net;

import java.io.File;

public interface NetManager {

    void get(String url,NetCallback callback,Object tag);

    void download(String url, File targetFile, NetDownLoadCallBack downLoadCallBack,Object tag);

    void cancel(Object tag);

}
