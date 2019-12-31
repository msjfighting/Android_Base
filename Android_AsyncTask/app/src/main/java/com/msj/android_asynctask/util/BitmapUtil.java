package com.msj.android_asynctask.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapUtil {

    // 根据图像 要显示的大小 和真正的大小的采样 进行压缩
    public static Bitmap ratio(String filepath,int pixelW,int pixelH){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        // 预加载
        BitmapFactory.decodeFile(filepath,options);

        int originalW = options.outWidth;
        int originalH = options.outHeight;

        options.inSampleSize = getSimpleSize(originalW,originalH,pixelW,pixelH);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filepath,options);
    }

    private static int getSimpleSize(int originalW, int originalH, int pixelW, int pixelH) {

        int simpleSize = 1;

        if (originalW > originalH && originalW > pixelW){
            simpleSize = originalW / pixelW;
        }else if (originalW < originalH && originalH < pixelH){
            simpleSize = originalH / pixelH;
        }
        if (simpleSize <= 0){
            simpleSize = 1;
        }

        return simpleSize;
    }
}
