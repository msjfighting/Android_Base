package com.msj.android_project.updater;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import com.msj.android_project.updater.net.NetManager;
import com.msj.android_project.updater.net.OkHttpNetManager;


import java.io.File;

public class AppUpdater {


    private static AppUpdater sInstance = new AppUpdater();


    /**
     * 网络请求,下载能力
     */
    private NetManager netManager = new OkHttpNetManager();


    public void setNetManager(NetManager netManager){
        this.netManager = netManager;
    }


    public NetManager getNetManager(){
        return netManager;
    }

    public static AppUpdater getInstance(){
        return sInstance;
    }

}
