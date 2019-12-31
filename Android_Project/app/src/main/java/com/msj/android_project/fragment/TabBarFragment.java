package com.msj.android_project.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.msj.android_project.R;
import com.msj.android_project.view.ViewPagerIndicator;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabBarFragment extends Fragment {

    private static final String BUNDLE_KEY_TITLE = "key_title";
    private static final String BUNDLE_KEY_TYPE = "key_type";
    @BindView(R.id.id_viewpager) ViewPager viewpager;
    @BindView(R.id.indicator) ViewPagerIndicator indicator;
    private SparseArray<GoodsFragment> mFragments = new SparseArray<>(); // 存储生成的fragment
    private List<String> mLists;
    private int mType;

    // Fragment 调用 activity方法 通过 interface
    public interface  onTitleClickListener{
        void onClick(String title);
    }
    private onTitleClickListener mListener;
    public void setOnTitleClickListener(onTitleClickListener listener) {
        this.mListener = listener;
    }


    public static TabBarFragment newInstance(List<String> lists,int type){
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_KEY_TITLE, (Serializable) lists);
        bundle.putInt(BUNDLE_KEY_TYPE,type);
        TabBarFragment tabFragment = new TabBarFragment();
        // 应用重启时 可以通过Arguments 获取到已经设置好的数据
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle argment =  getArguments();
        if (argment != null){
            mLists = (List<String>) argment.getSerializable(BUNDLE_KEY_TITLE);
            mType = argment.getInt(BUNDLE_KEY_TYPE);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mLists != null && mLists.size() > 0){
            viewpager.setOffscreenPageLimit(mLists.size());
            indicator.setTabTitle(mLists);
            indicator.setVisibleTabCount(4);
            indicator.setViewPager(viewpager, 0);
            indicator.setVisibility(View.VISIBLE);
            viewpager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
                @Override
                public Fragment getItem(int i) {
                    return GoodsFragment.newInstance(i,mType);
                }

                @Override
                public int getCount() {
                    return mLists.size();
                }
            });
        }else{
            indicator.setVisibility(View.GONE);
        }
    }
}
