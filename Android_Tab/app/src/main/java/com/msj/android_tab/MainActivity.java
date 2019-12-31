package com.msj.android_tab;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.msj.android_tab.Fragment.TabFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mVpMian;
    private List<String> titleList = new ArrayList<>(Arrays.asList("微信","通讯录","发现","我"));
    private Button mBtnWechat, mBtnFriend,mBtnFind,mBtnMe;

    private SparseArray<TabFragment> mFragments = new SparseArray<>(); // 存储生成的fragment

    private List<Button> mTabs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initViewPagerAdapter();

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
                    Button left = mTabs.get(position);
                    Button right = mTabs.get(position + 1);

                    left.setText((1-positionOffset)+"");

                    right.setText(positionOffset+"");
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

        mBtnWechat = findViewById(R.id.btn_wechat);
        mBtnFriend = findViewById(R.id.btn_friend);
        mBtnFind = findViewById(R.id.btn_find);
        mBtnMe = findViewById(R.id.btn_me);

        mTabs.add(mBtnWechat);
        mTabs.add(mBtnFriend);
        mTabs.add(mBtnFind);
        mTabs.add(mBtnMe);

        mBtnWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // activity调用Fragment方法
               TabFragment tabFragment = mFragments.get(0);
               if (tabFragment != null){
                   tabFragment.changeTitle("微信1");
               }
            }
        });
    }


    public void changeWechatTab(String title){
        mBtnWechat.setText(title);
    }
}
