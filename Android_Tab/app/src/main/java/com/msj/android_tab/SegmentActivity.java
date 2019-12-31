package com.msj.android_tab;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.msj.android_tab.Fragment.TabFragment;
import com.msj.android_tab.Fragment.ViewPagerFragment;
import com.msj.android_tab.layout.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SegmentActivity extends BaseActicity {

    private ViewPager mVpMian;
    private List<String> mTitles = new ArrayList<>(Arrays.asList("短信","收藏","推荐","微信","通讯录","发现"));

    private ViewPagerIndicator viewPagerIndicator;
    private SparseArray<ViewPagerFragment> mVpFragments = new SparseArray<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segment);

        initNavTab(true,"ViewPager实现Segment效果");

        initView();
    }

    private void initView() {
        mVpMian = fd(R.id.vp_main);
        viewPagerIndicator = fd(R.id.indicator);

        viewPagerIndicator.setVisibleTabCount(4);
        viewPagerIndicator.setTabTitle(mTitles);

        mVpMian.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return ViewPagerFragment.newInstence(mTitles.get(i));
            }

            @Override
            public int getCount() {
                return mTitles.size();
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                ViewPagerFragment viewPagerFragment = (ViewPagerFragment) super.instantiateItem(container, position);
                mVpFragments.put(position,viewPagerFragment);
                return viewPagerFragment;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                mVpFragments.remove(position);
                super.destroyItem(container, position, object);
            }
        });

        viewPagerIndicator.setViewPager(mVpMian,0);
    }
}
