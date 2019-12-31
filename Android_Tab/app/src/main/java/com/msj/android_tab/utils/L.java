package com.msj.android_tab.utils;

import android.util.Log;

import com.msj.android_tab.BuildConfig;

public class L {

    private static final String TAG = "hyman";

    private static boolean sDebud = BuildConfig.DEBUG;
    public static void d(String msg, Object... args){
        if (!sDebud) return;
        Log.d(TAG,String.format(msg, args));
    }
}
