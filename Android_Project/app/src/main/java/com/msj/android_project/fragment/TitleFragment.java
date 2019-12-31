package com.msj.android_project.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.msj.android_project.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TitleFragment extends Fragment {

   @BindView(R.id.id_back)
    ImageView back;
   @BindView(R.id.tv_title)
    TextView textView;
   @BindView(R.id.rl_layout)
    RelativeLayout mLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 第一次绘制用户界面时系统回调
     * @param inflater  布局填充器或加载器 将xml转换为view
     * @param container 表示当前fragment插入activity的布局视图对象
     * @param savedInstanceState 表示存储上一个fragment的信息
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_title,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this,view);


    }
}
