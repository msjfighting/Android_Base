package com.msj.android_project;


import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.msj.android_project.utils.PreferenceUtils;
import com.tencent.smtt.sdk.QbSdk;

import org.xutils.x;

public class MyApplication extends Application {
    public static MyApplication context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        /**
         * 初始化SP
         */
        PreferenceUtils.init(context, "TRIP");

        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);

        //非wifi情况下，主动下载x5内核
        QbSdk.setDownloadWithoutWifi(true);
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
            }

            @Override
            public void onCoreInitFinished() {

            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static MyApplication getContext() {
        return context;
    }
}
