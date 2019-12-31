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

public class TabFragment extends Fragment {
    private static final String BUNDLE_KEY_TITLE = "key_title";
    private TextView mTvTitle;
    private String mTitle;

    // Fragment 调用 activity方法 通过 interface
    public static interface  onTitleClickListener{
        void onClick(String title);
    }
    private onTitleClickListener mListener;
    public void setOnTitleClickListener(onTitleClickListener listener) {
        this.mListener = listener;
    }


    public static TabFragment newInstance(String title){
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY_TITLE,title);
        TabFragment tabFragment = new TabFragment();
        // 应用重启时 可以通过Arguments 获取到已经设置好的数据
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         Bundle argment =  getArguments();
         if (argment != null){
             mTitle = argment.getString(BUNDLE_KEY_TITLE,"");
         }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTvTitle = view.findViewById(R.id.tv_title);
        mTvTitle.setText(mTitle);

        mTvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Fragment 调用 activity方法 通过 interface
                if (mListener != null){
                    mListener.onClick("点击了");
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void changeTitle(String title){
        if (!isAdded()) return;
        mTvTitle.setText(title);
    }
}
