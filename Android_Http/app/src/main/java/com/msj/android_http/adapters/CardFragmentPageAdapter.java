package com.msj.android_http.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.msj.android_http.beans.CarInfo;
import com.msj.android_http.fragment.CardFragment;

import java.util.List;

public class CardFragmentPageAdapter  extends FragmentStatePagerAdapter {
    private List<CarInfo> mList;
    private List<String> answers;

    public CardFragmentPageAdapter(FragmentManager fm, List<CarInfo> list,List<String> answers) {
        super(fm);
        mList = list;
        this.answers = answers;
    }

    @Override
    public Fragment getItem(int i) {
        return  CardFragment.newInstance(mList.get(i),answers);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
