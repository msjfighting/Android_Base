package com.msj.android_asynctask.util;

import android.content.Context;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;

public class DiskLruCacheHelper {

    private static final String DIR_NAME = "diskCache";

    private static final int MAX_COUNT = 5 * 1024 * 1024;

    private static final int DEFAULT_APP_VERSION = 1; // 一旦版本升级,则所以缓存无效

    private static final String TAG = "DiskLruCacheHelper";

    private DiskLruCache mDiskLruCache;

    /**
     * 此处的context 使用全局的ApplicationContext即可,应用缓存模块是针对整个应用的
     * @param context
     * @throws IOException
     */
    public DiskLruCacheHelper(Context context) throws IOException {

    }

    public DiskLruCacheHelper(Context context, String dirName) throws IOException{

    }

    public  DiskLruCacheHelper(Context context, String dirName,int maxCount) throws IOException{

    }

    public DiskLruCacheHelper(File dir)throws IOException{

    }

//    public DiskLruCacheHelper(Context context,File dir) throws IOException{
//        mDiskLruCache = generateCache(context,dir,MAX_COUNT);
//
//    }
//    public DiskLruCacheHelper(Context context,File dir,int maxCount) throws IOException{
//        mDiskLruCache = generateCache(context,dir,maxCount);
//
//    }
//    private DiskLruCache generateCache(Context context, File dir, int maxCount) throws IOException {
//        if (!dir.exists() || !dir.isDirectory()){
//            throw  new IllegalArgumentException(dir+" is not a directory or does not exists");
//        }
//        int appVersion =int context == null ? DEFAULT_APP_VERSION : Utils.getAppVersion(context);
//
//        DiskLruCache diskLruCache = DiskLruCache.open(dir,appVersion,DEFAULT_APP_VERSION,maxCount);
//
//        return diskLruCache;
//    }


}
