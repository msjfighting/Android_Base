package com.msj.android_asynctask.util;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 用来加载网络图片,并缓存图片到本地和内存
 */
public class ImageLoader {
    private static ImageLoader mLoader;
    private LruCache<String, Bitmap> mLruCache;
    /**
     * 刷新主页面
     */
    private Handler mHandler;

    public static ImageLoader getInstance(){
        if (mLoader == null){
            synchronized (ImageLoader.class){
             if (mLoader == null){
                 mLoader = new ImageLoader();
             }
            }
        }
        return mLoader;
    }
    /**
     * 初始化缓存对象
     */
    public ImageLoader(){
        // 获取最大的缓存大小
        int maxSize = (int) (Runtime.getRuntime().maxMemory() / 8);
        mLruCache = new LruCache<String, Bitmap>(maxSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return super.sizeOf(key, value);
            }
        };
    }

    /**
     * 用来加载网络图片
     * @param imageView
     * @param url
     */
    public void displayImage(final ImageView imageView, String url){
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bitmap bitmap = getBitmapFromCache((String) msg.obj);
                imageView.setImageBitmap(bitmap);
            }
        };
        Bitmap bitmap = getBitmapFromCache(url);
        // 缓存中是否存在该图片
        if (bitmap != null){
                imageView.setImageBitmap(bitmap);
            }else{
                downloadImage(imageView,url);
                imageView.setImageBitmap(getBitmapFromCache(url));
            }
    }

    /**
     * 从缓存中读取图片
     * @param url
     * @return
     */
    @SuppressLint("NewApi")
    private Bitmap getBitmapFromCache(String url){

        return mLruCache.get(url);
    }

    /**
     * 将下载的图片保存到缓存中
     * @param bitmap
     * @param url
     */
    private void putBitmapToCache(Bitmap bitmap, String url){
        if (bitmap != null)
            mLruCache.put(url,bitmap);
    }

    /**
     * 下载图片 并添加到缓存中
     * @param imageView
     * @param url
     */
    private void downloadImage(final ImageView imageView,final String url){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("TAG", "onFailure");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
//                Bitmap bitmap = BitmapUtil.ratio(String.valueOf(response.body().bytes()),imageView.getWidth(),imageView.getHeight());
                if (bitmap != null){
                    putBitmapToCache(bitmap,url);
                    Message message = Message.obtain();
                    message.obj = url;
                    mHandler.sendMessage(message);
                }else{

                }
            }
        });
    }
}
