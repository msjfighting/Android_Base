package com.msj.android_http;

import android.app.Application;
import android.content.Context;

import com.http.api.OkHttpUtil;

public class CardApplication extends Application {
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpUtil.init();
        mContext = this;
    }

}
