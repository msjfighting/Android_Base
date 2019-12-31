package untils;

import android.content.Context;

public class DensityUtil {

    /*
        根据手机的分辨率从dip的单位 转换为px(像素)
     */
    public static int dip2px(Context content,float dpValue){
        final  float scale = content.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /*
     根据手机的分辨率从px 的单位 转为 dp
     */

    public static int px2dip(Context content,float pxValue){
        final  float scale = content.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
