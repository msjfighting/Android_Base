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
import com.msj.android_tab.views.TabView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivityWithTab extends BaseActicity {

    private ViewPager mVpMian;
    private List<String> titleList = new ArrayList<>(Arrays.asList("微信","通讯录","发现","我"));
    private TabView mTabWechat, mTabFriend,mTabFind,mTabMe;

    private SparseArray<TabFragment> mFragments = new SparseArray<>(); // 存储生成的fragment

    private List<TabView> mTabs = new ArrayList<>();

    private static final String BUNDLE_KEY_POS = "bundle_key_pos";

    private int mCurTabPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);

        initNavTab(true,"ViewPager实现Tab效果");

        if (savedInstanceState != null){
            mCurTabPos = savedInstanceState.getInt(BUNDLE_KEY_POS);
        }

        initView();

        initViewPagerAdapter();

        initEvents();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // 旋转屏幕前 保留选择的tab
        outState.putInt(BUNDLE_KEY_POS,mVpMian.getCurrentItem());
    }

    private void initEvents() {
        for (int i = 0; i < mTabs.size(); i++) {
            TabView tabView = mTabs.get(i);
            final int finalI = i;
            tabView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mVpMian.setCurrentItem(finalI,false);
                    setCurrentTab(finalI);
                }
            });
        }
    }

    private void initViewPagerAdapter() {
        mVpMian.setOffscreenPageLimit(titleList.size());
        mVpMian.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                TabFragment fragment = TabFragment.newInstance(titleList.get(i));
                if (i == 0){
                    fragment.setOnTitleClickListener(new TabFragment.onTitleClickListener() {
                        @Override
                        public void onClick(String title) {
                            changeWechatTab(title);
                        }
                    });
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return titleList.size();
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                // 创建时加入到 mFragments
                TabFragment fragment = (TabFragment)super.instantiateItem(container, position);
                mFragments.put(position,fragment);
                return fragment;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                // 销毁时移除mFragments中的fragment
                mFragments.remove(position);
                super.destroyItem(container, position, object);
            }
        });
        mVpMian.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffset1) {
                // 左->右 0->1 left pos, right pos + 1, positionOffset 0~1
                // left 1~0 (1 - positionOffset); right 0~1 (positionOffset)

                // 右->左 1->0 left pos, right pos + 1, positionOffset 1~0
                // left 0~1 (1 - positionOffset) ; right 1~0 (positionOffset)

                if (positionOffset > 0){
                    TabView left = mTabs.get(position);
                    TabView right = mTabs.get(position + 1);

                    left.setProgress( 1- positionOffset);
                    right.setProgress(positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        mVpMian = findViewById(R.id.vp_main);

        mTabWechat = findViewById(R.id.tab_wechat);
        mTabFriend = findViewById(R.id.tab_friend);
        mTabFind = findViewById(R.id.tab_find);
        mTabMe = findViewById(R.id.tab_me);

        mTabWechat.setIconAndTitle(R.drawable.ain,R.drawable.aio,"微信");
        mTabFriend.setIconAndTitle(R.drawable.ail,R.drawable.aim,"通讯录");
        mTabFind.setIconAndTitle(R.drawable.aip,R.drawable.aiq,"发现");
        mTabMe.setIconAndTitle(R.drawable.air,R.drawable.ais,"我");

        mTabs.add(mTabWechat);
        mTabs.add(mTabFriend);
        mTabs.add(mTabFind);
        mTabs.add(mTabMe);

        mTabWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // activity调用Fragment方法
               TabFragment tabFragment = mFragments.get(0);
               if (tabFragment != null){
                   tabFragment.changeTitle("微信1");
               }
            }
        });
        setCurrentTab(mCurTabPos);
    }

    public void setCurrentTab(int pos){
        for (int i = 0; i < mTabs.size(); i++) {
            TabView tabView = mTabs.get(i);
            if (i == pos){
                tabView.setProgress(1);
            }else {
                tabView.setProgress(0);
            }
        }
    }

    public void changeWechatTab(String title){

    }
}
