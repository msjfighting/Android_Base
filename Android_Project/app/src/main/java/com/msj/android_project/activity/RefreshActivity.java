package com.msj.android_project.activity;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import com.msj.android_project.R;
import com.msj.android_project.fragment.TabBarFragment;
import com.msj.android_project.view.TabBarView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RefreshActivity extends BaseActivity {
    @BindView(R.id.id_viewpager)
    ViewPager viewpager;
    private List<String> lists = new ArrayList<>(Arrays.asList("全部","女装","男装","内衣","美妆"
                                                                ,"配饰","鞋品","箱包","儿童","母婴","居家"
                                                                ,"美食","数码","家电","其他","车品","文体","宠物"
                                                                ));
    @BindView(R.id.id_home)
    public TabBarView tabBar1;

    @BindView(R.id.id_pangdan)
    public TabBarView tabBar2;

    @BindView(R.id.id_order)
    public TabBarView tabBar3;

    private List<TabBarView> mTabs = new ArrayList<>();


    private List<String> titleList = new ArrayList<>(Arrays.asList("首页","榜单","订单"));

    private SparseArray<TabBarFragment> mFragments = new SparseArray<>(); // 存储生成的fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);
        ButterKnife.bind(this);

        initNavBar(true,"好好学习");

        initView();
        initViewPagerAdapter();
        initEvents();
    }
    private void initEvents() {
        for (int i = 0; i < mTabs.size(); i++) {
            TabBarView tabView = mTabs.get(i);
            final int finalI = i;
            tabView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewpager.setCurrentItem(finalI,false);
                    setCurrentTab(finalI);
                }
            });
        }
    }
    private void initViewPagerAdapter() {

        viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                if (i == 0 ){
                    TabBarFragment fragment = TabBarFragment.newInstance(lists,0);
                    return fragment;
                }else if (i == 1){
                    TabBarFragment fragment = TabBarFragment.newInstance(Arrays.asList("2小时销售榜","今日爆单榜","昨日爆单榜","出单指数榜"),1);
                    return fragment;
                }
                return TabBarFragment.newInstance(null,2);
            }

            @Override
            public int getCount() {
                return titleList.size();
            }
            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                // 创建时加入到 mFragments
                TabBarFragment fragment = (TabBarFragment)super.instantiateItem(container, position);
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

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffset1) {
                if (positionOffset > 0){
                    TabBarView left = mTabs.get(position);
                    TabBarView right = mTabs.get(position + 1);

                    left.setProgress( positionOffset);
                    right.setProgress(1 - positionOffset);
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
    @SuppressLint("HandlerLeak")
    private void initView() {

        tabBar1.setIconAndTitle(R.drawable.home_no,R.drawable.home,"首页");
        tabBar2.setIconAndTitle(R.drawable.hot_no,R.drawable.hot,"榜单");
        tabBar3.setIconAndTitle(R.drawable.order_no,R.drawable.order,"订单");

        mTabs.add(tabBar1);
        mTabs.add(tabBar2);
        mTabs.add(tabBar3);


        setCurrentTab(0);
    }

    public void setCurrentTab(int pos){
        for (int i = 0; i < mTabs.size(); i++) {
            TabBarView tabView = mTabs.get(i);
            if (i == pos){
                tabView.setProgress(0);
            }else {
                tabView.setProgress(1);
            }
        }
    }

}