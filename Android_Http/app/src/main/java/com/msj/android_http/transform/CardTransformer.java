package com.msj.android_http.transform;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

public class CardTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(@NonNull View page, float position) {
        if (position < 0){
            page.setTranslationX(-position * page.getWidth());
            // 缩放卡片并调整位置
            float scale = (page.getWidth() + 40 * position) / page.getWidth();
            page.setScaleY(scale);
            page.setScaleX(scale);

            page.setTranslationY(-position * 40);
            page.setTranslationZ(position);
        }
    }
}
