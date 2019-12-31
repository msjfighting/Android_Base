package com.msj.android_http.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.msj.android_http.beans.EmotionBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EmotionRainView extends View {
    private boolean isRaining;
    private float mEmotionHeight, mEmotionWidth;
    private Context mContext;
    private Random random;
    private List<Bitmap> mBeanList = new ArrayList<>();
    private final List<EmotionBean> mEmoticonList = new ArrayList<>();
    private Matrix matrix;
    private Paint mPaint;
    private long mSatrtTime;

    public EmotionRainView(Context context) {
        super(context);
        initView(context,null);
    }

    public EmotionRainView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    public EmotionRainView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    public EmotionRainView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context,attrs);
    }

    public void initView(Context context,  AttributeSet attrs){
        if (attrs == null) return;

        mContext = context;
        setVisibility(GONE);
        setWillNotDraw(false);

        random = new Random();

        initPaint();

    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);
        mPaint.setDither(true);
        mPaint.setColor(getResources().getColor(android.R.color.holo_red_light));
        matrix = new Matrix();
    }

    public void startRain(final List<Bitmap> list){
        if (list.size() == 0){
            throw new RuntimeException("EmoticonRainView bitmap list is 0!");
        }

        stopRain();

        setVisibility(VISIBLE);

        post(new Runnable() {
            @Override
            public void run() {
                initData(list);
                isRaining = true;
                invalidate();
            }
        });
    }

    /**
     * 停止并考虑回收
     */
    public void stopRain(){
        isRaining = false;
        setVisibility(GONE);
    }

    /**
     * 初始化bean
     * @param list
     */
    private void initData(final List<Bitmap> list) {
        if(list.size() == 0){
            return;
        }

        this.mEmotionHeight = mEmotionWidth = dp2px(mContext,50);

        this.mSatrtTime = System.currentTimeMillis();
        mBeanList.clear();
        mBeanList.addAll(list);
        mEmoticonList.clear();

        //开始画表情的总时间
        int currentDuration = 0;

        int maxDuration = 2000;

        int i = 0;
        int size = list.size();
        while (currentDuration < maxDuration){
            EmotionBean bean = new EmotionBean();
            //因为要出现重复的图标
            bean.bitmap = list.get(i % size);

            bean.x = random.nextInt(getWidth());

            bean.y =  - (int)Math.ceil(mEmotionHeight);

            float duraiotn = random.nextInt(500) + maxDuration;
            bean.velocityY = (int) (getHeight()*16/duraiotn);
            bean.scale = 1.0f;
            bean.velocityX = Math.round(random.nextFloat());

            bean.appearTimeStamp = currentDuration;
            mEmoticonList.add(bean);
            currentDuration += random.nextInt(250);

            i++;
        }

    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopRain();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!isRaining || mBeanList.size() == 0) {
            return;
        }

        long currentTimestamp = System.currentTimeMillis();
        long totalTime = currentTimestamp - mSatrtTime;

        for (int i = 0; i <mEmoticonList.size() ; i++) {
            EmotionBean bean = mEmoticonList.get(i);
            Bitmap bitmap = bean.bitmap;
            if (bean.bitmap.isRecycled()  || isOutOfBottomBound(i)  || totalTime < bean.appearTimeStamp) {
                continue;
            }

            matrix.reset();

            float heightScale = mEmotionHeight / bitmap.getHeight();
            float widthScale = mEmotionWidth / bitmap.getWidth();
            matrix.setScale(widthScale,heightScale);

            bean.x = bean.x + bean.velocityX;
            bean.y = bean.y + bean.velocityY;

            matrix.postTranslate(bean.x,bean.y);

            canvas.drawBitmap(bitmap,matrix,mPaint);
        }

        postInvalidate();

    }
    /**
     * 某张图的位置是否超出下边界
     */
    private boolean isOutOfBottomBound(int position){
        return mEmoticonList.get(position).y > getHeight();
    }

    public static int dp2px(Context context,float dpValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }
}
