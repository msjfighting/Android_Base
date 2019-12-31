package com.msj.android_tab.views.transform;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

public class RotateTransformer implements ViewPager.PageTransformer {

    private static final float MAX_ROTATE = 15;
    @Override
    public void transformPage(@NonNull View page, float position) {
        // 从A->B A: position (0,-1) B: position (1,0)
        // 从B->A A: position (-1,0) B: position (0,1)

        if (position < -1){
            page.setPivotY(page.getHeight());
            page.setPivotX(page.getWidth());
            page.setRotation(-MAX_ROTATE);

        }else if (position <= 1){
            // 左边的这个页面
            if (position < 0){
                page.setPivotY(page.getHeight());
                // [0.5w 1w]
                page.setPivotX(0.5f * page.getWidth() * (1 - position));
                // (0,-1)  [0 , -max]
                page.setRotation(MAX_ROTATE * position);

            } else { // 右边的这个页面
                page.setPivotY(page.getHeight());
               // (1,0) // [0 0.5w]
                page.setPivotX(0.5f * page.getWidth() * (1 - position));
                // [ max 0]
                page.setRotation(MAX_ROTATE * position);
            }
        } else {
            page.setPivotY(page.getHeight());
            page.setPivotX(0);
            page.setRotation(MAX_ROTATE);
        }
    }
}
