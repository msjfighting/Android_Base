package com.msj.android_tab.views.transform;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

public class ScaleTransformer implements ViewPager.PageTransformer {
    /**
     * 当我们从第一页划到第二页的时候
     * 第一页：全屏变成0.75大小划走
     * 第二页：从0.75状态进来变成1
     */
    private static final float MIN_SCALE = 0.75f;
    private static final float MIN_ALPHA = 0.5f;
    @Override
    public void transformPage(@NonNull View page, float position) {
        // 从A->B A: position (0,-1) B: position (1,0)
        // 从B->A A: position (-1,0) B: position (0,1)

        if (position < -1){
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
            page.setAlpha(MIN_ALPHA);
        }else if (position <= 1){
            // 左边的这个页面
            if (position < 0){
                // a->b (0,-1) [1, 0.75]
               float scaleA = MIN_SCALE + ( 1- MIN_SCALE) * ( 1 + position);

               // [1 0.5]
                float alphaA = MIN_ALPHA + (1 - MIN_ALPHA) * (1 + position);

               // b->a (-1,0) [0.75, 1]
//                MIN_SCALE + (1- MIN_SCALE) *(1 + position);
                // [0.5 1]
//                MIN_ALPHA + (1-MIN_ALPHA)*(1+position);
                page.setScaleX(scaleA);
                page.setScaleY(scaleA);
                page.setAlpha(alphaA);
            } else { // 右边的这个页面
                // a->b (1,0) [0.75  1]
                float scaleB = MIN_SCALE + (1 - MIN_SCALE) * (1 - position);
                // [0.5 1]
                float alphaB = MIN_ALPHA + (1 - MIN_ALPHA) * (1 - position);
                // b->a (0,1) [0.75 1] MIN_SCALE + (1- MIN_SCALE) * (1-position);
                // [1 0.5]  MIN_SCALE + (1- MIN_SCALE) * (1-position);

                page.setScaleX(scaleB);
                page.setScaleY(scaleB);
                page.setAlpha(alphaB);
            }
        } else {
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
            page.setAlpha(MIN_ALPHA);
        }
    }
}
