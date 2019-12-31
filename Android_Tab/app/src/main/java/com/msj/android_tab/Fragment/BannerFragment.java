package com.msj.android_tab.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.msj.android_tab.R;

public class BannerFragment extends Fragment {
    private static final String BUNDLE_KEY_RES_ID = "bundle_key_resid";

    private ImageView mIvContent;
    private int mResId;

    public static BannerFragment newInstance(int resId){
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_KEY_RES_ID,resId);
        BannerFragment splashFragment = new BannerFragment();
        splashFragment.setArguments(bundle);
        return splashFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       Bundle arg = getArguments();
       if (arg != null){
           mResId = (int) arg.getSerializable(BUNDLE_KEY_RES_ID);
       }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_banner,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIvContent = view.findViewById(R.id.iv_content);
        mIvContent.setImageResource(mResId);
    }
}
