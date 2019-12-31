package com.msj.android_tab.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.msj.android_tab.R;

public class ViewPagerFragment extends Fragment {
    private TextView tvTitle;
    private String mTitle;
    private static final String BUNDLE_TITLE_KEY = "title";

    public static ViewPagerFragment newInstence(String title){
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_TITLE_KEY,title);
        ViewPagerFragment viewPagerFragment = new ViewPagerFragment();
        viewPagerFragment.setArguments(bundle);
        return viewPagerFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        if (arg != null){
            mTitle = (String) arg.getSerializable(BUNDLE_TITLE_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_segment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText(mTitle);
    }

    public void changeTitle(String title){
        if (!isAdded()) return;
        tvTitle.setText(title);
    }


}
