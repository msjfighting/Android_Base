package com.msj.android_tab;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.msj.android_tab.Fragment.SplashFragment;
import com.msj.android_tab.Fragment.TabFragment;
import com.msj.android_tab.views.TabView;
import com.msj.android_tab.views.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SplashActivity extends BaseActicity {

    private ViewPager mVpMian;

    private int[] mResIds = new int[]{
            R.drawable.guide_image1,
            R.drawable.guide_image2,
            R.drawable.guide_image3,
            R.drawable.guide_image4,
            R.drawable.guide_image5,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initNavTab(true,"ViewPager实现导航图效果");

        mVpMian = findViewById(R.id.vp_main);
        mVpMian.setOffscreenPageLimit(mResIds.length);
        mVpMian.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return SplashFragment.newInstance(mResIds[i]);
            }

            @Override
            public int getCount() {
                return mResIds.length;
            }
        });

        mVpMian.setPageTransformer(true,new ScaleTransformer());

    }
}
