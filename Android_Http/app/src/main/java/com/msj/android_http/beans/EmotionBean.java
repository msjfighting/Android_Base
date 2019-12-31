package com.msj.android_http.beans;

import android.graphics.Bitmap;

public class EmotionBean {
    /**
     * 需要绘制的bitmap
     */
    public Bitmap bitmap;

    /**
     * X轴和y轴坐标
     */
    public int x,y;

    /**
     * X轴和y轴的速率
     */
    public int velocityX, velocityY;

    /**
     * 图标开始下落的时间
     */
    public int appearTimeStamp;

    //随机缩放比例
    public float scale;

}
