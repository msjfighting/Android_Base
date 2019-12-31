package com.msj.android_tab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.msj.android_tab.Fragment.BannerFragment;
import com.msj.android_tab.views.transform.RotateTransformer;

public class BannerActivity extends BaseActicity {

    private ViewPager mVpMian;

    private int[] mResIds = new int[]{
            R.drawable.banner_image1,
            R.drawable.banner_image2,
            R.drawable.banner_image3,
            R.drawable.banner_image4,
            R.drawable.banner_image5,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        initNavTab(true,"ViewPager实现轮播动画");

        mVpMian = findViewById(R.id.vp_main);
        mVpMian.setOffscreenPageLimit(3);
        mVpMian.setPageMargin(40);
        mVpMian.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return BannerFragment.newInstance(mResIds[i]);
            }

            @Override
            public int getCount() {
                return mResIds.length;
            }
        });

        mVpMian.setPageTransformer(true,new RotateTransformer());

    }
}
