package com.msj.android_imageselector.utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 图片加载类
 */
public class ImageLoader {
    private static ImageLoader mInstance;
    /**
     * 图片缓存的核心对象
     */
    private LruCache<String , Bitmap> mLruCache;
    /**
     * 线程池
     */
    private ExecutorService mThreadPool;

    private static final int DEFAULT_THREAD_COUNT = 1;
    /**
     * 队列的调度方式
     */
    private Type mType = Type.LIFO;
    /**
     * 任务队列
     */
    private LinkedList<Runnable> mTaskQueue;

    /**
     * 后台轮询线程
     */
    private Thread mPoolThread;
    /**
     * 后台轮询线程的Handler
     */
    private Handler mPoolThreadHandler;
    /**
     * UI线程中的Handler
     */
    private Handler mUIHandler;

    /**
     * 保证 mPoolThreadHandler 使用的时候不等null 防止空指针异常 的信号量
     */
    private Semaphore mSemaphorePoolThreadHandler = new Semaphore(0);

    /**
     * 保证线程可以真正做到FIFO,LIFO
     */
    private Semaphore mSemaphoreThreadPool;

    public enum Type{
        FIFO,LIFO
    }

    private ImageLoader(int threadCount, Type type){
        init(threadCount,type);
    }

    /**
     * 初始化
     * @param threadCount
     * @param type
     */
    private void init(int threadCount, Type type) {
        // 后台轮询线程
        mPoolThread = new Thread(){
            @SuppressLint("HandlerLeak")
            @Override
            public void run() {
                Looper.prepare();
                mPoolThreadHandler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        // 线程池取出一个任务去执行
                        mThreadPool.execute(getTask());

                        try {
                            mSemaphoreThreadPool.acquire();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                // 释放一个信号量
                mSemaphorePoolThreadHandler.release();
                Looper.loop();
            }
        };
        mPoolThread.start();
        // 获取我们应用的最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheMemory = maxMemory / 8;

        mLruCache = new LruCache<String, Bitmap>(cacheMemory){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };

        // 创建线程池
        mThreadPool = Executors.newFixedThreadPool(threadCount);
        mTaskQueue = new LinkedList<>();
        mType = type;
        mSemaphoreThreadPool = new Semaphore(threadCount);

    }

    /**
     * 从任务队列取出一个方法
     * @return
     */
    private Runnable getTask() {
        if (mType == Type.FIFO){
            return mTaskQueue.removeFirst();
        }else if (mType == mType.LIFO){
            return mTaskQueue.removeLast();
        }
        return null;
    }

    public static ImageLoader getInstance(int count, Type type){
        if (mInstance == null){
            synchronized (ImageLoader.class){
                if (mInstance == null){
                    mInstance = new ImageLoader(count,type);
                }
            }
        }
        return mInstance;
    }

    /**
     * 根据path为imageView设置图片
     * @param path
     * @param imageView
     */
    @SuppressLint("HandlerLeak")
    public void loadImage(final String path, final ImageView imageView){
        imageView.setTag(path);
        if (mUIHandler == null){
            mUIHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    // 获取得到的图片 为imageview回调设置图片
                    ImageBeanHolder holder = (ImageBeanHolder) msg.obj;
                    Bitmap bm = holder.bitmap;
                    ImageView imgView = holder.imageView;
                    String path = holder.path;
                    // 将path与getag存储路径进行比较
                    if (imgView.getTag().toString().equals(path)){
                        imgView.setImageBitmap(bm);
                    }
                }
            };
        }
        // 根据path 在缓存中获取bitmap
        Bitmap bm = getBitmapFromLruCache(path);
        if (bm != null){
            refreshBitmap(bm, imageView, path);
        }else{
            addTask(new Runnable(){
                @Override
                public void run() {
                  // 加载图片
                    // 图片压缩
                    ImageSize size = getImageViewSize(imageView);
                    Bitmap bm = decodeSampledBitmapFromPath(path,size.width,size.height);
                    // 把图片加入到缓存
                    addBitmapTuLruCacge(path,bm);

                    refreshBitmap(bm, imageView, path);

                    mSemaphoreThreadPool.release();
                }
            });
        }
    }

    private void refreshBitmap(Bitmap bm, ImageView imageView, String path) {
        Message message = Message.obtain();
        ImageBeanHolder holder = new ImageBeanHolder();
        holder.bitmap = bm;
        holder.imageView = imageView;
        holder.path = path;
        message.obj = holder;
        mUIHandler.sendMessage(message);
    }

    /**
     * 将图片加入缓存
     * @param path
     * @param bm
     */
    private void addBitmapTuLruCacge(String path, Bitmap bm) {
        if (getBitmapFromLruCache(path) == null){
            if (bm != null){
                mLruCache.put(path,bm);
            }
        }
    }

    /**
     * 根据图片需要显示是的大小对图片进行压缩
     * @param path
     * @param width
     * @param height
     * @return
     */
    private Bitmap decodeSampledBitmapFromPath(String path, int width,int height) {
        // 获得图片的大小,并不把图片加载到内存当中
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path,options);
        options.inSampleSize = caculateInSampleSize(options,width,height);

        // 使用获得到inSampleSize 再次解析图片
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path,options);
        return bitmap;
    }

    /**
     * 根据需求的宽和高以及图片实际的宽和高计算SampleSize
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private int caculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;

        int inSampleSize = 1;
        if (width < reqWidth || height < reqHeight){
            int widthRadio = Math.round(width * 1.0f / reqWidth);
            int heighthRadio = Math.round(height * 1.0f / reqHeight);
            inSampleSize = Math.max(widthRadio,heighthRadio);
        }
        return inSampleSize;
    }

    /**
     * 根据imageView获取适当的压缩的宽和高
     * @param imageView
     * @return
     */
    private ImageSize getImageViewSize(ImageView imageView) {

        ImageSize imageSize = new ImageSize();

        // 屏幕
       DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();

       RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) imageView.getLayoutParams();

       // 获取imageview的实际宽度
       int width = imageView.getWidth();
       if (width <=0){
           // 获取imageview在layout中声明的宽度
           width = lp.width;
       }
       if (width <= 0){
           // 检查最大值
           width = getImageViewFieldValue(imageView,"mMaxWidth");
       }
       if (width<=0){
           width = displayMetrics.widthPixels;
       }

        // 获取imageview的实际高度
        int height = imageView.getHeight();
        if (height <=0){
            // 获取imageview在layout中声明的高度
            height = lp.height;
        }
        if (height <= 0){
            // 检查最大值
            height = getImageViewFieldValue(imageView,"mMaxHeight");
        }
        if (height<=0){
            height = displayMetrics.heightPixels;
        }
        imageSize.width = width;
        imageSize.height = height;
        return imageSize;
    }

    /**
     * 铜鼓反射获取ImageView的某个属性
     * @param object
     * @param fieldName
     * @return
     */
    private static  int getImageViewFieldValue(Object object,String fieldName){
        int value = 0;
        try {
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = field.getInt(object);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE){
                value = fieldValue;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }

    private synchronized void addTask(Runnable runnable) {
        mTaskQueue.add(runnable);
        try {
            if (mPoolThreadHandler == null)
               mSemaphorePoolThreadHandler.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mPoolThreadHandler.sendEmptyMessage(0x110);
        
    }

    /**
     * 根据path 在缓存中获取bitmap
     * @param key
     * @return
     */
    private Bitmap getBitmapFromLruCache(String key) {

        return mLruCache.get(key);
    }


    private class ImageSize{
        int width;
        int height;
    }



    private class ImageBeanHolder{
        Bitmap bitmap;
        ImageView imageView;
        String path;

    }
}
