package com.msj.android_http.transform;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

public class LoopTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(@NonNull View page, float position) {
        if(position < -1){
            position = -1;
        }else if (position > 1){
            position = 1;
        }

        float tempScale = position < 0 ? 1+position : 1-position;
        float scaleValue = 0.9f + tempScale*0.1f;

        page.setScaleX(scaleValue);
        page.setScaleY(scaleValue);



    }
}
